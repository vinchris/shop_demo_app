package com.msg.mapper;

import com.msg.dto.ProductDTO;
import com.msg.entities.Category;
import com.msg.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class ProductMapperTest {

//    @Autowired
//    ProductMapper mapper;

    @Mock
    Product mockProduct;

    @Mock
    ProductDTO mockProductDto;

    @Mock
    Category mockCategory;

    @BeforeEach
    void beforeEachTest() {
        when(mockCategory.getProducts()).thenReturn(Arrays.asList(mockProduct));

        when(mockProduct.getCategories()).thenReturn(Arrays.asList(mockCategory));

    }

    @Test
    void testMockingProductAndCategory() {
        log.info("mockProduct: " + mockProduct.toString() + " " + mockProduct.hashCode());

        log.info("mockCategory.getProducts().get(0): " + mockCategory.getProducts().get(0).toString()
                + " " + mockCategory.getProducts().get(0).hashCode());
        assertEquals(mockProduct, mockCategory.getProducts().get(0));
    }

    @Test
    void toProductDTOTest() {

    }

    @Test
    void toProductTest() {

    }
}