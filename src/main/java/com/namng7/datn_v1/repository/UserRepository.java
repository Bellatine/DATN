package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();

    @Query("UPDATE User u set u.status = :status where u.id = :id")
    int updateStatusUser(@Param("status") int status);
}