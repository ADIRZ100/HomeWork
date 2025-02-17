package com.example.demo;

import com.example.demo.PresentationImages;
import com.example.demo.Presentations;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-17T19:15:33")
@StaticMetamodel(ProofOfPlayEvent.class)
public class ProofOfPlayEvent_ { 

    public static volatile SingularAttribute<ProofOfPlayEvent, Presentations> presentationId;
    public static volatile SingularAttribute<ProofOfPlayEvent, PresentationImages> imageId;
    public static volatile SingularAttribute<ProofOfPlayEvent, Date> eventTime;
    public static volatile SingularAttribute<ProofOfPlayEvent, Long> id;

}