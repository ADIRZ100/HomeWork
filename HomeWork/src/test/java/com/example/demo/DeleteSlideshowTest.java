package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeleteSlideshowTest {

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
    public void testDeleteSlideshow_Success() throws Exception {
        int existingSlideshowId = 1; // ID אמיתי

     
        Query mockQuery = org.mockito.Mockito.mock(Query.class);
        when(emAdd.createNativeQuery("DELETE FROM slideshow WHERE id = ?")).thenReturn(mockQuery);
        when(mockQuery.setParameter(1, existingSlideshowId)).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1); // מחיקה הצליחה

        mockMvc.perform(delete("/api/deleteSlideshow/" + existingSlideshowId))
                .andExpect(status().isOk())
                .andExpect(content().string("Slideshow and its images deleted successfully"));
    }

    @Test
    public void testDeleteSlideshow_NotFound() throws Exception {
        int nonExistingSlideshowId = 9999; // ID שלא קיים

        // יצירת mock לשאילתה
        Query mockQuery = org.mockito.Mockito.mock(Query.class);
        when(emAdd.createNativeQuery("DELETE FROM slideshow WHERE id = ?")).thenReturn(mockQuery);
        when(mockQuery.setParameter(1, nonExistingSlideshowId)).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(0); // לא נמצא שורות למחיקה

        mockMvc.perform(delete("/api/deleteSlideshow/" + nonExistingSlideshowId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Slideshow not found"));
    }

    @Test
    public void testDeleteSlideshow_ServerError() throws Exception {
        int invalidSlideshowId = -1; // ID לא תקין שעלול לגרום לשגיאה פנימית

        // Simulating an internal server error by throwing an exception
        when(emAdd.createNativeQuery("DELETE FROM slideshow WHERE id = ?")).thenReturn(null);
        when(emAdd.createNativeQuery("DELETE FROM slideshow WHERE id = ?")).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(delete("/api/deleteSlideshow/" + invalidSlideshowId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }
}
