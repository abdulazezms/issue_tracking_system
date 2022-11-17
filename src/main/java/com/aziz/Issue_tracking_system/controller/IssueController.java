package com.aziz.Issue_tracking_system.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("issues")
public class IssueController {

    @GetMapping("/index")
    public String index(){
        return "issues/index";
    }
}
