package com.sibsutis.data.entity.order;

import com.sibsutis.data.entity.Product;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetailedOrderPK implements Serializable {
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public DetailedOrderPK() {
    }

    public DetailedOrderPK(int orderId, Product product) {
        this.orderId = orderId;
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return product;
    }

    public void setProductId(Product product) {
        this.product = product;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailedOrderPK orderPK)) return false;
        return orderId == orderPK.orderId &&
                product.equals(orderPK.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, product);
    }
}
