package com.chips.sales_system.repository;

import com.chips.sales_system.entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListRepository extends JpaRepository<ItemList, Long> {
    List<ItemList> findByBranchId(Long branchId);
}
