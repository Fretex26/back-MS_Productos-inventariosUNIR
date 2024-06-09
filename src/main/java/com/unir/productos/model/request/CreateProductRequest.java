package com.unir.productos.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String category;
    private String manufacturer;
    private int guarantyMonths;
    private int price;
}
