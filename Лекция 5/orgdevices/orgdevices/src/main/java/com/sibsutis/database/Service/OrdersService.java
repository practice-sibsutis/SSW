package com.sibsutis.database.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sibsutis.database.model.*;

public class OrdersService {
    private final Connection connection;
    private final String insertCustomerQuery =
            """
            INSERT INTO public.customers(customer_id, email, phone, name, delivery_address) VALUES (?, ?, ?, ?, ?)
            """;

    private final String insertProductQuery =
            """
            INSERT INTO public.products(product_id, name, cost, description) VALUES (?, ?, ?, ?)
            """;

    private final String insertShopQuery =
            """
                    	INSERT INTO public.shops(
                    	shop_id, address, directors_name, phone, working_time)
                    	VALUES(?,?,?,?,?)
            """;

    private final String insertOrderQuery =
            """
            INSERT INTO public.orders(order_id, product_id, customer_id, quantity, amount, shop_id, ordering_time)
                    	VALUES(?,?,?,?,?,?,?)
            """;

    private final String insertFeedbackQuery =
            """
                    	INSERT INTO public.feedbacks(
                    	order_id, customer_id, product_id, datetime, raiting, feedback_text)
                    	VALUES (?,?,?,?,?,?)
            """;

    public OrdersService(Connection connection)
    {
        if(connection == null) {
            throw new IllegalArgumentException("Connection is null");
        }

        this.connection = connection;
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(insertCustomerQuery)) {
            return addCustomer(customer, statement);
        }
    }

    public int addCustomers(List<Customer> customers) throws SQLException {
        int result = 0;
        try(PreparedStatement statement = connection.prepareStatement(insertCustomerQuery)) {
            for (Customer customer : customers) {
                result += addCustomer(customer, statement) ? 1 : 0;
            }
        }
        return result;
    }

    public boolean addProduct(Product product) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(insertProductQuery)) {
            return addProduct(product, statement);
        }
    }

    public int addProducts(List<Product> products) throws SQLException {
        int result = 0;
        try(PreparedStatement statement = connection.prepareStatement(insertProductQuery)) {
            for (Product product : products) {
                result += addProduct(product, statement) ? 1 : 0;
            }
        }
        return result;
    }

    public boolean addShop(Shop shop) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(insertShopQuery)) {
            return addShop(shop, statement);
        }
    }

    public int addShops(List<Shop> shops) throws SQLException {
        int result = 0;
        try(PreparedStatement statement = connection.prepareStatement(insertShopQuery)) {
            for (Shop shop : shops) {
                result += addShop(shop, statement) ? 1 : 0;
            }
        }
        return result;
    }

    public boolean addOrder(Order order) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(insertOrderQuery)) {
            return addOrder(order, statement);
        }
    }

    public int addOrders(List<Order> orders) throws SQLException {
        int result = 0;
        try(PreparedStatement statement = connection.prepareStatement(insertOrderQuery)) {
            for (Order order : orders) {
                result += addOrder(order, statement) ? 1 : 0;
            }
        }
        return result;
    }

    public boolean addFeedback(Feedback feedback) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(insertFeedbackQuery)) {
            return addFeedback(feedback, statement);
        }
    }

    public int addFeedbacks(List<Feedback> feedbacks) throws SQLException {
        int result = 0;
        try(PreparedStatement statement = connection.prepareStatement(insertFeedbackQuery)) {
            for (Feedback feedback : feedbacks) {
                result += addFeedback(feedback, statement) ? 1 : 0;
            }
        }
        return result;
    }

    public List<Product> getProductsByName(String name) throws SQLException {
        String query =
                """
                        SELECT product_id, name, cost, description
                        FROM products
                        WHERE name = ?;
                        """;
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("cost"),
                        resultSet.getString("description")));
            }

            return products;
        }
    }

    public List<Customer> getCustomersByName(String name) throws SQLException {
        String query =
                """
                        SELECT customer_id, email, phone, name, delivery_address
                        FROM customers
                        WHERE name = ?;
                        """;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("delivery_address")));
            }

            return customers;
        }
    }

    public List<Shop> getShopsByAddress(String address) throws SQLException {
        String query =
                """
                        SELECT shop_id, address, directors_name, phone, working_time
                        FROM shops
                        WHERE address = ?;
                        """;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, address);
            ResultSet resultSet = statement.executeQuery();
            List<Shop> shops = new ArrayList<>();
            while (resultSet.next()) {
                shops.add(new Shop(resultSet.getInt("shop_id"),
                        resultSet.getString("address"),
                        resultSet.getString("directors_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("working_time")));
            }

            return shops;
        }
    }

    public int getLastShopId() throws SQLException {
        String selectLast =
                """
                        SELECT shop_id FROM shops ORDER BY shop_id DESC LIMIT 1;
                        """;
        ResultSet resultSet = connection.createStatement().executeQuery(selectLast);
        int lastId = 0;

        while(resultSet.next()) {
            lastId = resultSet.getInt("shop_id");
        }
        return lastId;
    }

    public int getLastProductId() throws SQLException {
        String selectLast =
                """
                        SELECT product_id FROM products ORDER BY product_id DESC LIMIT 1;
                        """;
        ResultSet resultSet = connection.createStatement().executeQuery(selectLast);
        int lastId = 0;

        while(resultSet.next()) {
            lastId = resultSet.getInt("product_id");
        }
        return lastId;
    }

    public int getLastCustomerId() throws SQLException {
        String selectLast =
                """
                        SELECT customer_id FROM customers ORDER BY customer_id DESC LIMIT 1;
                        """;
        ResultSet resultSet = connection.createStatement().executeQuery(selectLast);
        int lastId = 0;

        while(resultSet.next()) {
            lastId = resultSet.getInt("customer_id");
        }
        return lastId;
    }

    public int getLastOrderId() throws SQLException {
        String selectLast =
                """
                        SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1;
                        """;
        ResultSet resultSet = connection.createStatement().executeQuery(selectLast);
        int lastId = 0;

        while(resultSet.next()) {
            lastId = resultSet.getInt("order_id");
        }
        return lastId;
    }

    public Map<Shop, Integer> getShopsWithGreaterOrder() throws SQLException {
        String selectShopsWithGreaterOrderCountQuery = """
                   SELECT shops.shop_id, any_value(shops.address) as address,
                   any_value(shops.directors_name) as directors_name,
                   any_value(shops.phone) as phone,
                   any_value(shops.working_time) as working_time,
                   COUNT(orders.shop_id) as count_orders
                   FROM shops
                   LEFT JOIN orders
                   ON shops.shop_id = orders.shop_id
                   GROUP BY shops.shop_id
                   ORDER BY count_orders DESC;
                """;
        try(PreparedStatement statement = connection.prepareStatement(selectShopsWithGreaterOrderCountQuery)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Shop, Integer> shops = new LinkedHashMap<>();
            while (resultSet.next()) {
                shops.put(new Shop(resultSet.getInt("shop_id"),
                        resultSet.getString("address"),
                        resultSet.getString("directors_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("working_time")), resultSet.getInt("count_orders"));
            }

            return shops;
        }
    }

    public Map<Product, Integer> getProductsWithMaxFeedbacks() throws SQLException {
        String selectProductWithMaxFeedbacksQuery =
                """
                        SELECT products.product_id as product_id,
                        any_value(products.name) as name,
                        any_value(products.cost) as cost,
                        any_value(products.description) as description,
                        COUNT(feedbacks.product_id) as count_reviews
                        FROM products
                        LEFT JOIN feedbacks
                        ON products.product_id = feedbacks.product_id
                        GROUP BY products.product_id
                        ORDER BY count_reviews DESC;
                """;

        try(PreparedStatement statement = connection.prepareStatement(selectProductWithMaxFeedbacksQuery)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Product, Integer> products = new LinkedHashMap<>();
            while (resultSet.next()) {
                products.put(new Product(resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("cost"),
                        resultSet.getString("description")),
                        resultSet.getInt("count_reviews"));
            }

            return products;
        }
    }

    public Map<Product, Double> getProductsWithMinRaiting() throws SQLException {
        String selectProductWithMinRaitingQuery =
                """
                        SELECT products.product_id, any_value(products.name) as name,
                        any_value(products.cost) as cost,
                        any_value(products.description) as description,
                        COALESCE(AVG(feedbacks.raiting), 0) as average_rating
                        FROM products
                        LEFT JOIN feedbacks
                        ON products.product_id = feedbacks.product_id
                        GROUP BY products.product_id
                        ORDER BY average_rating;
                """;

        try(PreparedStatement statement = connection.prepareStatement(selectProductWithMinRaitingQuery)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Product, Double> products = new LinkedHashMap<>();
            while (resultSet.next()) {
                products.put(new Product(resultSet.getInt("product_id"),
                                resultSet.getString("name"),
                                resultSet.getInt("cost"),
                                resultSet.getString("description")),
                        resultSet.getDouble("average_rating"));
            }

            return products;
        }
    }

    private boolean addCustomer(Customer customer, PreparedStatement statement) throws SQLException {
        statement.setInt(1, customer.id());
        statement.setString(2, customer.email());
        statement.setString(3, customer.phone());
        statement.setString(4, customer.name());
        statement.setString(5, customer.deliveryAddress());
        return statement.executeUpdate() == 1;
    }

    private boolean addProduct(Product product, PreparedStatement statement) throws SQLException {
        statement.setInt(1, product.id());
        statement.setString(2, product.name());
        statement.setInt(3, product.cost());
        statement.setString(4, product.description());
        return statement.executeUpdate() == 1;
    }

    private boolean addShop(Shop shop, PreparedStatement statement) throws SQLException {
        statement.setInt(1, shop.id());
        statement.setString(2, shop.address());
        statement.setString(3, shop.directorsName());
        statement.setString(4, shop.phone());
        statement.setString(5, shop.workingTime());
        return statement.executeUpdate() == 1;
    }

    private boolean addOrder(Order order, PreparedStatement statement) throws SQLException {
        statement.setInt(1, order.id());
        statement.setInt(2, order.productId());
        statement.setInt(3, order.customerId());
        statement.setInt(4, order.quantity());
        statement.setInt(5, order.amount());
        statement.setInt(6, order.shopId());
        statement.setTimestamp(7, Timestamp.from(order.orderingTime().toInstant()));
        return statement.executeUpdate() == 1;
    }

    private boolean addFeedback(Feedback feedback, PreparedStatement statement) throws SQLException {
        statement.setInt(1, feedback.orderId());
        statement.setInt(2, feedback.customerId());
        statement.setInt(3, feedback.productId());
        statement.setTimestamp(4, Timestamp.from(feedback.dateTime().toInstant()));
        statement.setInt(5, feedback.raiting());
        statement.setString(6, feedback.feedbackText());
        return statement.executeUpdate() == 1;
    }
}
