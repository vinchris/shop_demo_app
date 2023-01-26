package com.msg.mapper;

import com.msg.dto.ProductDTO;
import com.msg.entities.Category;
import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductMapper {

    public Product fromDto(ProductDTO dto) {
        if (dto != null) {
            Product product = new Product();
            product.setDescription(dto.getDescription());
            product.setProductStatus(EnumUtils.findEnumInsensitiveCase(ProductStatus.class, dto.getProductStatus().toUpperCase()));

            List<Category> listOfCategories = dto.getCategories().stream().map(c -> new Category(c, Arrays.asList(product))).toList();
            product.setCategories(listOfCategories);

            return product;
        }
        return null;
    }

    public ProductDTO toDto(Product product) {
        if (product != null) {
            ProductDTO dto = new ProductDTO();
            dto.setDescription(product.getDescription());
            dto.setProductStatus(dto.getProductStatus());
            dto.setCategories(product.getCategories().stream().map(c -> c.getDescription()).toList());
            dto.setPrice(product.getId() * 10);

            return dto;
        }
        return null;
    }

    public List<ProductDTO> allToDtos(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        if (products != null) {
            for (Product product : products) {
                ProductDTO dto = new ProductDTO();
                dto.setDescription(product.getDescription());
                dto.setProductStatus(dto.getProductStatus());
                dto.setCategories(product.getCategories().stream().map(c -> c.getDescription()).toList());
                dto.setPrice(product.getId() * 10);

                dtos.add(dto);
            }
        }
        return dtos;
    }
}
