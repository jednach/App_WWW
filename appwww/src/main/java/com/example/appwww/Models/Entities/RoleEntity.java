package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles", schema = "app")
public class RoleEntity extends BaseEntityAudit {

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permission_role",
            schema = "app",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions = new HashSet<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(PermissionEntity permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }
}
