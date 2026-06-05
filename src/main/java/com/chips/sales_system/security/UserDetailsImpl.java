package com.chips.sales_system.security;

import com.chips.sales_system.entity.User;
import com.chips.sales_system.entity.enums.Role;
import com.chips.sales_system.entity.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String name;
    private Role role;
    private UserStatus status;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, String name, Role role, UserStatus status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = status;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user, List<GrantedAuthority> authorities) {
        return new UserDetailsImpl(
            user.getId(), 
            user.getUsername(), 
            user.getPassword(),
            user.getName(),
            user.getRole(),
            user.getStatus(),
            authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public Role getRole() { return role; }
    public UserStatus getStatus() { return status; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return status == UserStatus.ACTIVE; }
}
