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
public class SlideshowControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddSlideshow_Success() throws Exception {
        String requestBody = "{\"title\": \"My Slideshow\", \"imageUrls\": [\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"], \"displayDurations\": [5, 10]}";

        mockMvc.perform(post("/api/addSlideshow")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Slideshow added successfully"));
    }

    @Test
    public void testAddSlideshow_EmptyImages() throws Exception {
        String requestBody = "{\"title\": \"Empty Slideshow\", \"imageUrls\": [], \"displayDurations\": []}";

        mockMvc.perform(post("/api/addSlideshow")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
