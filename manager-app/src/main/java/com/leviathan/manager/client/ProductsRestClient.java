package com.leviathan.manager.client;

import com.leviathan.manager.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClient {
    List<Product> findAllProducts();
    Product createProduct(String title, String details);
    Optional<Product> findProduct(int id);
    void updateProduct(int id, String title, String details);
    void deleteProduct(int id);
}
