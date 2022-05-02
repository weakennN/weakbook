package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.NotificationType;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.NotificationRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private WebSocketService webSocketService;
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public NotificationService(WebSocketService webSocketService, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.webSocketService = webSocketService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void saveNotification(NotificationType notificationType, Long receiverId, Long entityId) {
        ApplicationUser user = AuthService.getUser();
        User receiver = this.userRepository.findById(receiverId).get();
        if (user.getId().equals(receiverId))
            return;
        this.sendNotification(createNotification(notificationType, user, entityId), receiver.getEmail());
        this.notificationRepository.insert(notificationType.getId(), entityId, user.getId(), receiverId);
    }

    public List<Notification> getUserNotifications() {
        List<com.weakennN.weakbook.entity.Notification> notifications = this.notificationRepository.findByReceiver(AuthService.getUser().getId());
        List<Notification> result = new ArrayList<>();
        for (int i = 0; i < notifications.size(); i++)
            result.add(ViewMapper.mapNotification(notifications.get(i)));
        return result;
    }

    private void sendNotification(Notification notification, String username) {
        this.webSocketService.sendToUsers(notification, "/queue/notifications", List.of(username));
    }

    public Notification createNotification(NotificationType notificationType, ApplicationUser user, Long entityId) {
        if (notificationType.equals(NotificationType.POST_LIKE))
            return new Notification(user.getFirstName() + " " + user.getLastName() + " liked your post.", "/post/" + entityId, notificationType);
        else if (notificationType.equals(NotificationType.COMMENT_LIKE))
            return new Notification(user.getFirstName() + " " + user.getLastName() + " liked you comment.", "/post/" + entityId, notificationType);
        return new Notification(user.getFirstName() + " " + user.getLastName() + " sent you a friend request.", "", notificationType);
    }
}
