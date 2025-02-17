/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PresentationImages")
@NamedQueries({
    @NamedQuery(name = "PresentationImages.findAll", query = "SELECT p FROM PresentationImages p"),
    @NamedQuery(name = "PresentationImages.findById", query = "SELECT p FROM PresentationImages p WHERE p.id = :id"),
    @NamedQuery(name = "PresentationImages.findByImageUrl", query = "SELECT p FROM PresentationImages p WHERE p.imageUrl = :imageUrl"),
    @NamedQuery(name = "PresentationImages.findByDisplayDuration", query = "SELECT p FROM PresentationImages p WHERE p.displayDuration = :displayDuration")})
public class PresentationImages implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "imageId")
    private Collection<ProofOfPlayEvent> proofOfPlayEventCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "imageUrl")
    private String imageUrl;
    @Basic(optional = false)
    @Column(name = "displayDuration")
    private int displayDuration;
    @JoinColumn(name = "presentation_id", referencedColumnName = "id")
    @ManyToOne
    private Presentations presentationId;

    public PresentationImages() {
    }

    public PresentationImages(Integer id) {
        this.id = id;
    }

    public PresentationImages(Integer id, String imageUrl, int displayDuration) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.displayDuration = displayDuration;
    }

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

    public int getDisplayDuration() {
        return displayDuration;
    }

    public void setDisplayDuration(int displayDuration) {
        this.displayDuration = displayDuration;
    }

    public Presentations getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(Presentations presentationId) {
        this.presentationId = presentationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresentationImages)) {
            return false;
        }
        PresentationImages other = (PresentationImages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.PresentationImages[ id=" + id + " ]";
    }

    public Collection<ProofOfPlayEvent> getProofOfPlayEventCollection() {
        return proofOfPlayEventCollection;
    }

    public void setProofOfPlayEventCollection(Collection<ProofOfPlayEvent> proofOfPlayEventCollection) {
        this.proofOfPlayEventCollection = proofOfPlayEventCollection;
    }
    
}
