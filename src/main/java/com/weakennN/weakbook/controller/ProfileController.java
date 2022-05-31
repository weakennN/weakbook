package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.AuthService;
import com.weakennN.weakbook.service.FriendService;
import com.weakennN.weakbook.service.UserService;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.UserProfileView;
import com.weakennN.weakbook.view.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private UserService userService;
    private FriendService friendService;

    public ProfileController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/profile/{userId}")
    public String index(Model model, @PathVariable String userId) {
        System.out.println(userId);
        model.addAttribute("user", AuthService.getUserView());
        model.addAttribute("friends", this.friendService.getFriends(Long.parseLong(userId), 10, 0));
        model.addAttribute("countFriends", this.friendService.getCountFriends(Long.parseLong(userId)));
        return "profile";
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserView> getUserData(@PathVariable("userId") String stringUserId) {
        System.out.println("hwello");
        Long userId = Long.parseLong(stringUserId);
        UserProfileView userProfileView = ViewMapper.mapToUserProfile(this.userService.getUser(userId));
        userProfileView.setOwner(userId.equals(AuthService.getUser().getId()));
        userProfileView.setAreFriends(this.friendService.areFriends(userId));
        return new ResponseEntity<>(userProfileView, HttpStatus.OK);
    }

    @GetMapping("/profile/friends/{userId}")
    public String getFriendsView(Model model, @PathVariable String userId) {
        model.addAttribute("user", AuthService.getUserView());
        model.addAttribute("userId", userId);
        return "friends";
    }
}
