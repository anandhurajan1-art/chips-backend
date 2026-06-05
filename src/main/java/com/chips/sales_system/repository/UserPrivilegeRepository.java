package com.chips.sales_system.repository;

import com.chips.sales_system.entity.UserPrivilege;
import com.chips.sales_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {
    List<UserPrivilege> findByUser(User user);
    void deleteByUser(User user);
}
