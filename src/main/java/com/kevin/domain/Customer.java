package com.kevin.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String email;

    @Column(name = "CREATED_DATE")
    Date date;

    //getters and setters, contructors
}
