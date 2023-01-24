package com.msg.mapper;

import com.msg.dto.ProductDTO;
import com.msg.entities.Category;
import com.msg.entities.Product;
import com.msg.entities.ProductStatus;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;
import java.util.List;

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

            return dto;
        }
        return null;
    }
}
