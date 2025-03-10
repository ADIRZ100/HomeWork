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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "Presentations")
@NamedQueries({
    @NamedQuery(name = "Presentations.findAll", query = "SELECT p FROM Presentations p"),
    @NamedQuery(name = "Presentations.findById", query = "SELECT p FROM Presentations p WHERE p.id = :id"),
    @NamedQuery(name = "Presentations.findByTitle", query = "SELECT p FROM Presentations p WHERE p.title = :title")})
public class Presentations implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presentationId")
    private Collection<ProofOfPlayEvent> proofOfPlayEventCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "presentationId")
    private Collection<PresentationImages> presentationImagesCollection;

    public Presentations() {
    }

    public Presentations(Integer id) {
        this.id = id;
    }

    public Presentations(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<PresentationImages> getPresentationImagesCollection() {
        return presentationImagesCollection;
    }

    public void setPresentationImagesCollection(Collection<PresentationImages> presentationImagesCollection) {
        this.presentationImagesCollection = presentationImagesCollection;
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
        if (!(object instanceof Presentations)) {
            return false;
        }
        Presentations other = (Presentations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.Presentations[ id=" + id + " ]";
    }

    public Collection<ProofOfPlayEvent> getProofOfPlayEventCollection() {
        return proofOfPlayEventCollection;
    }

    public void setProofOfPlayEventCollection(Collection<ProofOfPlayEvent> proofOfPlayEventCollection) {
        this.proofOfPlayEventCollection = proofOfPlayEventCollection;
    }
    
}
