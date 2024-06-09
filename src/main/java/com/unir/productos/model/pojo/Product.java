package com.unir.productos.model.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "guarantyMonths")
    private int guarantyMonths;

    @Column(name = "price")
    private int price;

    public void update(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.category = productDTO.getCategory();
        this.manufacturer = productDTO.getManufacturer();
        this.guarantyMonths = productDTO.getGuarantyMonths();
        this.price = productDTO.getPrice();
    }
}
