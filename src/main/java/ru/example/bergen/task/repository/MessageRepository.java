package ru.example.bergen.task.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.example.bergen.task.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT msg FROM Message msg WHERE msg.dateTime >= :start AND msg.dateTime <= :end")
    List<Message> getAllBetweenDates(LocalDateTime start, LocalDateTime end);

    List<Message> getAllByQueue(String queue);

    List<Message> getAllByMessageId(Integer id);

    @Query("SELECT msg FROM Message msg WHERE msg.messageId=:messageId AND msg.queue=:queue AND msg.dateTime >= :start AND msg.dateTime <= :end")
    List<Message>getAllByMessageIdQueueDates(Integer messageId, String queue, LocalDateTime start, LocalDateTime end);
}
