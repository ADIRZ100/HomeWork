/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasess;

public class PresentationImagesDTO {
    private Integer id;
    private String imageUrl;
    private Integer displayDuration;

    // Constructor
    public PresentationImagesDTO(Integer id, String imageUrl, Integer displayDuration) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.displayDuration = displayDuration;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getDisplayDuration() {
        return displayDuration;
    }

    public void setDisplayDuration(Integer displayDuration) {
        this.displayDuration = displayDuration;
    }
}



