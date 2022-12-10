package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.entity.User;

public interface UserService {
    User getUser(int id);
    User findByUsername(String username);
    void saveUser(User user);
}
