package com.msg.service;

import com.msg.dto.ProductDTO;
import com.msg.entities.Product;
import com.msg.mapper.ProductMapper;
import com.msg.repositories.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * demo service layer object used to retrieve/manipulate products
 */
@Service
public class BusinessService {

    @Autowired
    private ProductJpaRepository repository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDTO retrieveHardCodedProduct() {
        return new ProductDTO("Dummy Product 1", "NEW", Arrays.asList("Dummy Category"), 10L);
    }

    public List<Product> retrieveAllProducts() {
        return repository.findAll();
    }

    public List<ProductDTO> retrieveAllProductDTOs() {
        List<ProductDTO> dtos = new ArrayList<>();
        List<Product> products = repository.findAll();

        for (Product product : products) {
            ProductDTO dto = productMapper.toDto(product);
            dto.setPrice(product.getId() + 10);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<ProductDTO> retrieveAllProductDTOs(List<Product> products, ProductMapper productMapper) {
        List<ProductDTO> dtos = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = productMapper.toDto(product);
            dto.setPrice(product.getId() + 10);
            dtos.add(dto);
        }

        return dtos;
    }


    public Optional<Product> retrieveProductByDescription(String description) {
        return repository.findByDescription(description);
    }
}
