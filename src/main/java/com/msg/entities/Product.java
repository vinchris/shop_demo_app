package com.msg.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

public class Product {

    private @Id
    @GeneratedValue Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;

    @OneToOne
    private Stock stock;
}
