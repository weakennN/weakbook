package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.FriendService;
import com.weakennN.weakbook.view.PostView;
import com.weakennN.weakbook.view.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/friend")
public class FriendController {

    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping(value = "/request/{userId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> sendFriendRequest(@PathVariable Long userId) {
        this.friendService.sendFriendRequest(userId);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }

    @PostMapping(value = "/accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long id) {
        this.friendService.acceptFriendRequest(id);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }

    @PostMapping(value = "/decline/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteFriendRequest(@PathVariable Long id) {
        this.friendService.deleteFriendRequest(id);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getFriends(@PathVariable String userId, @RequestParam("offset") int offset) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("count", this.friendService.getCountFriends(Long.parseLong(userId)));
        result.put("friends", this.friendService.getFriends(Long.parseLong(userId), 70, offset));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeFriend(@PathVariable Long userId) {
        this.friendService.removeFriend(userId);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }
}
