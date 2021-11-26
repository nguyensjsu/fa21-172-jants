package com.example.restAPI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

// follows same steps as spring-rest as well as StarbucksCard.java
@Entity
@Table
@Data
@RequiredArgsConstructor
public class StarbucksOrder {

    private @Id @GeneratedValue Long regid;

    @Column(nullable = false)     private String drink;
    @Column(nullable = false)     private String milk;
    @Column(nullable = false)     private String size;
    @Column(nullable = false)     private double total;
                                  private String status;
}