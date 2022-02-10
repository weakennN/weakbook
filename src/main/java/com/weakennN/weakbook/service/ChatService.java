package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.ChatMessage;
import com.weakennN.weakbook.entity.ChatParticipant;
import com.weakennN.weakbook.entity.ChatRoom;
import com.weakennN.weakbook.repository.ChatMessagesRepository;
import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.ChatRoomRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.view.ChatRoomView;
import com.weakennN.weakbook.view.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private WebSocketService webSocketService;
    private ChatRoomRepository chatRoomRepository;
    private UserRepository userRepository;
    private ChatParticipantRepository chatParticipantRepository;
    private ChatMessagesRepository chatMessagesRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository
            , ChatParticipantRepository chatParticipantRepository, WebSocketService webSocketService
            , ChatMessagesRepository chatMessagesRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.chatParticipantRepository = chatParticipantRepository;
        this.webSocketService = webSocketService;
        this.chatMessagesRepository = chatMessagesRepository;
    }

    public List<ChatRoomView> getUserChatRooms() {
        List<ChatRoom> chatRooms = this.chatRoomRepository.getAllByUserId(this.userRepository
                .findByEmail(AuthService.getCurrentUser().getEmail()).get().getId());
        List<ChatRoomView> result = new ArrayList<>();
        // temporary mapping for testing
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomView resultChatRoom = new ChatRoomView();
            resultChatRoom.setName("test " + chatRoom.getId()).setLatestMessage("last message").setId(chatRoom.getId());
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

    public void sendMessage(Message message, ApplicationUser applicationUser) {
        this.saveMessage(message, applicationUser);
        this.webSocketService.sendToUsers(message, "/queue/chat",
                this.chatParticipantRepository.findParticipantsEmailByChatRoom(
                        applicationUser.getId(), message.getChatRoomId())
        );
    }

    private void saveMessage(Message message, ApplicationUser applicationUser) {
        ChatMessage chatMessage = new ChatMessage(message.getMessage(),
                this.userRepository.findByEmail(applicationUser.getEmail()).get(),
                this.chatRoomRepository.findById(message.getChatRoomId()).get());
        this.chatMessagesRepository.save(chatMessage);
    }
}
