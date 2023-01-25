package com.msg.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public void addCategory(Category category) {
        categories.add(category);
        category.addProduct(this);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        Product product = (Product) o;
//
//        if (!Objects.equals(description, product.description)) return false;
//        if (productStatus != product.productStatus) return false;
//        return Objects.equals(categories, product.categories);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        result = 31 * result + (productStatus != null ? productStatus.hashCode() : 0);
//        return result;
//    }
}
