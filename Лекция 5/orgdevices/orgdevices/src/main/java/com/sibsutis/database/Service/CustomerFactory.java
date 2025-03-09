package com.sibsutis.database.Service;

import com.sibsutis.database.model.Customer;

public class CustomerFactory {
    private int id;

    public CustomerFactory(int id) {
        this.id = id;
    }

    public Customer createCustomer(String phone, String email, String name, String address ) {
        Customer customer = new Customer(id, phone, email, name, address);
        ++id;
        return customer;
    }
}
