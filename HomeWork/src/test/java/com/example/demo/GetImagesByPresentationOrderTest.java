package com.example.demo;

import com.example.demo.SlideshowController;
import com.example.demo.PresentationImagesDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetImagesByPresentationOrderTest {

    @Autowired
    private SlideshowController slideshowController;

    @MockBean
    private EntityManager emAdd;

    @Test
    public void testGetImagesByPresentationOrder() {
        Integer presentationId = 1;
        Object[] result1 = {1, "http://example.com/image1.jpg", 5};
        Object[] result2 = {2, "http://example.com/image2.jpg", 10};
        List<Object[]> mockResults = Arrays.asList(result1, result2);

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.when(mockQuery.getResultList()).thenReturn(mockResults);
        Mockito.when(emAdd.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

        ResponseEntity<List<PresentationImagesDTO>> response = slideshowController.getImagesByPresentationOrder(presentationId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        List<PresentationImagesDTO> dtoList = response.getBody();
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getId()).isEqualTo(1);
        assertThat(dtoList.get(0).getImageUrl()).isEqualTo("http://example.com/image1.jpg");
        assertThat(dtoList.get(0).getDisplayDuration()).isEqualTo(5);
    }

    @Test
    public void testGetImagesByPresentationOrder_Exception() {
        Mockito.when(emAdd.createNativeQuery(Mockito.anyString())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<PresentationImagesDTO>> response = slideshowController.getImagesByPresentationOrder(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }
}
