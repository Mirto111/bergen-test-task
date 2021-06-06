package ru.example.bergen.task.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.example.bergen.task.model.Message;
import ru.example.bergen.task.service.MessageService;

@Component
public class Receiver {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    private final MessageService service;

    public Receiver(MessageService service) {
        this.service = service;
    }

    @JmsListener(destination = "task-queue")
    public void receive(Message message) {
        Message msg = service.save(message);
        LOGGER.info("receiving message and save='{}'", msg);
    }
}
