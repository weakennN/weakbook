package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.Friend;
import com.weakennN.weakbook.repository.FriendRepository;
import com.weakennN.weakbook.repository.FriendRequestRepository;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.Notification;
import com.weakennN.weakbook.view.NotificationType;
import com.weakennN.weakbook.view.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    private FriendRequestRepository friendRequestRepository;
    private FriendRepository friendRepository;
    private WebSocketService webSocketService;

    public FriendService(FriendRequestRepository friendRequestRepository,
                         WebSocketService webSocketService, FriendRepository friendRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.webSocketService = webSocketService;
        this.friendRepository = friendRepository;
    }

    public void sendFriendRequest(Long receiverId) {
        System.out.println(receiverId);
        this.friendRequestRepository.sendFriendRequest(receiverId, AuthService.getUser().getId());
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

    public List<UserView> getFriends(Long userId, int limit) {
        List<UserView> result = new ArrayList<>();
        for (Friend friend : this.friendRepository.getFriendsByUserId(userId, limit)) {
            result.add(ViewMapper.mapUser(friend.getOwner().getId().equals(userId) ? friend.getUser() : friend.getOwner()));
        }
        return result;
    }
}
