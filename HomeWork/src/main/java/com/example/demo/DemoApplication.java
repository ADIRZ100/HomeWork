package com.example.demo;

import static com.example.demo.DemoApplication.emAdd;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;


@SpringBootApplication
public class DemoApplication {
    // Create an EntityManagerFactory instance to connect to the database
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_PROJECTLOGIN_jar_0.0.1-SNAPSHOTPU");
    // Create an EntityManager instance to manage the entity operations
    static EntityManager emAdd = emf.createEntityManager();
    static EntityManager emAddTime = emf.createEntityManager();
    static Img addImage = new Img(); // A new image instance for adding a new image.

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args); // Start the Spring Boot application
    }
}

@RestController
@RequestMapping("/api")
class ImageSlideshowController {

    private long imageIdCounter = 1; // Counter for image ID
    private long slideshowIdCounter = 1; // Counter for slideshow ID

    // POST method to add an image
    @PostMapping("/addImage")
    public ResponseEntity<Map<String, Object>> addImage(@RequestBody ImageRequest request) {
        Map<String, Object> response = new HashMap<>(); // Response map to return success or failure messages

        // Validate if the image URL is correct
        if (!isValidImageUrl(request.getUrl())) {
            response.put("success", false);
            response.put("message", "Invalid image URL");
            return ResponseEntity.badRequest().body(response); // Return bad request if URL is invalid
        }
        
        Img addImage = new Img();
        addImage.setUrl(request.getUrl()); // Set the image URL
        addImage.setDisplayDuration(request.getDisplayDuration()); // Set the display duration
        
        emAdd.getTransaction().begin(); // Start a new transaction
        try {
            emAdd.persist(addImage);  // Save the image to the database
            emAdd.getTransaction().commit(); // Commit the transaction
            System.out.println("Image added successfully!");
            response.put("success", true);
            return ResponseEntity.ok(response); // Return success response
        } catch (Exception e) {
            e.printStackTrace();
            if (emAdd.getTransaction().isActive()) {
                emAdd.getTransaction().rollback(); // Rollback transaction if an error occurs
            }
            response.put("success", false);
            response.put("message", "An error occurred while adding the image");
            return ResponseEntity.status(500).body(response); // Return server error response
        }
    }

    // DELETE method to delete an image by its ID
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        try {
            Img image = emAdd.find(Img.class, id); // Find the image by ID
            if (image != null) {
                emAdd.getTransaction().begin(); // Start a new transaction
                emAdd.remove(image); // Remove the image from the database
                emAdd.getTransaction().commit(); // Commit the transaction
                return ResponseEntity.ok("Image deleted successfully"); // Return success response
            } else {
                return ResponseEntity.notFound().build(); // Return not found if image doesn't exist
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (emAdd.getTransaction().isActive()) {
                emAdd.getTransaction().rollback(); // Rollback transaction if an error occurs
            }
            return ResponseEntity.status(500).body("An error occurred while deleting the image"); // Return server error response
        }
    }

    // POST method to add a slideshow
    @PostMapping("/addSlideshow")
    public ResponseEntity<String> addSlideshow(@RequestBody SlideshowRequest request) {
        try {
            Presentations presentation = new Presentations(); // Create a new presentation instance
            presentation.setTitle(request.getTitle()); // Set the slideshow title

            emAdd.getTransaction().begin(); // Start a new transaction

            emAdd.persist(presentation); // Save the slideshow to the database

            // Save the images with their display durations
            List<String> imageUrls = request.getImageUrls();
            List<Integer> displayDurations = request.getDisplayDurations();

            for (int i = 0; i < imageUrls.size(); i++) {
                PresentationImages image = new PresentationImages();
                image.setImageUrl(imageUrls.get(i)); // Set the image URL
                image.setDisplayDuration(displayDurations.get(i)); // Set the image display duration
                image.setPresentationId(presentation); // Associate the image with the slideshow

                emAdd.persist(image); // Save each image to the database
            }

            emAdd.getTransaction().commit(); // Commit the transaction

            return ResponseEntity.ok("Slideshow added successfully"); // Return success response
        } catch (Exception e) {
            e.printStackTrace();
            if (emAdd.getTransaction().isActive()) {
                emAdd.getTransaction().rollback(); // Rollback transaction if an error occurs
            }
            return ResponseEntity.status(500).body("An error occurred while adding the slideshow"); // Return server error response
        }
    }

      // DELETE method to delete a slideshow and its images by slideshow ID
    @DeleteMapping("/deleteSlideshow/{id}")
    @Transactional
    public ResponseEntity<String> deleteSlideshow(@PathVariable Integer id) {
        try {
            Presentations presentation = emAdd.find(Presentations.class, id); // Find the slideshow by ID
            
            if (presentation == null) {
                return ResponseEntity.status(404).body("Slideshow not found"); // Return not found if slideshow doesn't exist
            }

            // Remove all associated images
            for (PresentationImages image : presentation.getPresentationImagesCollection()) {
                emAdd.remove(image); // Remove each image
            }

            emAdd.remove(presentation); // Remove the slideshow
            return ResponseEntity.ok("Slideshow and its images deleted successfully"); // Return success response
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while deleting the slideshow"); // Return server error response
        }
    }
    
    // GET method to search for images with optional URL and display duration filters
    @GetMapping("/images/search")
    public ResponseEntity<List<Img>> searchImages(@RequestParam(required = false) String url,
                                                   @RequestParam(required = false) Integer minDuration,
                                                   @RequestParam(required = false) Integer maxDuration) {
        try {
            StringBuilder queryStr = new StringBuilder("SELECT i FROM Img i WHERE 1=1"); // Build the query string
            List<Object> parameters = new ArrayList<>(); // List to hold query parameters

            // Add URL filter if provided
            if (url != null && !url.isEmpty()) {
                queryStr.append(" AND i.url LIKE ?1");
                parameters.add("%" + url + "%");
            }

            // Add minimum display duration filter if provided
            if (minDuration != null) {
                queryStr.append(" AND i.displayDuration >= ?2");
                parameters.add(minDuration);
            }

            // Add maximum display duration filter if provided
            if (maxDuration != null) {
                queryStr.append(" AND i.displayDuration <= ?3");
                parameters.add(maxDuration);
            }

            TypedQuery<Img> query = emAdd.createQuery(queryStr.toString(), Img.class); // Create a query with the dynamic string

            // Set parameters in the query
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }

            List<Img> results = query.getResultList(); // Execute the query and get the results

            return ResponseEntity.ok(results); // Return the results as a response
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Return server error if an exception occurs
        }
    }

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

    // POST method to register proof of play event
    @PostMapping("/slideShow/{id}/proof-of-play/{imageId}")
    @Transactional
    public String registerProofOfPlay(@PathVariable("id") Integer presentationId,
                                      @PathVariable("imageId") Integer imageId) {
        Presentations presentation = emAdd.find(Presentations.class, presentationId); // Find the presentation by ID
        if (presentation == null) {
            return "Presentation not found"; // Return error if presentation doesn't exist
        }

        PresentationImages image = emAdd.find(PresentationImages.class, imageId); // Find the image by ID
        if (image == null) {
            return "Image not found"; // Return error if image doesn't exist
        }

        ProofOfPlayEvent proofOfPlayEvent = new ProofOfPlayEvent(); // Create a new ProofOfPlayEvent
        proofOfPlayEvent.setPresentationId(presentation);
        proofOfPlayEvent.setImageId(image);



        try {
            emAdd.getTransaction().begin();
        // Convert LocalDateTime to Date for event time
        LocalDateTime now = LocalDateTime.now();
        Date eventTime = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        proofOfPlayEvent.setEventTime(eventTime);
            emAdd.persist(proofOfPlayEvent); // Save the ProofOfPlayEvent to the database
            emAdd.getTransaction().commit();
            return "Proof of play event registered successfully."; // Return success response
        } catch (Exception e) {
            e.printStackTrace();
            return "Error registering proof of play event."; // Return error if saving the event fails
        }
    }

    // Helper method to validate the image URL
    private boolean isValidImageUrl(String url) {
        return url.matches(".*\\.(jpg|jpeg|png|webp)$"); // Check if the URL ends with a valid image extension
    }
}
