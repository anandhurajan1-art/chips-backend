package com.chips.sales_system.security;

import com.chips.sales_system.entity.User;
import com.chips.sales_system.entity.UserPrivilege;
import com.chips.sales_system.repository.UserPrivilegeRepository;
import com.chips.sales_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserPrivilegeRepository userPrivilegeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        java.util.Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        
        List<UserPrivilege> userPrivileges = userPrivilegeRepository.findByUser(user.get());
        List<GrantedAuthority> authorities = userPrivileges.stream()
                .map(up -> new SimpleGrantedAuthority(up.getPrivilege().getPrivilegeKey()))
                .collect(Collectors.toList());

        return UserDetailsImpl.build(user.get(), authorities);
    }
}
