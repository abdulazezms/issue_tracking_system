package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.entity.User;

import java.util.List;

public interface UserService {
    User getUser(int id);
    User findByUsername(String username);
    List<User> getAllUsers();
    void saveUser(User user);
}
