package com.example.demo;

import com.example.demo.PresentationImages;
import com.example.demo.ProofOfPlayEvent;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-17T19:15:33")
@StaticMetamodel(Presentations.class)
public class Presentations_ { 

    public static volatile CollectionAttribute<Presentations, ProofOfPlayEvent> proofOfPlayEventCollection;
    public static volatile SingularAttribute<Presentations, Integer> id;
    public static volatile SingularAttribute<Presentations, String> title;
    public static volatile CollectionAttribute<Presentations, PresentationImages> presentationImagesCollection;

}