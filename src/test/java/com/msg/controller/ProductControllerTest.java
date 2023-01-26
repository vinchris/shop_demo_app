package com.msg.controller;

import com.msg.dto.ProductDTO;
import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import com.msg.service.BusinessService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService; // a mock, by default, will return null so we have to initialize it so to not get exceptions, such as org.json.JSONException: Unparsable JSON string

    /**
     * Parse the response content and the given string as JSON and assert the two
     * are "similar" - i.e. they contain the same attribute-value pairs regardless of formatting.
     */
    @SneakyThrows
    @Test
    void addDummyTest_basic() {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/dummy-product")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result_basic = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}")) //
                .andReturn(); // using the expected json assertion we can also remove elements that would have been in the actual result and still get a match

        JSONAssert.assertEquals("{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}",
                result_basic.getResponse().getContentAsString(), false);
    }

    @Test
    void addDummyFromBusinessServiceTest() throws Exception {

        when(businessService.retrieveHardCodedProduct()).thenReturn(
                new ProductDTO("Dummy Product 2", "NEW", Arrays.asList("Dummy Category 2"), 10L));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/dummy-product-from-business-service")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result_basic = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{description:\"Dummy Product 2\",productStatus:NEW,categories:[\"Dummy Category 2\"]}"))
                .andReturn();

        JSONAssert.assertNotEquals("{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}",
                result_basic.getResponse().getContentAsString(), false);
        JSONAssert.assertEquals("{\"description\":\"Dummy Product 2\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category 2\"]}",
                result_basic.getResponse().getContentAsString(), false);
    }

    /**
     * here we mock a database request to get all products
     *
     * @throws Exception
     */
    @Test
    void retrieveAllDummyProductsFromMockedDatabase() throws Exception {

        when(businessService.retrieveAllProducts()).thenReturn(getAListOfDummyProducts1());

        RequestBuilder request = MockMvcRequestBuilders
                .get("/all-products-from-database")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}]"))
                .andReturn();

        JSONAssert.assertEquals("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}]",
                result.getResponse().getContentAsString(), true);
        JSONAssert.assertEquals("[{id:1001,description:\"Dummy Product 1\"}]",
                result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals("[{id:1001,description:\"Dummy Product 12\"}]",
                result.getResponse().getContentAsString(), false);

        // and again mocking and building a new request with other data
        when(businessService.retrieveAllProducts()).thenReturn(getAListOfDummyProducts2());

        result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}, {id:1002,description:\"Dummy Product 2\",productStatus:NEW}, {id:1003,description:\"Dummy Product 3\",productStatus:NEW}]"))
                .andReturn();

        JSONAssert.assertEquals("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}, {id:1002,description:\"Dummy Product 2\",productStatus:NEW}, {id:1003,description:\"Dummy Product 3\",productStatus:NEW}]",
                result.getResponse().getContentAsString(), true);
        JSONAssert.assertEquals("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}, {}, {id:1003,description:\"Dummy Product 3\",productStatus:NEW}]",
                result.getResponse().getContentAsString(), false);
        JSONAssert.assertEquals("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}, {}, {}]",
                result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals("[{id:1001,description:\"Dummy Product 1\",productStatus:NEW}]",
                result.getResponse().getContentAsString(), false);

    }

    private List<Product> getAListOfDummyProducts1() {
        List<Product> dummyListProducts = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1001L);
        product1.setProductStatus(ProductStatus.NEW);
        product1.setDescription("Dummy Product 1");
        dummyListProducts.add(product1);

        return dummyListProducts;
    }

    private List<Product> getAListOfDummyProducts2() {
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

    /**
     * here we mock a database request to get all products
     *
     * @throws Exception
     */
    @Test
    void retrieveAllDummyProductDTOsFromBusinessMockTest() throws Exception {

    }

}