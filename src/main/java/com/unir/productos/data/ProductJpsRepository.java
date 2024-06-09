package com.unir.productos.data;

import com.unir.productos.model.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface ProductJpsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByName (String name);
    List<Product> findByCategory (String category);
    List<Product> findByManufacturer (String manufacturer);
    List<Product> findByGuarantyMonths (Integer guarantyMonths);
    List<Product> findByPrice (Integer price);
}