package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions", schema = "app")
public class PermissionEntity extends BaseEntityAudit {
    private String name;
    private String description;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Set<RoleEntity> roles = new HashSet<>();
}
