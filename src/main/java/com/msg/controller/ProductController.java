package com.msg.controller;

import com.msg.dto.ProductDTO;
import com.msg.entities.Product;
import com.msg.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/dummy-product")
    public ProductDTO addDummyProduct() {
        return new ProductDTO("Dummy Product 1", "NEW", Arrays.asList("Dummy Category"), 10L);
    }

    @GetMapping("/dummy-product-from-business-service")
    public ProductDTO addDummyProductFromBusinessService() {
        return businessService.retrieveHardCodedProduct();
    }

    @GetMapping("/all-products-from-database")
    public List<Product> addDummyProductFromDatabase() {
        return businessService.retrieveAllProducts();
    }

    @GetMapping("/products-from-database-description-hardcoded")
    public Optional<Product> retrieveProductFromDatabaseByDescription() {
        String description = "Product 1";
        return businessService.retrieveProductByDescription(description);
    }

    @GetMapping("/all-products-from-business-service")
    public List<ProductDTO> retrieveProductsFromServiceLayer() {
        return businessService.retrieveAllProductDTOs();
    }
}
