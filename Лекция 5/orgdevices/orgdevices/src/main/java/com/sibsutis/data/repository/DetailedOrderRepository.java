package com.sibsutis.data.repository;

import com.sibsutis.data.entity.order.DetailedOrder;
import com.sibsutis.data.entity.order.DetailedOrderPK;
import org.springframework.data.repository.CrudRepository;

public interface DetailedOrderRepository  extends CrudRepository<DetailedOrder, DetailedOrderPK> {
}
