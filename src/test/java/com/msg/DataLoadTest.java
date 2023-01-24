package com.msg;

import com.msg.entities.*;
import com.msg.repositories.CustomerJpaRepository;
import com.msg.repositories.OrderHeaderJpaRepository;
import com.msg.repositories.ProductJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class DataLoadTest {

    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderJpaRepository orderHeaderRepository;

    @Autowired
    CustomerJpaRepository customerRepository;

    @Autowired
    ProductJpaRepository productRepository;

    @Test
    public void testLazyVsEager() {
        OrderHeader orderHeader = orderHeaderRepository.getReferenceById(1123L);

        log.info("Order id is: " + orderHeader.getId());
        log.info("Customer name for this order is: " + orderHeader.getCustomer().getCustomerName());
    }

    @Test
    @Rollback(value = false)
    void testDataLoader() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 10;

        for (int i = 0; i < ordersToCreate; i++) {
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    /**
     * helper method that saves a new order with a given customer and list of products
     *
     * @param customer
     * @param products
     * @return
     */
    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    /**
     * save/load customer test data
     *
     * @return
     */
    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    /**
     * saves a customer in the customer repository
     *
     * @param customerName
     * @return
     */
    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName(customerName);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    /**
     * load test products in a list of products
     *
     * @return
     */
    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    /**
     * saves a product in the product repository
     *
     * @param description
     * @return
     */
    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = new Product();
                    p1.setDescription(description);
                    p1.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(p1);
                });
    }
}
