package com.sibsutis.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private int id;
    private String email;
    private String phone;
    private String name;
    private String address;
    private List<Order> orders;

    public Customer(){
        this.orders = new ArrayList<>();
    }

    public Customer(int id, String email, String phone, String name, String address, List<Order> orders) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.address = address;

        this.orders = Objects.requireNonNullElseGet(orders, ArrayList::new);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return id == customer.id &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone, name, address, orders);
    }
}
