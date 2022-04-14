package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.FriendService;
import com.weakennN.weakbook.view.PostView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/friend")
public class FriendController {

    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping(value = "/request", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<String> sendFriendRequest(@RequestBody String receiverId) {
        this.friendService.sendFriendRequest(Long.parseLong(receiverId));
        return new ResponseEntity<>("214124", HttpStatus.OK);
    }

    @PostMapping("/accept")
    @ResponseBody
    public ResponseEntity<?> acceptFriendRequest(@RequestBody Long id) {
        this.friendService.acceptFriendRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteFriendRequest(@RequestBody Long id) {
        this.friendService.deleteFriendRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: get all friends get mapping
}
