package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.AuthService;
import com.weakennN.weakbook.service.ChatService;
import com.weakennN.weakbook.view.ChatRoomView;
import com.weakennN.weakbook.view.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller()
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping({"/", ""})
    public String getChatView(Model model) {
        model.addAttribute("user", AuthService.getUserView());
        return "chat";
    }

    @GetMapping("/chatRooms")
    @ResponseBody
    public ResponseEntity<List<ChatRoomView>> getChatRooms() {
        return new ResponseEntity<>(this.chatService.getUserChatRooms(), HttpStatus.OK);
    }

    @PostMapping(value = "/chatRooms", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ChatRoomView> createChatRoom(@RequestBody String userId) {
        return new ResponseEntity<>(this.chatService.createNewChatRoom(Long.parseLong(userId)), HttpStatus.OK);
    }

    @PostMapping(value = "/message/{chatRoomId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> message(@RequestBody Message message, @PathVariable Long chatRoomId) {
        this.chatService.sendMessage(message, chatRoomId);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }

    @GetMapping("/chatRooms/messages/{chatRoomId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable("chatRoomId") Long chatRoomId, @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.chatService.getMessages(chatRoomId, offset), HttpStatus.OK);
    }
}
