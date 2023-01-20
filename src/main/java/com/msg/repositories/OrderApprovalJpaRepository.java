package com.msg.repositories;

import com.msg.entities.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalJpaRepository extends JpaRepository<OrderApproval, Long> {
}
