package com.msg.repositories;

import com.msg.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OrderHeaderJpaRepositoryTest {

    @Autowired
    OrderHeaderJpaRepository repository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    CustomerJpaRepository customerJpaRepository;

    @Autowired
    OrderApprovalJpaRepository orderApprovalJpaRepository;

    Product product;

    Customer customer;

    OrderApproval approval;

    @BeforeEach
    void setupBeforeEach() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("Product 1 for Test");
        product = productJpaRepository.saveAndFlush(newProduct);

        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("Cristi");
        customer = customerJpaRepository.saveAndFlush(newCustomer);

        approval = new OrderApproval();
        approval.setApprovedBy("me");
    }

    @Test
    public void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
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
        OrderHeader savedOrder = repository.save(orderHeader);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(2);
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

    @Test
    void saveOrderHeaderWithOrderLineAndCustomerTest() {
        OrderHeader orderHeader = new OrderHeader();
        OrderHeader savedOrder = repository.save(orderHeader);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(2);
        orderLine.setProduct(product);
        orderHeader.addOrderLine(orderLine);
        customer.addOrder(orderHeader);

        orderHeader.setOrderApproval(approval);

        repository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getCreatedDate());
        assertNotNull(savedOrder.getLastModifiedDate());
        assertEquals(savedOrder.getOrderLines().size(), 1);
        assertNotNull(savedOrder.getCustomer());

        OrderHeader fetchedOrder = repository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
        assertNotNull(fetchedOrder.getCustomer());
    }

    @Test
    void deleteOrderHeader() {
        OrderHeader orderHeader = new OrderHeader();
        OrderHeader savedOrder = repository.save(orderHeader);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(2);
        orderLine.setProduct(product);
        savedOrder.addOrderLine(orderLine);
        customer.addOrder(savedOrder);

        savedOrder.setOrderApproval(approval);

        log.info("Order saved and flushed !!!");

        repository.deleteById(savedOrder.getId());
        repository.flush();

    }

    @Test
    void testDeleteOrderHeader() {

//This part is the same as the above test case
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        Customer savedCustomer = customerJpaRepository.save(customer);

        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);

        OrderApproval approval = new OrderApproval();
        approval.setApprovedBy("me");

        orderHeader.setOrderApproval(approval);

        OrderHeader savedOrder = repository.save(orderHeader);


        repository.flush();

        Long approvalID = orderHeader.getOrderApproval().getId();


        repository.deleteById(savedOrder.getId());
        repository.flush();

//The order header with the last deleted ID is no longer in the DB.
        Optional<OrderHeader> fetchedOrder = repository.findById(savedOrder.getId());
        assertTrue(fetchedOrder.isEmpty());

//But the Order approval "me" is still there
        if (fetchedOrder.isPresent()) {
            OrderApproval orderApproval = repository.findById(approvalID).get().getOrderApproval();
            assertNotNull(orderApproval);
        }

    }

}