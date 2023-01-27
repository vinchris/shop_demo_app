package com.msg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersTest {

    @Test
    void hamcrest_learning() {
        List<Integer> numbers = Arrays.asList(12, 15, 45);

        assertThat(numbers, hasSize(3));
        assertThat(numbers, hasItems(12, 45));
        assertThat(numbers, everyItem(greaterThan(10)));
        assertThat(numbers, everyItem(lessThan(110)));

        assertThat("", isEmptyString());
        assertThat("ABCDE", startsWith("ABC"));
        assertThat("ABCDE", containsString("BCD"));
        assertThat("ABCDE", endsWith("CDE"));
    }
}
