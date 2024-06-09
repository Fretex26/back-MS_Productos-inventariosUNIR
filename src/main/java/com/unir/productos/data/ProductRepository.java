package com.unir.productos.data;

import com.unir.productos.data.utils.SearchCriteria;
import com.unir.productos.data.utils.SearchOperation;
import com.unir.productos.data.utils.SearchStatement;
import com.unir.productos.model.pojo.Product;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductJpsRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    public List<Product> search(String name, String category, String manufacturer, int guarantyMonths, int price) {
        SearchCriteria<Product> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(name)) {
            spec.add(new SearchStatement("name", name, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(category)) {
            spec.add(new SearchStatement("category", category, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(manufacturer)) {
            spec.add(new SearchStatement("manufacturer", manufacturer, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(String.valueOf(guarantyMonths)) && guarantyMonths > 0) {
            spec.add(new SearchStatement("guarantyMonths", guarantyMonths, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(String.valueOf(price)) && price > 0) {
            spec.add(new SearchStatement("price", price, SearchOperation.EQUAL));
        }
        return repository.findAll(spec);
    }

}
