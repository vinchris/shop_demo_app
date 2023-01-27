package com.msg;

import com.msg.entities.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class ShopDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void createStream_whenFindFirstResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A", "B", "C", "D");

        Optional<String> result = list.stream().findFirst();

        assertTrue(result.isPresent());
        assertThat(result.get(), is("A"));
    }

    @Test
    public void createStream_whenFindAnyResultIsPresent_thenCorrect() {
        List<String> list = Arrays.asList("A", "B", "C", "D");

        Optional<String> result = list.stream().findAny();

        ProductStatus enumValue1 = EnumUtils.findEnumInsensitiveCase(ProductStatus.class, "NEW");

        assertTrue(result.isPresent());
        //is("A"), is("B"), is("C"),
        assertThat(result.get(), anyOf(is("A")));
        assertTrue(enumValue1 != null);
        assertEquals(ProductStatus.NEW, enumValue1);
    }

}
