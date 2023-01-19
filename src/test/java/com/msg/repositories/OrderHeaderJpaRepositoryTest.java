package com.msg.repositories;

import com.msg.entities.OrderHeader;
import com.msg.entities.OrderLine;
import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderJpaRepositoryTest {

    @Autowired
    OrderHeaderJpaRepository repository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    Product product;

    @BeforeEach
    void setupBeforeEach(){
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("Product 1 for Test");
        product = productJpaRepository.saveAndFlush(newProduct);
    }

    @Test
    public void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("New Customer");
        OrderHeader savedOrder = repository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getCreatedDate());
        assertNotNull(savedOrder.getLastModifiedDate());

        OrderHeader fetchedOrder = repository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());

    }

    @Test
    void saveOrderHeaderWithOrderLineTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("New Customer");
        OrderHeader savedOrder = repository.save(orderHeader);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity_ordered(2);
        orderLine.setProduct(product);
//
//        orderHeader.setOrderLines(Arrays.asList(orderLine));
//        orderLine.setOrderHeader(orderHeader); // inverse relationship (bidirectional)

        // we removed the code above because we implemented in OrderHeader entity
        // an association helper method which performs the code above for us and helps us keep our code cleaner
        orderHeader.addOrderLine(orderLine);

        repository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getCreatedDate());
        assertNotNull(savedOrder.getLastModifiedDate());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = repository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

}