package com.sibsutis.database.Service;

import com.sibsutis.database.model.Customer;
import com.sibsutis.database.model.Order;
import com.sibsutis.database.model.Product;
import com.sibsutis.database.model.Shop;

import java.time.ZonedDateTime;

public class OrderFactory {
    private int id;

    public OrderFactory(int id) {
        this.id = id;
    }

    public Order createOrder(Product product, Customer customer,
                             int quantity, Shop shop, ZonedDateTime date) {
        Order order = new Order(id, product.id(), customer.id(), quantity,
                quantity * product.cost(), shop.id(), date);
        ++id;
        return order;
    }
}
