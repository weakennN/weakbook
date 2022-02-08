package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.ChatParticipant;
import com.weakennN.weakbook.entity.ChatRoom;
import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.ChatRoomRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.view.ChatRoomView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private ChatRoomRepository chatRoomRepository;
    private UserRepository userRepository;
    private ChatParticipantRepository chatParticipantRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository
            , ChatParticipantRepository chatParticipantRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.chatParticipantRepository = chatParticipantRepository;
    }

    public List<ChatRoomView> getUserChatRooms() {
        List<ChatRoom> chatRooms = this.chatRoomRepository.getAllByUserId(this.userRepository
                .findByEmail(AuthService.getCurrentUser().getEmail()).get().getId());
        List<ChatRoomView> result = new ArrayList<>();
        // temporary mapping for testing
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomView resultChatRoom = new ChatRoomView();
            resultChatRoom.setName("test " + chatRoom.getId()).setLatestMessage("last message");
            result.add(resultChatRoom);
        }

        return result;
    }

    public ChatRoomView createNewChatRoom(Long userId) {
        ApplicationUser user = AuthService.getCurrentUser();
        ChatRoom newChatRoom = this.chatRoomRepository.save(new ChatRoom());

        ChatParticipant chatParticipant1 = new ChatParticipant();
        chatParticipant1.setUser(this.userRepository.findByEmail(user.getEmail()).get()).setChatRoom(newChatRoom);
        ChatParticipant chatParticipant2 = new ChatParticipant();
        chatParticipant2.setUser(this.userRepository.findById(userId).get()).setChatRoom(newChatRoom);

        this.chatParticipantRepository.save(chatParticipant1);
        this.chatParticipantRepository.save(chatParticipant2);

        return new ChatRoomView();
    }
}
