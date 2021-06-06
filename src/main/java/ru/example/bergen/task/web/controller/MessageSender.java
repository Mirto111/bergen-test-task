package ru.example.bergen.task.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.bergen.task.model.Message;

import java.util.List;

@RestController
@RequestMapping("api/messages/")
public class MessageSender {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MessageSender.class);

    private final JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/send")
    public void sendMessages(@RequestBody List<Message> messages) {

        for (Message m : messages) {
            jmsTemplate.convertAndSend(m.getQueue(), messages);
            LOGGER.info("sending message='{}'", m);
        }
    }

}
