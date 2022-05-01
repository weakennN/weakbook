package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.NotificationType;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.NotificationRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.view.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private WebSocketService webSocketService;
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public NotificationService(WebSocketService webSocketService, NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.webSocketService = webSocketService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void saveNotification(NotificationType notificationType, Long receiverId, Long entityId) {
        ApplicationUser user = AuthService.getUser();
        User receiver = this.userRepository.findById(receiverId).get();
        if (user.getId().equals(receiverId))
            return;
        if (notificationType.equals(NotificationType.POST_LIKE))
            this.sendNotification(user.getFirstName() + " " + user.getLastName() + " liked your post.", receiver.getEmail()
                    , "/post/" + entityId, NotificationType.POST_LIKE);
        else if (notificationType.equals(NotificationType.COMMENT_LIKE))
            this.sendNotification(user.getFirstName() + " " + user.getLastName() + " liked you comment.", receiver.getEmail()
                    , "/post/" + entityId, NotificationType.COMMENT_LIKE);
        else if (notificationType.equals(NotificationType.FRIEND_REQUEST))
            this.sendNotification(user.getFirstName() + " " + user.getLastName() + " sent you a friend request.", receiver.getEmail()
                    , "", NotificationType.FRIEND_REQUEST);
        this.notificationRepository.insert(notificationType.getId(), entityId, user.getId(), receiverId);
    }

    private void sendNotification(String message, String username, String link, NotificationType notificationType) {
        this.webSocketService.sendToUsers(this.createNotification(message, link, notificationType), "/queue/notifications", List.of(username));
    }

    private Notification createNotification(String message, String link, NotificationType notificationType) {
        return new Notification(message, link, notificationType);
    }
}
