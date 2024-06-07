package com.harkesh.letterbox.controller;

import com.harkesh.letterbox.dto.MessageDto;
import com.harkesh.letterbox.entity.ChatMessage;
import com.harkesh.letterbox.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/register")
//    @SendTo("/public/broadcast")
    public void register(
        @Payload MessageDto message,
        SimpMessageHeaderAccessor headerAccessor
    ) {

        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSenderName());
        chatService.handleRegister(message);
    }

    @MessageMapping("/chat/send")
    public void send(@Payload MessageDto message) {
        chatService.handleMessage(message);
        messagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
    }
}
