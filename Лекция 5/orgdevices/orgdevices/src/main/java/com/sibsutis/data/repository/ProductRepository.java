package com.sibsutis.data.repository;

import com.sibsutis.data.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
