/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "img")
@NamedQueries({
    @NamedQuery(name = "Img.findAll", query = "SELECT i FROM Img i"),
    @NamedQuery(name = "Img.findById", query = "SELECT i FROM Img i WHERE i.id = :id"),
    @NamedQuery(name = "Img.findByUrl", query = "SELECT i FROM Img i WHERE i.url = :url"),
    @NamedQuery(name = "Img.findByDisplayDuration", query = "SELECT i FROM Img i WHERE i.displayDuration = :displayDuration")})
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "displayDuration")
    private int displayDuration;

    public Img() {
    }

    public Img(Integer id) {
        this.id = id;
    }

    public Img(Integer id, String url, int displayDuration) {
        this.id = id;
        this.url = url;
        this.displayDuration = displayDuration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDisplayDuration() {
        return displayDuration;
    }

    public void setDisplayDuration(int displayDuration) {
        this.displayDuration = displayDuration;
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
        if (!(object instanceof Img)) {
            return false;
        }
        Img other = (Img) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.Img[ id=" + id + " ]";
    }
    
}
