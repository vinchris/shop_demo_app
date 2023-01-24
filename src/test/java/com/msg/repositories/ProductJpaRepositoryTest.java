package com.msg.repositories;

import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductJpaRepositoryTest {

    @Autowired
    ProductJpaRepository jpaRepository;

    @Test
    void persistAndRetrievalTest() {
        Product product = new Product();
        product.setDescription("product 1");
        product.setProductStatus(ProductStatus.IN_STOCK);

        Product savedProduct = jpaRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getProductStatus());
        assertNotNull(savedProduct.getCreatedDate());
        assertNotNull(savedProduct.getLastModifiedDate());

        Product fetchedProduct = jpaRepository.getReferenceById(product.getId());

        assertNotNull(fetchedProduct);
        assertNotNull(fetchedProduct.getId());
        assertNotNull(fetchedProduct.getProductStatus());
        assertNotNull(fetchedProduct.getCreatedDate());
        assertNotNull(fetchedProduct.getLastModifiedDate());
    }

    @Test
    void findByDescriptionTest() {
        Product product = jpaRepository.findByDescription("Product 1").get();

        assertNotNull(product);
        assertNotNull(product.getCategories());
    }
}