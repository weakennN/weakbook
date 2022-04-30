package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.ChatMessage;
import com.weakennN.weakbook.entity.ChatParticipant;
import com.weakennN.weakbook.entity.ChatRoom;
import com.weakennN.weakbook.repository.ChatMessagesRepository;
import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.ChatRoomRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.utils.hypermedia.Link;
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
                .findByEmail(AuthService.getUser().getEmail()).get().getId());
        List<ChatRoomView> result = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomView chatRoomView = ViewMapper.mapToChatRoomView(chatRoom, this.chatMessagesRepository, this.chatParticipantRepository);
            result.add(chatRoomView);
        }

        return result;
    }

    public ChatRoomView createNewChatRoom(Long userId) {
        ApplicationUser user = AuthService.getUser();
        ChatRoom newChatRoom = this.chatRoomRepository.save(new ChatRoom());

        ChatParticipant chatParticipant1 = new ChatParticipant();
        chatParticipant1.setUser(this.userRepository.findByEmail(user.getEmail()).get()).setChatRoom(newChatRoom);
        ChatParticipant chatParticipant2 = new ChatParticipant();
        chatParticipant2.setUser(this.userRepository.findById(userId).get()).setChatRoom(newChatRoom);

        this.chatParticipantRepository.save(chatParticipant1);
        this.chatParticipantRepository.save(chatParticipant2);
        return new ChatRoomView();
    }

    public List<Message> getMessages(Long chatRoomId, int offset) {
        List<ChatMessage> chatMessages = this.chatMessagesRepository.getChatMessageByChatRoomId(chatRoomId, offset);
        List<Message> result = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            Message message = ViewMapper.mapMessage(chatMessage, chatMessage.getUser());
            if (AuthService.getUser().getId().equals(chatMessage.getUser().getId())) {
                message.setFromCurrentUser(true);
            }
            result.add(message);
        }
        return result;
    }

    public void sendMessage(Message message, ApplicationUser applicationUser) {
        ChatMessage chatMessage = this.saveMessage(message, applicationUser);
        this.webSocketService.sendToUsers(ViewMapper.mapMessage(chatMessage, chatMessage.getUser()), "/queue/chat",
                this.chatParticipantRepository.findParticipantsEmailByChatRoom(
                        applicationUser.getId(), message.getChatRoomId())
        );
    }

    private ChatMessage saveMessage(Message message, ApplicationUser applicationUser) {
        ChatMessage chatMessage = new ChatMessage(message.getMessage(),
                this.userRepository.findByEmail(applicationUser.getEmail()).get(),
                this.chatRoomRepository.findById(message.getChatRoomId()).get());
        return this.chatMessagesRepository.save(chatMessage);
    }
}
