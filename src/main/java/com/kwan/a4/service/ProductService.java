package com.kwan.a4.service;


import com.kwan.a4.domain.Product;
import com.kwan.a4.repository.ProductRepository;

import java.util.List;


public class ProductService {

    private static final ProductService INSTANCE = new ProductService();

    private ProductRepository productRepository;

    private ProductService() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    public void init(String filePath) {
        productRepository = ProductRepository.getInstance();
        productRepository.setFilePath(filePath);
    }

    public void add(Product product) {
        productRepository.insertProduct(product);
    }

    public void update(Product product) {
        productRepository.updateProduct(product);
    }

    public Product delete(String code) {
        return productRepository.deleteProduct(code);
    }

    public List<Product> getList() {
        return productRepository.selectProducts();
    }

    public Product get(String code) {
        return productRepository.selectProduct(code);
    }

    public boolean exists(String code) {
        return productRepository.exists(code);
    }

}
