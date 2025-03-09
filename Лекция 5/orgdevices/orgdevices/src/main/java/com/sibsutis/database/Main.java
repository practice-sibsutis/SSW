package com.sibsutis.database;

import com.sibsutis.database.Service.*;
import com.sibsutis.database.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("Usage: <program name> <jdbc:url> <username> <password>");
            return;
        }
        try(Connection connection = DriverManager.getConnection(args[0], args[1], args[2])) {
            OrdersService service = new OrdersService(connection);
            CustomerFactory customerFactory = new CustomerFactory(service.getLastCustomerId() + 1);
            ProductFactory productFactory = new ProductFactory(service.getLastProductId() +1);
            ShopFactory shopFactory = new ShopFactory(service.getLastShopId() + 1);
            OrderFactory orderFactory = new OrderFactory(service.getLastOrderId() + 1);
            FeedbackFactory feedbackFactory = new FeedbackFactory();

            System.out.println("Adding data...");

            List<Customer> customers = List.of(
                    customerFactory.createCustomer("phone1", "email1", "Anton1", "address1"),
                    customerFactory.createCustomer("phone2", "email2", "Anton2", "address2"),
                    customerFactory.createCustomer("phone3", "email3", "Anton3", "address3")
            );

            List<Product> products = List.of(
                    productFactory.createProduct("product1", 1000, "description1"),
                    productFactory.createProduct("product2", 500, "description2"),
                    productFactory.createProduct("product3", 6000, "description3")
            );

            List<Shop> shops = List.of(
                    shopFactory.createShop("address1", "name1", "phone1", "time1"),
                    shopFactory.createShop("address2", "name2", "phone2", "time2"),
                    shopFactory.createShop("address3", "name3", "phone3", "time3"),
                    shopFactory.createShop("address4", "name4", "phone4", "time4")
            );

            service.addCustomers(customers);
            service.addProducts(products);
            service.addShops(shops);

            List<Order> orders = List.of(
                    orderFactory.createOrder(service.getProductsByName("product1").getFirst(),
                    service.getCustomersByName("Anton1").getFirst(), 2,
                            service.getShopsByAddress("address3").getFirst(), ZonedDateTime.now()),

                    orderFactory.createOrder(service.getProductsByName("product2").getFirst(),
                            service.getCustomersByName("Anton1").getFirst(), 1,
                            service.getShopsByAddress("address3").getFirst(), ZonedDateTime.now()),

                    orderFactory.createOrder(service.getProductsByName("product2").getFirst(),
                            service.getCustomersByName("Anton1").getFirst(), 2,
                            service.getShopsByAddress("address3").getFirst(), ZonedDateTime.now()),

                    orderFactory.createOrder(service.getProductsByName("product3").getFirst(),
                            service.getCustomersByName("Anton2").getFirst(), 1,
                            service.getShopsByAddress("address3").getFirst(), ZonedDateTime.now())
            );

            service.addOrders(orders);

            List<Feedback> feedbacks = List.of(
                    feedbackFactory.createFeedback(orders.getFirst(), service.getCustomersByName("Anton1").getFirst(),
                            service.getProductsByName("product1").getFirst(), ZonedDateTime.now(), (short)3, "OK"),


                    feedbackFactory.createFeedback(orders.get(2), service.getCustomersByName("Anton1").getFirst(),
                            service.getProductsByName("product2").getFirst(), ZonedDateTime.now(), (short)3, "OK")
            );

            service.addFeedbacks(feedbacks);

            System.out.println("Success!");

            Map<Shop, Integer> shopsWithGreaterOrder = service.getShopsWithGreaterOrder();

            System.out.println("Shops with greater order:");
            for(Map.Entry<Shop, Integer> entry : shopsWithGreaterOrder.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            System.out.println();

            Map<Product, Integer> orductsWithMaxFeedbacks = service.getProductsWithMaxFeedbacks();

            System.out.println("Products with max feedbacks:");
            for(Map.Entry<Product, Integer> entry : orductsWithMaxFeedbacks.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            System.out.println();

            Map<Product, Double> orductsWithMinRaiting = service.getProductsWithMinRaiting();

            System.out.println("Products with min avg raiting:");
            for(Map.Entry<Product, Double> entry : orductsWithMinRaiting.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            connection.createStatement().execute("TRUNCATE TABLE public.customers CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE products CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE shops CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE orders CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE feedbacks CASCADE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
