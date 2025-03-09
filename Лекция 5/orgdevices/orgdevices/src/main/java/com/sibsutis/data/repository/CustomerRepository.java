package com.sibsutis.data.repository;

import com.sibsutis.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public Optional<Customer> findByName(String name);
}
