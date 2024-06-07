package com.harkesh.letterbox.service;

import com.harkesh.letterbox.dto.ContactDto;
import com.harkesh.letterbox.dto.MessageDto;
import com.harkesh.letterbox.dto.UserDto;
import com.harkesh.letterbox.entity.ChatMessage;
import com.harkesh.letterbox.entity.User;
import com.harkesh.letterbox.exceptions.NotFoundException;
import com.harkesh.letterbox.repository.ChatRepository;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public List<MessageDto> getMessagesByUsername(String senderName, String receiverName) {
//         not really sender and receiver, receiver can also be sender in a chat

        User sender = userService.getUserByUsername(senderName);
        User receiver = userService.getUserByUsername(receiverName);

        List<MessageDto> messages = chatRepository.findMessagesWithSenderAndReceiver(sender.getId(), receiver.getId());

        if (messages.isEmpty()) {
            throw new NotFoundException("No Messages found");
        }

        return messages;
    }

    public ContactDto getContacts(String username) {
        User user = userService.getUserByUsername(username);

        List<Long> contactIds = chatRepository.findContacts(user.getId());

        List<UserDto> contacts = contactIds.stream()
            .map(userService::getUserById)
            .collect(Collectors.toList());

        return ContactDto.builder().contacts(contacts).build();
    }

    public void handleRegister(MessageDto message) {
        System.out.println(message.getSenderName() + " connected at " + (new Date()).toString());
    }

    public void handleMessage(MessageDto messageDto) {
        User sender = userService.getUserByUsername(messageDto.getSenderName());
        User receiver = userService.getUserByUsername(messageDto.getReceiverName());

        ChatMessage chatMessage = ChatMessage.builder()
            .sender(sender)
            .receiver(receiver)
            .messageType(messageDto.getMessageType())
            .contentType(messageDto.getContentType())
            .content(messageDto.getContent())
            .build();

        chatRepository.save(chatMessage);
    }
}
