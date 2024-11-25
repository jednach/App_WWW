package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import com.example.appwww.Models.Enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", schema = "app")
public class UserEntity extends BaseEntityAudit implements UserDetails {

    @Enumerated(value = EnumType.STRING)
    private UserType type;

    @NotNull @Email
    private String email;

    @NotNull
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_user",
            schema = "app",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(RoleEntity role : roles){
            authorities.addAll(role.getAuthorities());
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
