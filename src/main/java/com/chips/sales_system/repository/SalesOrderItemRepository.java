package com.chips.sales_system.repository;

import com.chips.sales_system.entity.SalesOrderItem;
import com.chips.sales_system.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
    List<SalesOrderItem> findByOrder(SalesOrder order);
}
