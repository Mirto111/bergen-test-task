package ru.example.bergen.task.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.bergen.task.config.TestConfig;
import ru.example.bergen.task.service.MessageService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MessageService service;

    @Test
    void getByDates() throws Exception {
        mockMvc.perform(get("/api/messages/get/by-dates?start=2021-06-04&end=2021-06-05")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[2].id", is(5)));
    }

    @Test
    void getByNameQueue() throws Exception {
        mockMvc.perform(get("/api/messages/get/by-queue/test-queue1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].queue", is("test-queue1")));
    }

    @Test
    void getByMessageId() throws Exception {
        mockMvc.perform(get("/api/messages/get/by-id/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].messageId", is(15)))
                .andExpect(jsonPath("$[1].messageId", is(15)));
    }

    @Test
    void getAllByMessageIdQueueDates() throws Exception {
        mockMvc.perform(get("/api/messages/get/by-param/message-id/16/queue/test-queue?start=2021-06-06&end=2021-06-07")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[1].id", is(4)));
    }
}