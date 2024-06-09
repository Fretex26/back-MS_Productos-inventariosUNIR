package com.unir.productos.service;

import com.unir.productos.model.pojo.Product;
import com.unir.productos.model.pojo.ProductDTO;
import com.unir.productos.model.request.CreateProductRequest;

import java.util.List;

public interface ProductsService {
    List<Product> getProducts(String name, String category, String manufacturer, int guarantyMonths, int price);
    Product getProductById(String id);
    Product addProduct(CreateProductRequest request);
    Product updateProduct(String id, String updateRequest);
    Product updateProduct(String id, ProductDTO updateRequest);
    Boolean deleteProduct(String id);
}