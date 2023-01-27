package com.msg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTest {

    @Test
    void assertJ_learning() {
        List<Integer> numbers = Arrays.asList(12, 15, 45);

        assertThat(numbers)
                .hasSize(3)
                .contains(12, 15)
                .allMatch(x -> x > 10)
                .noneMatch(x -> x > 110);

        assertThat("").isEmpty();
        assertThat("ABCDE").isNotEmpty()
                .contains("BCD")
                .startsWith("AB")
                .endsWith("DE");
    }
}
