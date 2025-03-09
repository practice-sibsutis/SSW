package com.sibsutis.data;

import com.sibsutis.data.model.Customer;
import com.sibsutis.data.model.Order;
import com.sibsutis.data.model.Product;
import com.sibsutis.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Order System Started");
        Scanner scanner = new Scanner(System.in);
        String input;

        printMenu();
        while(!(input = scanner.next()).equalsIgnoreCase("exit")){
            input = input.toLowerCase();
            switch(input) {
                case "0":
                    System.out.println("Exiting...");
                    return;
                case "1":
                    System.out.println("Adding Product:");
                    Product product = createProduct();
                    if (product != null) {
                        orderService.addProduct(product);
                        System.out.println("Product Added");
                    }
                    break;

                case "2":
                    System.out.println("Adding Customer:");
                    Customer newCustomer = createCustomer();
                    if (newCustomer != null) {
                        orderService.addCustomer(newCustomer);
                        System.out.println("Customer Added");
                    }
                    break;

                case "3":
                    System.out.println("Adding Order:");
                    Order order = createOrder();
                    if (order != null) {
                        Customer customerForOrder = lookupCustomerByLogin();
                        orderService.addOrderByLogin(customerForOrder.getName(), order);
                        System.out.println("Order Added");
                    }
                    break;

                case "4":
                    System.out.println("Lookup Product by Name:");
                    Product productByName = lookupProductByName();
                    if (productByName != null) {
                        System.out.println(productByName);
                    }
                    break;

                case "5":
                    System.out.println("Lookup Customer by Login:");
                    Customer lookupCustomer = lookupCustomerByLogin();
                    if (lookupCustomer != null) {
                        System.out.println(lookupCustomer);
                    }
                    break;

                case "6":
                    System.out.println("All Products:");
                    List<Product> products = getAllProducts();
                    for (Product productsFromBase : products) {
                        System.out.println(productsFromBase);
                    }
                    break;

                case "7":
                    System.out.println("All Customers:");
                    List<Customer> customers = getAllCustomers();
                    for (Customer customerFromBase : customers) {
                        System.out.println(customerFromBase);
                    }
                    break;

                case "8":
                    System.out.println("Updating Product:");
                    updateProduct();
                    break;

                case "9":
                    System.out.println("Updating Customer:");
                    updateCustomer();
                    break;

                default:
                    System.out.println("Invalid Input");
                    break;
            }
            printMenu();
        }

    }

    private Product createProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        if(name.isEmpty()) {
            System.out.println("Product Name cannot be empty");
            return null;
        }

        product.setName(name);

        System.out.print("Enter Product Cost: ");
        try {
            int cost = Integer.parseInt(scanner.nextLine());
            product.setCost(cost);
        }
        catch (NumberFormatException e) {
            System.out.println("Cost must be a integer number");
            return null;
        }

        System.out.println("Enter description:");
        String description = scanner.nextLine();
        product.setDescription(description);
        return product;
    }

    private Customer createCustomer() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = new Customer();
        System.out.print("Enter Customer Name: ");

        String name = scanner.nextLine();

        if(name.isEmpty()) {
            System.out.println("Customer name cannot be empty");
            return null;
        }
        customer.setName(name);

        String email = scanner.nextLine();

        if(email.isEmpty()) {
            System.out.println("Customer email cannot be empty");
            return null;
        }
        customer.setEmail(email);

        String phone = scanner.nextLine();

        if(phone.isEmpty()) {
            System.out.println("Customer phone cannot be empty");
            return null;
        }
        customer.setPhone(phone);

        String address = scanner.nextLine();
        customer.setAddress(address);

        return customer;
    }

    private Customer lookupCustomerByLogin() {
        System.out.print("Enter Customer Login: ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        try {
            return orderService.getCustomerByLogin(login);
        }
        catch (NoSuchElementException e) {
            System.out.println("Customer not found");
            return null;
        }
    }

    private List<Product> getAllProducts() {
        try{
            return orderService.getAllProducts();
        }
        catch (NoSuchElementException e) {
            System.out.println("No products found");
            return new ArrayList<>();
        }
    }

    private List<Customer> getAllCustomers() {
        try {
            return orderService.getAllCustomers();
        }
        catch (NoSuchElementException e) {
            System.out.println("No customers found");
            return new ArrayList<>();
        }
    }

    private Product lookupProductByName() {
        System.out.print("Enter Product Name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            return orderService.getProductByName(name);
        }
        catch (NoSuchElementException e) {
            System.out.println("Product not found");
            return null;
        }
    }

    private boolean updateProduct() {
        System.out.print("Enter Product Name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            Product product = orderService.getProductByName(name);
            System.out.print("Enter new product name: ");
            String newName = scanner.nextLine();
            product.setName(newName.isEmpty() ? product.getName() : newName);

            System.out.print("Enter new product cost: ");
            String newCost = scanner.nextLine();
            if(newCost.isEmpty() == false) {
                product.setCost(Integer.parseInt(newCost));
            }

            System.out.println("Enter new description: ");
            String newDescription = scanner.nextLine();
            product.setDescription(newDescription.isEmpty() ? product.getDescription() : newDescription);
            orderService.updateProductByName(name, product);
            System.out.println("Product updated");
            return true;
        }
        catch (NoSuchElementException e) {
            System.out.println("Product not found");
            return false;
        }
        catch (NumberFormatException e) {
            System.out.println("Cost must be a integer number");
            return false;
        }
    }

    private boolean updateCustomer() {
        System.out.print("Enter Customer Login: ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        try {
            Customer customer = orderService.getCustomerByLogin(login);
            System.out.print("Enter new customer name: ");
            String newName = scanner.nextLine();
            customer.setName(newName.isEmpty() ? customer.getName() : newName);

            System.out.print("Enter new customer email: ");
            String newEmail = scanner.nextLine();
            customer.setEmail(newEmail.isEmpty() ? customer.getEmail() : newEmail);

            System.out.print("Enter new phone: ");
            String newPhone = scanner.nextLine();
            customer.setPhone(newPhone.isEmpty() ? customer.getPhone() : newPhone);

            System.out.print("Enter new delivery address: ");
            String newAddress = scanner.nextLine();
            customer.setAddress(newAddress.isEmpty() ? customer.getAddress() : newAddress);

            orderService.updateCustomerByLogin(login, customer);

            System.out.println("Customer updated");
            return true;
        }
        catch (NoSuchElementException e) {
            System.out.println("Customer not found");
            return false;
        }
    }

    private Order createOrder() {
        Order order = new Order();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        if(title.isEmpty()) {
            System.out.println("Title cannot be empty");
            return null;
        }

        order.setTitle(title);

        Map<Product, Integer> products = new HashMap<>();
        Product product = null;
        String isDone = "n";
        do {
            product = null;
            while (product == null) {
                product = lookupProductByName();
            }

            int quantity = 0;

            while (quantity <= 0) {
                System.out.print("Enter quantity: ");
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Quantity must be a integer number");
                }
            }

            final int QUANTITY = quantity;
            products.compute(product, (key, value) -> value == null ? QUANTITY : value + QUANTITY);

            System.out.print("Do you want to continue? (y/n): ");
            isDone = scanner.nextLine();
        } while (isDone.equalsIgnoreCase("y"));

        order.setProducts(products);
        order.setOrderingTime(new Timestamp(System.currentTimeMillis()));
        return order;
    }

    private void printMenu() {
        System.out.println("1. Add Product");
        System.out.println("2. Add Customer");
        System.out.println("3. Add Order"); // TODO
        System.out.println("4. Lookup Product By Name");
        System.out.println("5. Lookup Customer By Login");
        System.out.println("6. Print All Products");
        System.out.println("7. Print All Customers");
        System.out.println("8. Update Product");
        System.out.println("9. Update Customer"); // TODO
        System.out.println("0. Exit");
    }
}
