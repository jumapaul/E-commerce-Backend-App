package com.ecommerceapp.chatservice.chat;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private String id;
    private String senderId;
    private String recipientId;
    private String content;
}
