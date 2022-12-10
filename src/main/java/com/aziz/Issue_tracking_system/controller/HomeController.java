package com.aziz.Issue_tracking_system.controller;

import com.aziz.Issue_tracking_system.entity.User;
import com.aziz.Issue_tracking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
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
        User cur = userService.findByUsername(user.getUsername());
        if(cur != null){
            //in case the username is already taken
            return "redirect:signup?error=true";
        }

        // Save to db
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles("USER");
        userService.saveUser(user);
        return "redirect:signup?success=true";
    }

}