package com.msg.entities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class OrderHeaderTest {

    @Test
    public void testEquals(){
        log.info("testEquals");
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);
        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(1L);

        assert orderHeader1.equals(orderHeader2);
        Assertions.assertTrue(orderHeader1.getId().equals(orderHeader2.getId()));
    }

    @Test
    public void testNotEquals(){
        log.info("testNotEquals");
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);
        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(2L);

        assert !orderHeader1.equals(orderHeader2);
        Assertions.assertFalse(orderHeader1.getId().equals(orderHeader2.getId()));
    }

}
