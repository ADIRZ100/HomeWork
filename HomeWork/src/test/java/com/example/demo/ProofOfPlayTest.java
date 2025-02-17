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
public class ProofOfPlayTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRegisterProofOfPlay_Success() throws Exception {
        int presentationId = 1; // ID של מצגת קיימת לבדיקה
        int imageId = 1; // ID של תמונה קיימת לבדיקה

        mockMvc.perform(post("/slideShow/" + presentationId + "/proof-of-play/" + imageId))
                .andExpect(status().isOk())
                .andExpect(content().string("Proof of play event registered successfully."));
    }

    @Test
    public void testRegisterProofOfPlay_PresentationNotFound() throws Exception {
        int nonExistentPresentationId = 999; // ID לא קיים
        int imageId = 1; // ID של תמונה קיימת לבדיקה

        mockMvc.perform(post("/slideShow/" + nonExistentPresentationId + "/proof-of-play/" + imageId))
                .andExpect(status().isOk())
                .andExpect(content().string("Presentation not found"));
    }

    @Test
    public void testRegisterProofOfPlay_ImageNotFound() throws Exception {
        int presentationId = 1; // ID של מצגת קיימת לבדיקה
        int nonExistentImageId = 999; // ID לא קיים

        mockMvc.perform(post("/slideShow/" + presentationId + "/proof-of-play/" + nonExistentImageId))
                .andExpect(status().isOk())
                .andExpect(content().string("Image not found"));
    }
}
