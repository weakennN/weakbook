package com.weakennN.weakbook.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketService {

    private SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendToUsers(Object data, String destination, List<String> usernames) {
        System.out.println(usernames);
        for (String username : usernames) {
            this.simpMessagingTemplate.convertAndSendToUser(username, destination, data);
        }
    }
}
