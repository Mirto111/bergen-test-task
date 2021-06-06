package ru.example.bergen.task.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.bergen.task.model.Message;
import ru.example.bergen.task.repository.MessageRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message>getAllByMessageIdQueueDates(Integer messageId, String queue, LocalDate start, LocalDate end) throws IllegalAccessException {
        if (start == null || end == null || end.isBefore(start)) throw new IllegalAccessException("Неверная дата");
        if (queue == null || queue.isBlank()) throw new IllegalAccessException("Отсутствует имя очереди");
        return repository.getAllByMessageIdQueueDates(
                messageId,
                queue,
                LocalDateTime.of(start, LocalTime.of(0, 0)),
                LocalDateTime.of(end.plusDays(1), LocalTime.of(0, 0)));
    }

    public List<Message> getAllBetweenDates(LocalDate start, LocalDate end) throws IllegalAccessException {
        if (start == null || end == null || end.isBefore(start)) throw new IllegalAccessException("Неверная дата");

        return repository.getAllBetweenDates(LocalDateTime.of(start, LocalTime.of(0, 0)), LocalDateTime.of(end.plusDays(1), LocalTime.of(0, 0)));
    }

    public List<Message> getAllByQueue(String queue) throws IllegalAccessException {
        if (queue == null || queue.isBlank()) throw new IllegalAccessException("Отсутствует имя очереди");
        return repository.getAllByQueue(queue);
    }

    public List<Message> getAllByMessageId(Integer id) {
        return repository.getAllByMessageId(id);
    }

    public Message save(Message message) {
        message.setDateTime(LocalDateTime.now().withNano(0));
        return repository.save(message);
    }

    public Message findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

}
