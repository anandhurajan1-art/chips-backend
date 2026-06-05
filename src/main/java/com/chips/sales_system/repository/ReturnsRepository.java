package com.chips.sales_system.repository;

import com.chips.sales_system.entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnsRepository extends JpaRepository<Returns, Long> {
    boolean existsByReturnNo(String returnNo);
}
