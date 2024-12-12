package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select * from user where email =:email",nativeQuery = true)
    User findByEmailA(String email);
    Optional<User> findByEmail(String email);
    @Query(value = "Select * from user where email =:email and is_active =:isActive",nativeQuery = true)
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);

    User findByIdAndIsActive(Long id, Boolean isActive);
    @Query(value = "Select * from user where email =:email and is_active =:isActive",nativeQuery = true)
    User findEmailAndIsActive(String email,  Boolean isActive);
}
