package com.chips.sales_system.repository;

import com.chips.sales_system.entity.SalesItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesItemsRepository extends JpaRepository<SalesItems, Long> {
}
