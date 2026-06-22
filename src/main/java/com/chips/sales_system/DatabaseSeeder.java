package com.chips.sales_system;

import com.chips.sales_system.entity.Privilege;
import com.chips.sales_system.entity.User;
import com.chips.sales_system.entity.UserPrivilege;
import com.chips.sales_system.entity.enums.Role;
import com.chips.sales_system.entity.enums.UserStatus;
import com.chips.sales_system.repository.PrivilegeRepository;
import com.chips.sales_system.repository.UserPrivilegeRepository;
import com.chips.sales_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedPrivileges();
        seedAdminUser();
    }

    private void seedPrivileges() {
        List<String[]> privileges = Arrays.asList(
            new String[]{"Dashboard View", "DASHBOARD_VIEW"},
            new String[]{"Take Order", "TAKE_ORDER"},
            new String[]{"View Orders", "VIEW_ORDERS"},
            new String[]{"Generate Bill", "GENERATE_BILL"},
            new String[]{"Sales", "SALES"},
            new String[]{"View Bill", "VIEW_BILL"},
            new String[]{"Item List", "ITEM_LIST"},
            new String[]{"Add Item Name", "ADD_ITEM_NAME"},
            new String[]{"Add Units", "ADD_UNITS"},
            new String[]{"Add Shop", "ADD_SHOP"},
            new String[]{"Staff Management", "STAFF_MANAGEMENT"},
            new String[]{"Reports", "REPORTS"},
            new String[]{"Return Entry", "RETURN_ENTRY"},
            new String[]{"View Return Report", "VIEW_RETURN_REPORT"},
            new String[]{"Item-wise Return Report", "ITEM_WISE_RETURN_REPORT"},
            new String[]{"Shop-wise Return Report", "SHOP_WISE_RETURN_REPORT"},
            new String[]{"Download Return Report PDF", "DOWNLOAD_RETURN_REPORT_PDF"},
            new String[]{"View Settings", "VIEW_SETTINGS"},
            new String[]{"View Item Count Report", "VIEW_ITEM_COUNT_REPORT"}
        );

        for (String[] p : privileges) {
            if (privilegeRepository.findByPrivilegeKey(p[1]).isEmpty()) {
                Privilege privilege = new Privilege();
                privilege.setPrivilegeName(p[0]);
                privilege.setPrivilegeKey(p[1]);
                privilegeRepository.save(privilege);
            }
        }
    }

    private void seedAdminUser() {
        User admin = userRepository.findByUsername("admin").orElse(new User());
        
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin")); // Default password
        admin.setName("System Admin");
        admin.setPhone("0000000000");
        admin.setRole(Role.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        userRepository.save(admin);
        
        // Clear existing privileges to avoid duplicates
        List<UserPrivilege> existing = userPrivilegeRepository.findByUser(admin);
        userPrivilegeRepository.deleteAll(existing);
        
        // Assign all privileges to admin
        List<Privilege> allPrivileges = privilegeRepository.findAll();
        for (Privilege p : allPrivileges) {
            UserPrivilege up = new UserPrivilege();
            up.setUser(admin);
            up.setPrivilege(p);
            userPrivilegeRepository.save(up);
        }
    }
}
