/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.util.List;

 class SlideshowRequest {
    private String title;
    private List<String> imageUrls;
    private List<Integer> displayDurations;


    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<Integer> getDisplayDurations() {
        return displayDurations;
    }

    public void setDisplayDurations(List<Integer> displayDurations) {
        this.displayDurations = displayDurations;
    }
}