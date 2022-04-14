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
        model.addAttribute("user", AuthService.getUserView());
        model.addAttribute("friends", this.friendService.getFriends(Long.parseLong(userId), 100));
        return "profile";
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserView> getUserData(@PathVariable("userId") String stringUserId) {
        Long userId = Long.parseLong(stringUserId);
        UserProfileView userProfileView = ViewMapper.mapToUserProfile(this.userService.getUser(userId));
        if (userId.equals(AuthService.getUser().getId()))
            userProfileView.setOwner(true);
        return new ResponseEntity<>(userProfileView, HttpStatus.OK);
    }
}
