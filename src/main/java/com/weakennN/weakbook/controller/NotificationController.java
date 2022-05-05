package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.NotificationService;
import com.weakennN.weakbook.view.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<List<Notification>> getNotifications() {
        return new ResponseEntity<>(this.notificationService.getUserNotifications(), HttpStatus.OK);
    }

    @PatchMapping(value = "/notifications/see", consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<?> seeNotifications() {
        this.notificationService.seeNotifications();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
