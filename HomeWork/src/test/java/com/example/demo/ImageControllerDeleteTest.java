package com.example.demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class ImageControllerDeleteTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private EntityManager emAdd; // Mocking the EntityManager

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDeleteImage_Success() throws Exception {
        int existingImageId = 1; // יש לעדכן עם ID אמיתי במסד הנתונים

        Img mockImage = new Img();
        mockImage.setId(existingImageId);
        when(emAdd.find(Img.class, existingImageId)).thenReturn(mockImage);
        when(emAdd.getTransaction()).thenReturn((EntityTransaction) mock(Transaction.class));

        mockMvc.perform(delete("/deleteImage/" + existingImageId))
                .andExpect(status().isOk())
                .andExpect(content().string("Image deleted successfully"));
    }

    @Test
    public void testDeleteImage_NotFound() throws Exception {
        int nonExistingImageId = 9999; // ID שלא קיים

        when(emAdd.find(Img.class, nonExistingImageId)).thenReturn(null);

        mockMvc.perform(delete("/deleteImage/" + nonExistingImageId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteImage_ServerError() throws Exception {
        int invalidImageId = -1; // ID לא תקין שעלול לגרום לשגיאה פנימית

        when(emAdd.find(Img.class, invalidImageId)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(delete("/deleteImage/" + invalidImageId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while deleting the image"));
    }
}
