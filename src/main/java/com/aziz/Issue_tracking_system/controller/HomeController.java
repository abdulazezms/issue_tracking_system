package com.aziz.Issue_tracking_system.controller;

import com.aziz.Issue_tracking_system.dao.UserRepository;
import com.aziz.Issue_tracking_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("login")
    public String login(){return "login";}

    @GetMapping("signup")
    public String signup(Model model){
        System.out.println("again, i am having this");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("processSignup")
    public String processSignup(@ModelAttribute("user") User user){
        User cur = userRepository.findByUsername(user.getUsername());
        if(cur != null){
            return "redirect:signup?error=true";
        }

        // Save to db
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles("USER");
        userRepository.save(user);
        return "redirect:index";
    }

}