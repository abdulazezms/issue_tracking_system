package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.UserRepository;
import com.aziz.Issue_tracking_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(int id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
