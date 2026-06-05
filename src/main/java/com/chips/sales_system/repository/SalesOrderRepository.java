package com.chips.sales_system.repository;

import com.chips.sales_system.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    boolean existsByOrderNo(String orderNo);
}
