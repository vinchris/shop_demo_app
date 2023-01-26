package com.msg.service;

import com.msg.dto.ProductDTO;
import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import com.msg.mapper.ProductMapper;
import com.msg.repositories.ProductJpaRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessServiceTest {

    @InjectMocks
    private BusinessService businessService;

    @Mock
    private ProductJpaRepository repository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductDTO dto;


    @Test
    void findAllProductsTest() throws JSONException {
        when(repository.findAll()).thenReturn(getAListOfDummyProducts());

        List<Product> listOfMockedProducts = businessService.retrieveAllProducts();

        assertEquals(Long.valueOf(1001L), listOfMockedProducts.get(0).getId());
        assertEquals(Long.valueOf(1002L), listOfMockedProducts.get(1).getId());
    }

    @Test
    void findAllProductDtosTest() throws JSONException {
        when(repository.findAll()).thenReturn(getAListOfDummyProducts());


        List<Product> listOfMockedProducts = businessService.retrieveAllProducts();
        List<ProductDTO> productDTOList = businessService.retrieveAllProductDTOs(listOfMockedProducts, productMapper);

        assertEquals(listOfMockedProducts.get(0).getDescription(), productDTOList.get(0).getDescription());
    }

    private List<Product> getAListOfDummyProducts() {
        List<Product> dummyListProducts = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1001L);
        product1.setProductStatus(ProductStatus.NEW);
        product1.setDescription("Dummy Product 1");
        dummyListProducts.add(product1);

        Product product2 = new Product();
        product2.setId(1002L);
        product2.setProductStatus(ProductStatus.NEW);
        product2.setDescription("Dummy Product 2");
        dummyListProducts.add(product2);

        Product product3 = new Product();
        product3.setId(1003L);
        product3.setProductStatus(ProductStatus.NEW);
        product3.setDescription("Dummy Product 3");
        dummyListProducts.add(product3);

        return dummyListProducts;
    }

}