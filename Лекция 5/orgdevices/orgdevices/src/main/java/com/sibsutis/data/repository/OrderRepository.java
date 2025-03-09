package com.sibsutis.data.repository;

import com.sibsutis.data.entity.order.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
