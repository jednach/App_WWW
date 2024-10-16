package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
