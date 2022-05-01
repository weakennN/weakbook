package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.Friend;
import com.weakennN.weakbook.entity.NotificationType;
import com.weakennN.weakbook.repository.FriendRepository;
import com.weakennN.weakbook.repository.FriendRequestRepository;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    private FriendRequestRepository friendRequestRepository;
    private FriendRepository friendRepository;
    private NotificationService notificationService;

    public FriendService(FriendRequestRepository friendRequestRepository,
                         NotificationService notificationService, FriendRepository friendRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
        this.notificationService = notificationService;
    }

    public void sendFriendRequest(Long receiverId) {
        this.friendRequestRepository.sendFriendRequest(receiverId, AuthService.getUser().getId());
        this.notificationService.saveNotification(NotificationType.FRIEND_REQUEST, receiverId,
                this.friendRequestRepository.findByReceiverIdAndSenderId(receiverId, AuthService.getUser().getId()).getId());
    }

    public void acceptFriendRequest(Long id) {
        this.friendRequestRepository.accept(id);
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
