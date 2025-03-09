package com.sibsutis.data.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private int id;
    private Timestamp orderingTime;
    private String title;
    private Map<Product, Integer> products;

    public Order() {
        products = new HashMap<>();
    }

    public Order(int id, Timestamp orderingTime, String title, Map<Product, Integer> products) {
        this.id = id;
        this.orderingTime = orderingTime;
        this.title = title;

        this.products = Objects.requireNonNullElseGet(products, HashMap::new);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(Timestamp orderingTime) {
        this.orderingTime = orderingTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderingTime=" + orderingTime +
                ", title='" + title + '\'' +
                ", products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return id == order.id &&
                Objects.equals(orderingTime, order.orderingTime) &&
                Objects.equals(title, order.title) &&
                Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderingTime, title, products);
    }
}
