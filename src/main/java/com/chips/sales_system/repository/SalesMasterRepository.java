package com.chips.sales_system.repository;

import com.chips.sales_system.entity.SalesMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesMasterRepository extends JpaRepository<SalesMaster, Long> {
    List<SalesMaster> findByBranchIdAndShopIdAndSaleDateBetween(Long branchId, Long shopId, LocalDate startDate, LocalDate endDate);
    List<SalesMaster> findByBranchIdAndSaleDateBetween(Long branchId, LocalDate startDate, LocalDate endDate);
    List<SalesMaster> findByShopIdAndSaleDateBetween(Long shopId, LocalDate startDate, LocalDate endDate);
    List<SalesMaster> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
