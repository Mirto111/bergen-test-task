package ru.example.bergen.task.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.example.bergen.task.model.Message;
import ru.example.bergen.task.service.MessageService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/messages/get/")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/by-param/message-id/{message}/queue/{queue}")
    public List<Message> getAllByMessageIdQueueDates(@PathVariable Integer message,
                                                     @PathVariable String queue,
    @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("end")  LocalDate end) throws IllegalAccessException {
        return service.getAllByMessageIdQueueDates(message, queue, start, end);
    }

    @GetMapping("/by-dates")
    public List<Message> getByDates(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("end") LocalDate end) throws IllegalAccessException {
        return service.getAllBetweenDates(start, end);
    }

    @GetMapping("/by-queue/{name}")
    public List<Message> getByNameQueue(@PathVariable String name) throws IllegalAccessException {
        return service.getAllByQueue(name);
    }

    @GetMapping("/by-id/{id}")
    public List<Message> getByMessageId(@PathVariable Integer id) {
        return service.getAllByMessageId(id);
    }
}
