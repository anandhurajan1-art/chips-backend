package com.chips.sales_system.repository;

import com.chips.sales_system.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByPrivilegeKey(String privilegeKey);
}
