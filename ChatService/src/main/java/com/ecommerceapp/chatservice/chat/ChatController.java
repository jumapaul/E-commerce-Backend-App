package com.ecommerceapp.chatservice.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService service;
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage message
    ) {
        ChatMessage savedMsg = service.save(message);

        template.convertAndSendToUser(
                message.getRecipientId(),
                "/queue/message",
                ChatNotification.builder()
                        .id(savedMsg.getId())
                        .recipientId(savedMsg.getRecipientId())
                        .senderId(savedMsg.getSenderId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }

    @GetMapping("/chat/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessage(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ) {
        return ResponseEntity.ok(service.findChatMessage(senderId, recipientId));
    }
}