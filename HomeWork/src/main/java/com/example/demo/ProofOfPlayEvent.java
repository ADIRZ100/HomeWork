/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "ProofOfPlayEvent")
@NamedQueries({
    @NamedQuery(name = "ProofOfPlayEvent.findAll", query = "SELECT p FROM ProofOfPlayEvent p"),
    @NamedQuery(name = "ProofOfPlayEvent.findById", query = "SELECT p FROM ProofOfPlayEvent p WHERE p.id = :id"),
    @NamedQuery(name = "ProofOfPlayEvent.findByEventTime", query = "SELECT p FROM ProofOfPlayEvent p WHERE p.eventTime = :eventTime")})
public class ProofOfPlayEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "event_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime;
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PresentationImages imageId;
    @JoinColumn(name = "presentation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Presentations presentationId;

    public ProofOfPlayEvent() {
    }

    public ProofOfPlayEvent(Long id) {
        this.id = id;
    }

    public ProofOfPlayEvent(Long id, Date eventTime) {
        this.id = id;
        this.eventTime = eventTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public PresentationImages getImageId() {
        return imageId;
    }

    public void setImageId(PresentationImages imageId) {
        this.imageId = imageId;
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
        if (!(object instanceof ProofOfPlayEvent)) {
            return false;
        }
        ProofOfPlayEvent other = (ProofOfPlayEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.ProofOfPlayEvent[ id=" + id + " ]";
    }
    
}
