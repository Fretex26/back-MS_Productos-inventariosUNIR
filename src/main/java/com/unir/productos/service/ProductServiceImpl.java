package com.unir.productos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.productos.data.ProductRepository;
import com.unir.productos.model.pojo.Product;
import com.unir.productos.model.pojo.ProductDTO;
import com.unir.productos.model.request.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductsService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Product> getProducts(String name, String category, String manufacturer, int guarantyMonths, int price) {
        if (StringUtils.hasLength(name) || StringUtils.hasLength(category) || StringUtils.hasLength(manufacturer)
                || io.micrometer.common.util.StringUtils.isNotBlank(String.valueOf(guarantyMonths)) && guarantyMonths > 0
                || io.micrometer.common.util.StringUtils.isNotBlank(String.valueOf(price)) && price > 0) {
            return productRepository.search(name, category, manufacturer, guarantyMonths, price);
        }

        List<Product> products = productRepository.getAllProducts();
        return products.isEmpty() ? null : products;
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.getProductById(Long.parseLong(id));
    }

    @Override
    public Product addProduct(CreateProductRequest request) {
        if (request != null && StringUtils.hasLength(request.getName().trim())
                && StringUtils.hasLength(request.getCategory().trim())
                && StringUtils.hasLength(request.getManufacturer().trim())
                && request.getGuarantyMonths() > 0
                && request.getPrice() > 0) {

            Product product = Product.builder().name(request.getName()).category(request.getCategory())
                    .manufacturer(request.getManufacturer()).guarantyMonths(request.getGuarantyMonths())
                    .price(request.getPrice()).build();

            return productRepository.saveProduct(product);
        } else {
            return null;
        }
    }

    @Override
    public Product updateProduct(String id, String updateRequest) {
        Product product = productRepository.getProductById(Long.valueOf(id));
        if (product != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(updateRequest));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(product)));
                Product patched = objectMapper.treeToValue(target, Product.class);
                productRepository.saveProduct(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating product {}", id, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Product updateProduct(String id, ProductDTO updateRequest) {
        Product product = productRepository.getProductById(Long.valueOf(id));
        if (product != null) {
            product.update(updateRequest);
            productRepository.saveProduct(product);
            return product;
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteProduct(String id) {
        Product product = productRepository.getProductById(Long.valueOf(id));

        if (product != null) {
            productRepository.deleteProduct(product);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}