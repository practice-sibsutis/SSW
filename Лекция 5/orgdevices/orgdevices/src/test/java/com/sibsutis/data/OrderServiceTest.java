package com.sibsutis.data;

import com.sibsutis.data.model.Customer;
import com.sibsutis.data.model.Order;
import com.sibsutis.data.model.Product;
import com.sibsutis.data.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {OrderServiceTest.Initializer.class})
public class OrderServiceTest {
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private OrderService orderService;

    @AfterEach
    void removeData() {
        String url = postgreSQLContainer.getJdbcUrl();
        String userName = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        try(Connection connection = DriverManager.getConnection(url, userName, password)) {
            connection.createStatement().execute("TRUNCATE TABLE public.customers CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE products CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE orders CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE detailed_orders CASCADE");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddCustomerAndGetAllCustomers() {
        var customer = new Customer();
        customer.setName("Customer1");
        customer.setEmail("Email customer1");
        customer.setAddress("Address customer1");
        customer.setPhone("Phone customer1");

        customer = orderService.addCustomer(customer);

        var expectedCustomers = List.of(customer);

        var actualCustomers = assertDoesNotThrow(() -> orderService.getAllCustomers());

        assertIterableEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void testAddProductAndGetAllProducts() {
        var product = new Product();
        product.setName("Product1");
        product.setCost(100);
        product.setDescription("Description product1");

        product = orderService.addProduct(product);

        var expectedProducts = List.of(product);

        var actualProducts = assertDoesNotThrow(() -> orderService.getAllProducts());

        assertIterableEquals(expectedProducts, actualProducts);

    }

    @Test
    void testAddProductAndGetProductByName() {
        var product = new Product();
        product.setName("Product1");
        product.setCost(100);
        product.setDescription("Description product1");

        product = orderService.addProduct(product);

        var actualProduct = assertDoesNotThrow(() -> orderService.getProductByName("Product1"));

        assertEquals(product, actualProduct);
    }

    @Test
    void testAddProductAndUpdateProductByName() {
        var product = new Product();
        product.setName("Product1");
        product.setCost(100);
        product.setDescription("Description product1");

        product = orderService.addProduct(product);

        product.setName("New name product1");
        product.setCost(200);
        product.setDescription("New description product1");

        orderService.updateProductByName("Product1", product);

        var actualProduct = assertDoesNotThrow(() -> orderService.getProductByName("New name product1"));

        assertEquals(product, actualProduct);
    }

    @Test
    void testAddCustomerAndGetCustomerByLogin() {
        var customer = new Customer();
        customer.setName("Customer1");
        customer.setEmail("Email customer1");
        customer.setAddress("Address customer1");
        customer.setPhone("Phone customer1");

        customer = orderService.addCustomer(customer);

        var actualCustomer = assertDoesNotThrow(() -> orderService.getCustomerByLogin("Customer1"));

        assertEquals(customer, actualCustomer);
    }

    @Test
    void testAddCustomerAndUpdateCustomerByLogin() {
        var customer = new Customer();
        customer.setName("Customer1");
        customer.setEmail("Email customer1");
        customer.setAddress("Address customer1");
        customer.setPhone("Phone customer1");

        customer = orderService.addCustomer(customer);

        customer.setPhone("New phone customer1");
        customer.setAddress("New address customer1");
        customer.setEmail("New email customer1");
        customer.setName("New name customer1");

        orderService.updateCustomerByLogin("Customer1", customer);

        var actualCustomer = assertDoesNotThrow(() -> orderService.getCustomerByLogin("New name customer1"));

        assertEquals(customer, actualCustomer);
    }

    @Test
    void testAddOrderByLogin() {
        var customer = new Customer();
        customer.setName("Customer1");
        customer.setEmail("Email customer1");
        customer.setAddress("Address customer1");
        customer.setPhone("Phone customer1");

        customer = orderService.addCustomer(customer);

        Map<Product, Integer> products = new HashMap<>();

        var product = new Product();
        product.setName("Product1");
        product.setCost(100);
        product.setDescription("Description product1");

        product = orderService.addProduct(product);
        products.put(product, 2);

        product = new Product();
        product.setName("Product2");
        product.setCost(1000);
        product.setDescription("Description product2");

        product = orderService.addProduct(product);
        products.put(product, 3);

        var order = new Order();
        var orderTime = new Timestamp(System.currentTimeMillis());

        order.setOrderingTime(orderTime);
        order.setTitle("Order for customer1");
        order.setProducts(products);

        customer.setOrders(List.of(order));

        order = orderService.addOrderByLogin("Customer1", order);

        var actualCustomer = assertDoesNotThrow(() -> orderService.getCustomerByLogin("Customer1"));

        assertEquals(customer, actualCustomer);
    }

}