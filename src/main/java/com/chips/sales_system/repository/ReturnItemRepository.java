package com.chips.sales_system.repository;

import com.chips.sales_system.entity.ReturnItem;
import com.chips.sales_system.entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {
    List<ReturnItem> findByReturns(Returns returns);
}
