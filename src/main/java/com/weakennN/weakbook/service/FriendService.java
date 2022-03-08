package com.weakennN.weakbook.service;

import com.weakennN.weakbook.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private FriendRequestRepository friendRequestRepository;

    public void sendFriendRequest(Long receiverId) {
        this.friendRequestRepository.sendFriendRequest(receiverId, AuthService.getCurrentUser().getId());
        // TODO: send notification
    }

    public void acceptFriendRequest(Long id) {
        this.friendRequestRepository.accept(id);
        // TODO: send notification
    }

    public void deleteFriendRequest(Long id) {
        this.friendRequestRepository.delete(id);
    }
}
