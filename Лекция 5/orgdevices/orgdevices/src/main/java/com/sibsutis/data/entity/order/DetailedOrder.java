package com.sibsutis.data.entity.order;

import jakarta.persistence.*;

@Entity
@Table(name = "detailed_orders", schema = "public")
public class DetailedOrder {
    @EmbeddedId
    private DetailedOrderPK orderPK;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "amount", nullable = false)
    private int amount;


    public DetailedOrderPK getOrderPK() {
        return orderPK;
    }

    public void setOrderPK(DetailedOrderPK orderPK) {
        this.orderPK = orderPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
