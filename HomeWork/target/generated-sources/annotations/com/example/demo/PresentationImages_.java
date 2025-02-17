package com.example.demo;

import com.example.demo.Presentations;
import com.example.demo.ProofOfPlayEvent;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-17T19:15:33")
@StaticMetamodel(PresentationImages.class)
public class PresentationImages_ { 

    public static volatile SingularAttribute<PresentationImages, Integer> displayDuration;
    public static volatile SingularAttribute<PresentationImages, Presentations> presentationId;
    public static volatile SingularAttribute<PresentationImages, String> imageUrl;
    public static volatile CollectionAttribute<PresentationImages, ProofOfPlayEvent> proofOfPlayEventCollection;
    public static volatile SingularAttribute<PresentationImages, Integer> id;

}