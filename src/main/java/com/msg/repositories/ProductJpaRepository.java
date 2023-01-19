package com.msg.repositories;

import com.msg.entities.OrderHeader;
import com.msg.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Product findByDescription(String description);
}
