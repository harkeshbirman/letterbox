package com.harkesh.letterbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String senderName;
    private String receiverName;
    private String content;
    private String contentType;
    private String messageType;
}
