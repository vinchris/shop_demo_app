package com.msg.mapper;

import com.msg.dto.ProductDTO;
import com.msg.entities.Category;
import com.msg.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    @InjectMocks
    private ProductMapper mapper;

    @Mock
    Product mockProduct;

    @Mock
    private ProductDTO mockProductDto;


    @Test
    void toProductDTOTest() {
        when(mockProduct.getCategories()).thenReturn(Arrays.asList(new Category("Category 1", Arrays.asList(mockProduct))));
        when(mockProduct.getDescription()).thenReturn("Product 1");
        ProductDTO newProdcutDto = mapper.toDto(mockProduct);

        assertEquals(mockProduct.getDescription(), newProdcutDto.getDescription());
    }

    @Test
    void toProductTest() {
        when(mockProductDto.getCategories()).thenReturn(Arrays.asList("Category 2"));
        when(mockProductDto.getDescription()).thenReturn("Product 2");
        when(mockProductDto.getProductStatus()).thenReturn("NEW");
        Product newProduct = mapper.fromDto(mockProductDto);

        assertEquals(mockProductDto.getDescription(), newProduct.getDescription());
        assertEquals(mockProductDto.getProductStatus(), newProduct.getProductStatus().toString());
        assertEquals(mockProductDto.getCategories().get(0), newProduct.getCategories().get(0).getDescription());

    }
}