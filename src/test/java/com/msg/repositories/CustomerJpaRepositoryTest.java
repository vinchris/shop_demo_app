package com.msg.repositories;

import com.msg.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerJpaRepositoryTest {

    @Autowired
    CustomerJpaRepository customerJpaRepository;

    @Autowired
    OrderHeaderJpaRepository orderHeaderJpaRepository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    OrderHeader orderHeader;

    Product product;

    @BeforeEach
    @Disabled
    public void beforeEachTest() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("Product 1 for Test");
        product = productJpaRepository.save(newProduct);

        OrderLine newOrderLine = new OrderLine();
        newOrderLine.setQuantityOrdered(1);
        newOrderLine.setProduct(product);
        OrderHeader newOrderHeader = new OrderHeader();
        newOrderHeader.addOrderLine(newOrderLine);
        orderHeader = orderHeaderJpaRepository.saveAndFlush(newOrderHeader);
    }

    @Test
    @Disabled
    public void createCustomerAndPlaceOrder() {
        Customer customer = new Customer();
        customer.addOrder(orderHeader);

        Customer savedCustomer = customerJpaRepository.saveAndFlush(customer);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getOrderHeaders());
        assertEquals(savedCustomer.getOrderHeaders().size(), 1);

    }

}