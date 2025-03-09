package com.sibsutis.database;

import com.sibsutis.database.Service.OrdersService;
import com.sibsutis.database.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@Testcontainers
class OrdersServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("testDB")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withInitScript("init.sql");

    @AfterEach
    void removeData() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            connection.createStatement().execute("TRUNCATE TABLE public.customers CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE products CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE shops CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE orders CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE feedbacks CASCADE");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddCustomers() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {

            List<Customer> customers = List.of(
                    new Customer(11, "phone1", "email1", "name1", "address1"),
                    new Customer(12, "phone2", "email2", "name2", "address2"),
                    new Customer(13, "phone3", "email3", "name3", "address3")
            );

            OrdersService ordersService = new OrdersService(connection);

            int result = assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertEquals(customers.size(), result);

            List<Customer> actualCustomers = getCustomers(connection);
            assertEquals(customers, actualCustomers);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testAddProducts() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {

            List<Product> products = List.of(
                    new Product(11, "product1", 1000, "description1"),
                    new Product(12, "product2", 500, "description2"),
                    new Product(13, "product3", 6000, "description3")
            );

            OrdersService ordersService = new OrdersService(connection);

            int result = assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertEquals(products.size(), result);

            List<Product> actualProducts = getProducts(connection);
            assertEquals(products, actualProducts);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testAddShops() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );

            OrdersService ordersService = new OrdersService(connection);

            int result = assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertEquals(shops.size(), result);
            List<Shop> actualShops = getShops(connection);
            assertEquals(shops, actualShops);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testAddOrders() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2"),
                    new Product(3, "product3", 6000, "description3")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(21, 2, 1, 1, 50000, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()))
            );

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            int result = assertDoesNotThrow(() -> ordersService.addOrders(orders));
            assertEquals(orders.size(), result);

            List<Order> actualOrders = getOrders(connection);
            assertEquals(orders, actualOrders);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testAddFeedbacks() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2"),
                    new Product(3, "product3", 6000, "description3")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 1, ZonedDateTime.now()),
                    new Order(21, 2, 1, 1, 50000, 1, ZonedDateTime.now())
            );
            List<Feedback> feedbacks = List.of(
                    new Feedback(21, 1, 1, ZonedDateTime.of(2024, 11,
                            19, 11,12,42,0,
                            ZoneId.systemDefault()), (short)5, "description1"),
                    new Feedback(21, 2, 1, ZonedDateTime.of(2024, 11,
                            19, 11,12,42,0,
                            ZoneId.systemDefault()), (short)5, "description2")
            );

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertDoesNotThrow(() -> ordersService.addOrders(orders));
            int result = assertDoesNotThrow(() -> ordersService.addFeedbacks(feedbacks));
            assertEquals(orders.size(), result);

            List<Feedback> actualFeedbacks = getFeedbacks(connection);
            assertEquals(feedbacks, actualFeedbacks);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetShopByAddress() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            List<Shop> actualShops = assertDoesNotThrow(() -> ordersService.getShopsByAddress("address1"));
            assertEquals(List.of(new Shop(1, "address1",
                    "dirName1", "phone1", "workingTime1")), actualShops);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetCustomersByName() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            List<Customer> actualCustomers = assertDoesNotThrow(() -> ordersService.getCustomersByName("name3"));
            assertEquals(List.of(new Customer(3, "phone3", "email3",
                    "name3", "address3")), actualCustomers);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetProductsByName() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Product> products = List.of(
                    new Product(11, "product1", 1000, "description1"),
                    new Product(12, "product2", 500, "description2"),
                    new Product(13, "product3", 6000, "description3")
            );

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            List<Product> actualProducts = assertDoesNotThrow(() -> ordersService.getProductsByName("product2"));
            assertEquals(List.of(new Product(12, "product2",
                    500, "description2")), actualProducts);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetLastCustomerId() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            int expectedId = 3;

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));


            int actualId = assertDoesNotThrow(() -> ordersService.getLastCustomerId());
            assertEquals(expectedId, actualId);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetLastProductId() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Product> products = List.of(
                    new Product(11, "product1", 1000, "description1"),
                    new Product(12, "product2", 500, "description2"),
                    new Product(13, "product3", 6000, "description3")
            );

            int expectedId = 13;

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            int actualId = assertDoesNotThrow(() -> ordersService.getLastProductId());
            assertEquals(expectedId, actualId);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetLastShopId() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );

            int expectedId = 4;

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            int actualId = assertDoesNotThrow(() -> ordersService.getLastShopId());
            assertEquals(expectedId, actualId);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetLastOrderId() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2"),
                    new Product(3, "product3", 6000, "description3")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(21, 2, 1, 1, 50000, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()))
            );

            int expectedId = 21;

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertDoesNotThrow(() -> ordersService.addOrders(orders));

            int actualId = assertDoesNotThrow(() -> ordersService.getLastOrderId());
            assertEquals(expectedId, actualId);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testGetShopsWithGreaterOrders() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2"),
                    new Product(3, "product3", 6000, "description3")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(21, 2, 1, 1, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()))
            );

            Map<Shop, Integer> expectedMap = new LinkedHashMap<>();
            expectedMap.put(shops.get(2), 2);
            expectedMap.put(shops.get(0), 0);
            expectedMap.put(shops.get(1), 0);
            expectedMap.put(shops.get(3), 0);

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertDoesNotThrow(() -> ordersService.addOrders(orders));

            Map<Shop, Integer> actualShops = assertDoesNotThrow(() -> ordersService.getShopsWithGreaterOrder());

            assertEquals(expectedMap, actualShops);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testProductsWithMaxFeedbacks() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2"),
                    new Product(3, "product3", 6000, "description3")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(21, 2, 1, 1, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(22, 1, 1, 2, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(22, 1, 2, 1, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()))
            );

            List<Feedback> feedbacks = List.of(
                    new Feedback(21, 1, 1,
                            ZonedDateTime.of(2024, 11,
                            19, 11,12,42,0,
                            ZoneId.systemDefault()), (short)3, "OK"),
                    new Feedback(22, 1, 2,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()), (short)3, "OK"),
                    new Feedback(21, 2, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()), (short)3, "OK")
            );

            Map<Product, Integer> expectedMap = new LinkedHashMap<>();
            expectedMap.put(products.get(0), 2);
            expectedMap.put(products.get(1), 1);
            expectedMap.put(products.get(2), 0);

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertDoesNotThrow(() -> ordersService.addOrders(orders));
            assertDoesNotThrow(() -> ordersService.addFeedbacks(feedbacks));

            Map<Product, Integer> actualShops = assertDoesNotThrow(() -> ordersService.getProductsWithMaxFeedbacks());

            assertEquals(expectedMap, actualShops);
        }
        catch (SQLException e) {
            fail();
        }
    }

    @Test
    void testProductsWithMinAvgRaiting() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            List<Customer> customers = List.of(
                    new Customer(1, "phone1", "email1", "name1", "address1"),
                    new Customer(2, "phone2", "email2", "name2", "address2"),
                    new Customer(3, "phone3", "email3", "name3", "address3")
            );
            List<Product> products = List.of(
                    new Product(1, "product1", 1000, "description1"),
                    new Product(2, "product2", 500, "description2")
            );
            List<Shop> shops = List.of(
                    new Shop(1, "address1", "dirName1", "phone1", "workingTime1"),
                    new Shop(2, "address2", "dirName2", "phone2", "workingTime2"),
                    new Shop(3, "address3", "dirName3", "phone3", "workingTime3"),
                    new Shop(4, "address4", "dirName4", "phone4", "workingTime4")
            );
            List<Order> orders = List.of(
                    new Order(21, 1, 1, 2, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(21, 2, 1, 1, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(22, 1, 1, 2, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault())),
                    new Order(22, 1, 2, 1, 50000, 3,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()))
            );

            List<Feedback> feedbacks = List.of(
                    new Feedback(21, 1, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()), (short)3, "OK"),
                    new Feedback(22, 1, 2,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()), (short)3, "OK"),
                    new Feedback(21, 2, 1,
                            ZonedDateTime.of(2024, 11,
                                    19, 11,12,42,0,
                                    ZoneId.systemDefault()), (short)2, "OK")
            );

            Map<Product, Double> expectedMap = new LinkedHashMap<>();
            expectedMap.put(products.get(1), 2.0);
            expectedMap.put(products.get(0), 3.0);

            OrdersService ordersService = new OrdersService(connection);
            assertDoesNotThrow(() -> ordersService.addCustomers(customers));
            assertDoesNotThrow(() -> ordersService.addProducts(products));
            assertDoesNotThrow(() -> ordersService.addShops(shops));
            assertDoesNotThrow(() -> ordersService.addOrders(orders));
            assertDoesNotThrow(() -> ordersService.addFeedbacks(feedbacks));

            Map<Product, Double> actualShops = assertDoesNotThrow(() -> ordersService.getProductsWithMinRaiting());

            assertEquals(expectedMap, actualShops);
        }
        catch (SQLException e) {
            fail();
        }
    }

    private List<Customer> getCustomers(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM public.customers";
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    customers.add(new Customer(resultSet.getInt("customer_id"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            resultSet.getString("delivery_address")));
                }
            }
        }
        return customers;
    }

    private List<Product> getProducts(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM public.products";
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    products.add(new Product(resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("cost"),
                            resultSet.getString("description")));
                }
            }
        }
        return products;
    }

    private List<Order> getOrders(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM public.orders";
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    orders.add(new Order(resultSet.getInt("order_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("amount"),
                            resultSet.getInt("shop_id"),
                            ZonedDateTime.ofInstant(resultSet.getTimestamp("ordering_time").toInstant(),
                                    ZoneId.systemDefault())));
                }
            }
        }
        return orders;
    }

    private List<Shop> getShops(Connection connection) throws SQLException {
        List<Shop> shops = new ArrayList<>();
        String query = "SELECT * FROM public.shops";
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    shops.add(new Shop(resultSet.getInt("shop_id"),
                            resultSet.getString("address"),
                            resultSet.getString("directors_name"),
                            resultSet.getString("phone"),
                            resultSet.getString("working_time")));
                }
            }
        }
        return shops;
    }

    private List<Feedback> getFeedbacks(Connection connection) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM public.feedbacks";
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    feedbacks.add(new Feedback(resultSet.getInt("order_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("customer_id"),
                            ZonedDateTime.ofInstant(resultSet.getTimestamp("datetime").toInstant(),
                                    ZoneId.systemDefault()),
                            resultSet.getShort("raiting"),
                            resultSet.getString("feedback_text")));
                }
            }
        }
        return feedbacks;
    }
}