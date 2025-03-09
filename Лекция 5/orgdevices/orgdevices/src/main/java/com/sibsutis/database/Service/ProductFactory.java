package com.sibsutis.database.Service;

import com.sibsutis.database.model.Product;

public class ProductFactory {
    private int id;

    public ProductFactory(int id) {
        this.id = id;
    }

    public Product createProduct(String name, int price, String description) {
        Product product = new Product(id, name, price, description);
        ++id;
        return product;
    }
}
