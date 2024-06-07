package com.harkesh.letterbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @Column(columnDefinition = "VARCHAR(5000)")
    private String content;
    private String messageType;
    private String contentType;
    @CreatedDate
    private Timestamp timestamp;
}
