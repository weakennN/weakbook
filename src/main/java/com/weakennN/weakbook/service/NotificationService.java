package com.weakennN.weakbook.service;

import com.weakennN.weakbook.view.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private WebSocketService webSocketService;

    public NotificationService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public void sendNotification(String message, String username, String link) {
        this.webSocketService.sendToUsers(this.createNotification(message, link),"/queue/notifications", List.of(username));
    }

    private Notification createNotification(String message, String link) {
        return new Notification(message, link);
    }
}
