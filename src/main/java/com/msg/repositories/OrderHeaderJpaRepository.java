package com.msg.repositories;

import com.msg.entities.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderJpaRepository extends JpaRepository<OrderHeader, Long> {
}
