package com.sibsutis.data.service;

import com.sibsutis.data.model.Customer;
import com.sibsutis.data.model.Order;
import com.sibsutis.data.model.Product;
import com.sibsutis.data.repository.CustomerRepository;
import com.sibsutis.data.repository.DetailedOrderRepository;
import com.sibsutis.data.repository.OrderRepository;
import com.sibsutis.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DetailedOrderRepository detailedOrderRepository;

    public Product updateProductByName(String name, Product product) {
        com.sibsutis.data.entity.Product entityProduct = productRepository.findByName(name).orElseThrow();
        entityProduct.setName(product.getName());
        entityProduct.setCost(product.getCost());
        entityProduct.setDescription(product.getDescription());
        productRepository.save(entityProduct);
        return product;
    }

    public Customer updateCustomerByLogin(String login, Customer customer) {
        com.sibsutis.data.entity.Customer entityCustomer = customerRepository.findByName(login).orElseThrow();
        entityCustomer.setName(customer.getName());
        entityCustomer.setAddress(customer.getAddress());
        entityCustomer.setPhone(customer.getPhone());
        entityCustomer.setEmail(customer.getEmail());
        customerRepository.save(entityCustomer);

        return customer;
    }

    public List<Product> getAllProducts() {
        Iterable<com.sibsutis.data.entity.Product> entityProducts = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        for(com.sibsutis.data.entity.Product product : entityProducts) {
            products.add(new Product(product.getId(),
                    product.getName(),
                    product.getCost(),
                    product.getDescription()));
        }

        return products;
    }

    public List<Customer> getAllCustomers() {
        Iterable<com.sibsutis.data.entity.Customer> entityCustomers = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        for(com.sibsutis.data.entity.Customer entityCustomer : entityCustomers) {
            List<Order> orders = entityCustomer.getOrders().stream().map(order -> {
                Map<Product, Integer> products = new HashMap<>();
                for(com.sibsutis.data.entity.order.DetailedOrder detailedOrder : order.getDetailedOrder()) {
                    products.put(new Product(detailedOrder.getOrderPK().getProductId().getId(),
                                    detailedOrder.getOrderPK().getProductId().getName(),
                                    detailedOrder.getOrderPK().getProductId().getCost(),
                                    detailedOrder.getOrderPK().getProductId().getDescription()),
                            detailedOrder.getQuantity());
                }
                return new Order(order.getOrderId(), order.getOrderingTime(), order.getTitle(), products);
            }).toList();

            customers.add(
                    new Customer(entityCustomer.getCustomerId(),
                            entityCustomer.getEmail(),
                            entityCustomer.getPhone(),
                            entityCustomer.getName(),
                            entityCustomer.getAddress(),
                            orders)
            );
        }

        return customers;
    }


    public Product getProductByName(String name) {
        com.sibsutis.data.entity.Product entityProduct = productRepository.findByName(name).orElseThrow();
        return new Product(entityProduct.getId(),
                entityProduct.getName(),
                entityProduct.getCost(),
                entityProduct.getDescription());
    }

    public Customer getCustomerByLogin(String login) {
         com.sibsutis.data.entity.Customer entityCustomer = customerRepository.findByName(login).orElseThrow();
        List<Order> orders = entityCustomer.getOrders().stream().map(order -> {
            Map<Product, Integer> products = new HashMap<>();
            for(com.sibsutis.data.entity.order.DetailedOrder detailedOrder : order.getDetailedOrder()) {
                products.put(new Product(detailedOrder.getOrderPK().getProductId().getId(),
                                detailedOrder.getOrderPK().getProductId().getName(),
                                detailedOrder.getOrderPK().getProductId().getCost(),
                                detailedOrder.getOrderPK().getProductId().getDescription()),
                        detailedOrder.getQuantity());
            }
            return new Order(order.getOrderId(), order.getOrderingTime(), order.getTitle(), products);
        }).toList();

        return new Customer(entityCustomer.getCustomerId(),
                 entityCustomer.getEmail(),
                 entityCustomer.getPhone(),
                 entityCustomer.getName(),
                 entityCustomer.getAddress(),
                 orders);
    }

    public Order addOrderByLogin(String login, Order order) {
        com.sibsutis.data.entity.Customer entityCustomer = customerRepository.findByName(login).orElseThrow();
        com.sibsutis.data.entity.order.Order entityOrder = new com.sibsutis.data.entity.order.Order();
        entityOrder.setCustomerId(entityCustomer.getCustomerId());
        entityOrder.setOrderingTime(order.getOrderingTime());
        entityOrder.setTitle(order.getTitle());
        entityOrder = orderRepository.save(entityOrder);
        order.setId(entityOrder.getOrderId());

        for(Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            com.sibsutis.data.entity.Product entityProduct = productRepository.findById(entry.getKey().getId()).orElseThrow();
            com.sibsutis.data.entity.order.DetailedOrder detailedOrder = new com.sibsutis.data.entity.order.DetailedOrder();
            detailedOrder.setQuantity(entry.getValue());
            detailedOrder.setOrderPK(new com.sibsutis.data.entity.order.DetailedOrderPK(entityOrder.getOrderId(), entityProduct));
            detailedOrder.setAmount(entry.getKey().getCost() * entry.getValue());
            detailedOrderRepository.save(detailedOrder);
        }
        return order;
    }

    public Customer addCustomer(Customer customer) {
        com.sibsutis.data.entity.Customer entityCustomer = new com.sibsutis.data.entity.Customer();
        entityCustomer.setName(customer.getName());
        entityCustomer.setAddress(customer.getAddress());
        entityCustomer.setPhone(customer.getPhone());
        entityCustomer.setEmail(customer.getEmail());
        entityCustomer = customerRepository.save(entityCustomer);

        customer.setId(entityCustomer.getCustomerId());

        return customer;
    }

    public Product addProduct(Product product) {
        com.sibsutis.data.entity.Product entityProduct = new com.sibsutis.data.entity.Product();
        entityProduct.setName(product.getName());
        entityProduct.setCost(product.getCost());
        entityProduct.setDescription(product.getDescription());
        entityProduct = productRepository.save(entityProduct);

        product.setId(entityProduct.getId());

        return product;
    }
}
