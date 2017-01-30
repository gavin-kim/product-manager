package com.kwan.a4.repository;


import com.kwan.a4.domain.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


// Product repository
public class ProductRepository {

    final private static ProductRepository INSTANCE = new ProductRepository();
    private List<Product> products = new ArrayList<>();
    private String filePath = null;

    private ProductRepository() {
    }

    public static ProductRepository getInstance() {
        return INSTANCE;
    }

    // Called once from the controller based on controller context
    public void setFilePath(String filePath) {
        this.filePath = getClass().getResource(filePath).getPath();
        products = selectProducts();
    }

    public List<Product> selectProducts() {
        products.clear();
        File file = new File(filePath);
        try {
            BufferedReader in =
                new BufferedReader(new FileReader(file));

            String line = in.readLine();
            while (line != null) {
                StringTokenizer t = new StringTokenizer(line, "|");
                if (t.countTokens() >= 3) {
                    String code = t.nextToken();
                    String description = t.nextToken();
                    String priceAsString = t.nextToken();
                    double price = Double.parseDouble(priceAsString);

                    Product p = new Product();
                    p.setCode(code);
                    p.setDescription(description);
                    p.setPrice(price);

                    products.add(p);
                }
                line = in.readLine();
            }
            in.close();
            return products;

        } catch (IOException e) {
            System.out.println(e);
            return products;
        }
    }

    public Product selectProduct(String code) {

        for (Product p : products) {
            if (code != null && code.equalsIgnoreCase(p.getCode())) {

                return p;
            }
        }
        return null;
    }

    public boolean exists(String code) {
        return selectProduct(code) != null;
    }

    private void saveProducts(List<Product> products) {
        try (PrintWriter out = new PrintWriter(new File(filePath))) {
            for (Product p : products) {
                out.println(p.getCode() + "|"
                    + p.getDescription() + "|"
                    + p.getPrice());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void insertProduct(Product product) {
        products = selectProducts();
        products.add(product);
        saveProducts(products);
    }

    public void updateProduct(Product product) {
        products = selectProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (product.getCode() != null
                && product.getCode().equalsIgnoreCase(p.getCode())) {

                products.set(i, product);
                saveProducts(products);
            }
        }

    }

    public Product deleteProduct(String code) {
        products = selectProducts();
        Product product = null;

        for (int i = 0; i < products.size(); i++) {

            Product p = products.get(i);

            if (code != null && code.equalsIgnoreCase(p.getCode())) {
                product = products.remove(i);
                saveProducts(products);
            }
        }
        return product;
    }
}
