package ru.example.bergen.task.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.example.bergen.task.config.TestConfig;
import ru.example.bergen.task.model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
class MessageServiceTest {

    @Autowired
    MessageService service;

    LocalDateTime dateTime = LocalDateTime.of(2021, 6, 4, 11, 0);
    List<Message> messages = List.of(
            new Message(1, 15, "test-queue", "body1", dateTime),
            new Message(2, 16 ,"test-queue", "body2", dateTime.plusDays(2)),
            new Message(3, 15,"test-queue", "body1", dateTime.plusDays(1)),
            new Message(4, 16 ,"test-queue", "body2", dateTime.plusDays(3)),
            new Message(5, 14,"test-queue1", "body1", dateTime.plusMinutes(20)));

    @Test
    void getAllBetweenDates() throws Exception {
        LocalDate start = LocalDate.of(2021, 6, 4);
        LocalDate end = LocalDate.of(2021, 6, 5);
        List<Message> actual = List.of(messages.get(0), messages.get(2) ,messages.get(4));
        List<Message> expected = service.getAllBetweenDates(start, end);
        assertEquals(expected, actual);
    }

    @Test
    void getAllByQueue() throws Exception {
        List<Message> actual = List.of(messages.get(4));
        List<Message> expected = service.getAllByQueue("test-queue1");
        assertEquals(expected, actual);
    }

    @Test
    void getAllByMessageId() {
        List<Message> actual = List.of(messages.get(0), messages.get(2));
        List<Message> expected = service.getAllByMessageId(15);
        assertEquals(expected, actual);
    }


    @Test
    void saveMessage() {
        Message actual = new Message(null,10 ,"task-queue", "body1", null);
        Message msg = service.save(actual);
        actual.setId(msg.getId());
        actual.setDateTime(msg.getDateTime());
        assertEquals(service.findById(actual.getId()), actual);
    }

    @Test
    void getAllByMessageIdQueueDates() throws Exception {
        LocalDate start = LocalDate.of(2021, 6, 6);
        LocalDate end = LocalDate.of(2021, 6, 7);
        List<Message> actual = List.of(messages.get(1), messages.get(3));
        List<Message> expected = service.getAllByMessageIdQueueDates(16, "test-queue", start, end);
        assertEquals(expected, actual);
    }
}