package com.harkesh.letterbox.repository;

import com.harkesh.letterbox.dto.MessageDto;
import com.harkesh.letterbox.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    @Query(
        "SELECT NEW " +
        "com.harkesh.letterbox.dto.MessageDto(c.sender.username, c.receiver.username, c.content, c.contentType, c.messageType) " +
        "FROM ChatMessage c "  +
        "WHERE (c.sender.id = :id_1 AND c.receiver.id = :id_2) " +
        "OR (c.sender.id = :id_2 AND c.receiver.id = :id_1)")
    List<MessageDto> findMessagesWithSenderAndReceiver(@Param("id_1") long id1, @Param("id_2") long id2);

    @Query(
        "SELECT DISTINCT CASE " +
        "WHEN c.sender.id = :id THEN c.receiver.id ELSE c.sender.id END " +
        "AS user_id " +
        "FROM ChatMessage c " +
        "WHERE c.sender.id = :id OR c.receiver.id = :id"
    )
    List<Long> findContacts(@Param("id") long id);
}
