package com.project.UDMS.repository;

import com.project.UDMS.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByPassword(String password);
    Boolean existsByUserId(String Name);
}
