package com.chips.sales_system.repository;

import com.chips.sales_system.entity.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {
    List<ItemMaster> findByBranchId(Long branchId);
}
