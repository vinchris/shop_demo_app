package com.msg.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws JSONException {

        String response = this.restTemplate.getForObject("/all-products-from-database", String.class);
        log.info("Response: " + response);
        JSONAssert.assertEquals("[{\"id\":1,\"description\":\"Product 1\",\"productStatus\":\"NEW\"},{\"id\":2,\"description\":\"Product 2\",\"productStatus\":\"NEW\"},{\"id\":3,\"description\":\"Product 3\",\"productStatus\":\"NEW\"},{\"id\":4,\"description\":\"Product 4\",\"productStatus\":\"NEW\"}]",
                response, false);
    }
}
