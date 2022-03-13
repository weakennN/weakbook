package com.weakennN.weakbook.service;

import com.weakennN.weakbook.repository.FriendRequestRepository;
import com.weakennN.weakbook.view.Notification;
import com.weakennN.weakbook.view.NotificationType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private FriendRequestRepository friendRequestRepository;
    private WebSocketService webSocketService;

    public FriendService(FriendRequestRepository friendRequestRepository,
                         WebSocketService webSocketService) {
        this.friendRequestRepository = friendRequestRepository;
        this.webSocketService = webSocketService;
    }

    public void sendFriendRequest(Long receiverId) {
        System.out.println(receiverId);
        this.friendRequestRepository.sendFriendRequest(receiverId, AuthService.getCurrentUser().getId());
        Notification notification = new Notification("Someone send you a friend request.", "");
        notification.setType(NotificationType.FRIEND);
        this.webSocketService.sendToUsers(notification, "/queue/notifications", List.of("test@abv.bg"));
    }

    public void acceptFriendRequest(Long id) {
        this.friendRequestRepository.accept(id);
        // TODO: send notification
    }

    public void deleteFriendRequest(Long id) {
        this.friendRequestRepository.delete(id);
    }
}
