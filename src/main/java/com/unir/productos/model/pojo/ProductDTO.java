package com.unir.productos.model.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {

    private String name;
    private String category;
    private String manufacturer;
    private int guarantyMonths;
    private int price;

}