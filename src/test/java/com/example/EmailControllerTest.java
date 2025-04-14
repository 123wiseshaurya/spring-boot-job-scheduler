package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testScheduleEmailJob() throws Exception {
        mockMvc.perform(post("/email/schedule")
                        .param("to", "test@example.com")
                        .param("subject", "Test Subject")
                        .param("body", "Test Body")
                        .param("time", "2025-04-12T10:30")
                        .param("jobType", "EMAIL")
                        .param("repeat", "ONCE")
                        .param("zoneId", "Asia/Kolkata"))
                .andExpect(status().isOk());
    }
}