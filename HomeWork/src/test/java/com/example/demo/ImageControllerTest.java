package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class ImageControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddImage() throws Exception {
        mockMvc.perform(post("/api/addImage")
                .contentType("application/json")
                .content("{\"url\": \"https://example.com/image.jpg\", \"duration\": 10}"))
                .andExpect(status().isCreated()) // ציפייה לסטטוס 201, תמונה הועלתה בהצלחה
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"url\": \"https://example.com/image.jpg\", \"duration\": 10}"));
    }
}
