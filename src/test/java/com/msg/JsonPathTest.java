package com.msg;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class JsonPathTest {

    @Test
    void jsonPath_learning() {
        String responseFromService = "[{\"id\":1,\"description\":\"Product 1\",\"productStatus\":\"NEW\"}," +
                "{\"id\":2,\"description\":\"Product 2\",\"productStatus\":\"NEW\"}," +
                "{\"id\":3,\"description\":\"Product 3\",\"productStatus\":\"NEW\"}," +
                "{\"id\":4,\"description\":\"Product 4\",\"productStatus\":\"NEW\"}]";

        DocumentContext context = JsonPath.parse(responseFromService);
        int length = context.read("$.length()");
        log.info("$.length() -> " + context.read("$.length()").toString());
        assertThat(length).isEqualTo(4);

        log.info("$.[1] -> " + context.read("$.[1]").toString());
        assertThat(context.read("$.[1]").toString()).isEqualTo("{id=2, description=Product 2, productStatus=NEW}");
        log.info("$.[1] -> " + context.read("$.[0:1]").toString());

        log.info("$..id -> " + context.read("$..id").toString());
        List<Integer> ids = context.read("$..id");
        assertThat(ids).containsExactly(1, 2, 3, 4);

        log.info(context.read("$.[?(@.description=='Product 4')]").toString());
        assertThat(context.read("$.[?(@.description=='Product 4')]").toString())
                .isEqualTo("[{\"id\":4,\"description\":\"Product 4\",\"productStatus\":\"NEW\"}]");

        log.info(context.read("$.[?(@.id==4)]").toString());
        assertThat(context.read("$.[?(@.id==4)]").toString())
                .isEqualTo("[{\"id\":4,\"description\":\"Product 4\",\"productStatus\":\"NEW\"}]");
    }


}
