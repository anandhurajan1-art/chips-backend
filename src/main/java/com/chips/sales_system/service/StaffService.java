package com.chips.sales_system.service;

import com.chips.sales_system.dto.StaffDto;
import com.chips.sales_system.entity.Privilege;
import com.chips.sales_system.entity.User;
import com.chips.sales_system.entity.UserPrivilege;
import com.chips.sales_system.entity.enums.Role;
import com.chips.sales_system.entity.enums.UserStatus;
import com.chips.sales_system.repository.PrivilegeRepository;
import com.chips.sales_system.repository.UserPrivilegeRepository;
import com.chips.sales_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<StaffDto> getAllStaff() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Transactional
    public StaffDto createStaff(StaffDto staffDto) {
        User user = new User();
        user.setName(staffDto.getName());
        user.setPhone(staffDto.getPhone());
        user.setUsername(staffDto.getUsername());
        user.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        user.setRole(Role.STAFF);
        user.setStatus(UserStatus.valueOf(staffDto.getStatus()));
        User savedUser = userRepository.save(user);

        savePrivileges(savedUser, staffDto.getPrivileges());
        return mapToDto(savedUser);
    }

    @Transactional
    public StaffDto updateStaff(Long id, StaffDto staffDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(staffDto.getName());
        user.setPhone(staffDto.getPhone());
        if (staffDto.getPassword() != null && !staffDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        }
        if (staffDto.getStatus() != null) {
            user.setStatus(UserStatus.valueOf(staffDto.getStatus()));
        }
        User savedUser = userRepository.save(user);

        userPrivilegeRepository.deleteByUser(savedUser);
        savePrivileges(savedUser, staffDto.getPrivileges());

        return mapToDto(savedUser);
    }

    private void savePrivileges(User user, List<String> privilegeKeys) {
        if (privilegeKeys != null) {
            for (String key : privilegeKeys) {
                Optional<Privilege> privilege = privilegeRepository.findByPrivilegeKey(key);
                privilege.ifPresent(p -> {
                    UserPrivilege up = new UserPrivilege();
                    up.setUser(user);
                    up.setPrivilege(p);
                    userPrivilegeRepository.save(up);
                });
            }
        }
    }

    private StaffDto mapToDto(User user) {
        StaffDto dto = new StaffDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setStatus(user.getStatus() != null ? user.getStatus().name() : null);
        List<String> privs = userPrivilegeRepository.findByUser(user).stream()
                .map(up -> up.getPrivilege().getPrivilegeKey())
                .collect(Collectors.toList());
        dto.setPrivileges(privs);
        return dto;
    }
}
