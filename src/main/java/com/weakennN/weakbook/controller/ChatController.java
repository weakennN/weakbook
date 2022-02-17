package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.service.ChatService;
import com.weakennN.weakbook.view.ChatRoomView;
import com.weakennN.weakbook.view.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping({"/", ""})
    public String getChatView() {
        return "chat";
    }

    @GetMapping("/getChatRooms")
    @ResponseBody
    public ResponseEntity<List<ChatRoomView>> getChatRooms() {
        return new ResponseEntity<>(this.chatService.getUserChatRooms(), HttpStatus.OK);
    }

    @PostMapping(value = "/createChatRoom", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ChatRoomView> createChatRoom(@RequestBody String userId) {
        return new ResponseEntity<>(this.chatService.createNewChatRoom(Long.parseLong(userId)), HttpStatus.OK);
    }

    @MessageMapping("/message")
    public void message(@RequestBody Message message, UsernamePasswordAuthenticationToken authenticationToken) {
        this.chatService.sendMessage(message, (ApplicationUser) authenticationToken.getPrincipal());
    }

    @GetMapping("getMessages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.chatService.getMessages(chatRoomId, offset), HttpStatus.OK);
    }
}
