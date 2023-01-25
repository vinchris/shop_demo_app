package com.msg.mapper;

import com.msg.entities.Category;
import com.msg.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    @Mock
    Product mockProduct1;

    @Mock
    Product mockProduct2;

    @Mock
    Category mockCategory1;

    @Test
    void assertAndVerifyTest() {
        when(mockCategory1.getProducts()).thenReturn(Arrays.asList(mockProduct1));
        when(mockProduct1.getCategories()).thenReturn(Arrays.asList(mockCategory1));

        log.info("mockProduct: " + mockProduct1.toString() + " " + mockProduct1.hashCode());
        log.info("mockCategory.getProducts().get(0): " + mockCategory1.getProducts().get(0).toString()
                + " " + mockCategory1.getProducts().get(0).hashCode());

        assertEquals(mockProduct1, mockCategory1.getProducts().get(0));

        // verify that a mocked object's method is being called:
        verify(mockCategory1, times(3)).getProducts();
        verify(mockCategory1, atLeast(2)).getProducts();
        verify(mockCategory1, atLeastOnce()).getProducts();
        verify(mockCategory1, atMost(4)).getProducts();
        verify(mockCategory1, never()).setProducts(anyList());

        log.info("mockCategory: " + mockCategory1.toString() + " " + mockCategory1.hashCode());
        log.info("mockProduct.getCategories().get(0): " + mockProduct1.getCategories().get(0).toString()
                + " " + mockProduct1.getCategories().get(0).hashCode());

        assertEquals(mockCategory1, mockProduct1.getCategories().get(0));
        verify(mockProduct1, times(3)).getCategories();
    }

    @Test
    void argumentCaptureTest() {
        mockCategory1.setDescription("Mock Category");

        // Verification
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockCategory1).setDescription(captor.capture());

        assertEquals("Mock Category", captor.getValue());
    }

    @Test
    void multipleArgumentsCaptureTest() {
        mockCategory1.addProduct(mockProduct1);
        mockCategory1.addProduct(mockProduct2);
        mockCategory1.addProduct(mockProduct1);

        // Verification
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(mockCategory1, times(3)).addProduct(captor.capture());

        List<Product> allValues = captor.getAllValues();
        assertEquals(mockProduct1, allValues.get(0));
        assertEquals(mockProduct2, allValues.get(1));
        assertEquals(mockProduct1, allValues.get(2));
    }

    /**
     * spy() is usually used where we want to use the original dependency
     * and call verify on its methods
     */
    @Test
    void spyingTest() {
        ArrayList<Product> mockList = mock(ArrayList.class);
        log.info("The mocked arrayList initial values !");
        log.info("mockList.get(0) -> expected null ; actual " + mockList.get(0));
        log.info("mockList.size() -> expected 0 ; actual " + mockList.size());
        mockList.add(mockProduct1);
        mockList.add(mockProduct2);
        log.info("mockList.size() -> expected 0 ; actual " + mockList.size());
        log.info("ONLY using mock methods like when and thenReturn can we modify mock objects");
        when(mockList.size()).thenReturn(5);
        log.info("mockList.size() -> expected 5 ; actual " + mockList.size());

        System.out.println("The DIFFERENCE between mock() and spy() is that " +
                " \n spy() maintains the object's original behaviour, the 'spied' object calls real methods" +
                " \n where the mocked object would not");

        ArrayList<Product> spiedList = spy(ArrayList.class);
        log.info("The 'spied' arrayList initial values !");
        log.info("spiedList.get(0) -> IndexOutOfBoundsException because the list is empty and get(0) is called");
        log.info("spiedList.size() -> expected 0 ; actual " + spiedList.size());
        spiedList.add(mockProduct1);
        spiedList.add(mockProduct2);
        log.info("spiedList.size() -> expected 2; actual " + spiedList.size());
        log.info("using mock methods like when and thenReturn we modify the spied object's real parameters/fields");
        when(spiedList.size()).thenReturn(5);
        log.info("spiedList.size() -> expected 5 ; actual " + spiedList.size());
        spiedList.add(mockProduct2);
        log.info("Even though we've added another item in the list, the value for spiedList.size() " +
                " \n won't change because we mocked it to always return the value of '5'");
        log.info("spiedList.size() -> expected 5 ; actual " + spiedList.size());

        verify(spiedList, times(2)).add(mockProduct2);

    }

    @Test
    @Disabled
    void toProductDTOTest() {

    }

    @Test
    @Disabled
    void toProductTest() {

    }
}