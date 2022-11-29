package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}

