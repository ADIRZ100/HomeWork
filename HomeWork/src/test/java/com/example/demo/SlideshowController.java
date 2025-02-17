package com.example.demo;

import com.example.demo.PresentationImagesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SlideshowController {

    @Autowired
    private EntityManager emAdd;

    // GET method to retrieve images in order based on presentation ID
    @GetMapping("/slideshowImages/{presentationId}/order")
    public ResponseEntity<List<PresentationImagesDTO>> getImagesByPresentationOrder(@PathVariable Integer presentationId) {
        try {
            // SQL query to get images by presentation order
            String query = "SELECT p.id, p.imageUrl, p.displayDuration " +
                           "FROM PresentationImages p " +
                           "JOIN Presentations pr ON p.presentation_id = pr.id " +
                           "WHERE pr.id = ? " + 
                           "ORDER BY p.id ASC";
            
            Query typedQuery = emAdd.createNativeQuery(query); // Create a native SQL query
            typedQuery.setParameter(1, presentationId); // Set the presentation ID parameter
            
            List<Object[]> results = typedQuery.getResultList(); // Execute the query and get the results
            
            List<PresentationImagesDTO> dtoList = new ArrayList<>(); // Create a list of DTOs
            for (Object[] result : results) {
                Integer id = (Integer) result[0];
                String imageUrl = (String) result[1];
                Integer displayDuration = (Integer) result[2];
                
                PresentationImagesDTO dto = new PresentationImagesDTO(id, imageUrl, displayDuration); // Create DTOs from results
                dtoList.add(dto);
            }
            
            return ResponseEntity.ok(dtoList); // Return the DTO list as a response
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Return server error if an exception occurs
        }
    }
}
