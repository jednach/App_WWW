package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT r.name FROM UserEntity u JOIN u.roles r WHERE u.email = :email")
    List<String> findRolesByUsername(@Param("email") String email);
}
