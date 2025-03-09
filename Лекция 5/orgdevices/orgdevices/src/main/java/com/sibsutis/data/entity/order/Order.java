package com.sibsutis.data.entity.order;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "ordering_time")
    private Timestamp orderingTime;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    List<DetailedOrder> detailedOrder;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public List<DetailedOrder> getDetailedOrder() {
        return detailedOrder;
    }
}
