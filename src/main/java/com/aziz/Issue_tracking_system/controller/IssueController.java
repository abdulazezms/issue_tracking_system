package com.aziz.Issue_tracking_system.controller;
import com.aziz.Issue_tracking_system.dao.IssueRepository;
import com.aziz.Issue_tracking_system.dao.ProjectRepository;
import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {
    ProjectRepository projectRepository;
    IssueRepository issueRepository;

    @Autowired
    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository){
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
    }

    @GetMapping("")
    public String getIssues(@RequestParam("id") Optional<Integer> id, Model model){
        String redirectionPath = "redirect:projects/index";;
        if(id.isEmpty()) return redirectionPath;
        //Add the project name to the controller as well as the list of issues related to this project.
        int theId = id.get();
        Project project = projectRepository.getReferenceById(theId);
        if(project == null) return redirectionPath;
        model.addAttribute("projectName", " - " + project.getName());
        return "issues/index";
    }

    @GetMapping("/showFormAdd")
    public String showForm(Model model){
        Issue issue = new Issue();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("the currently logged in user is " + auth.getName());
        model.addAttribute("issue", issue);
        return "redirect:/issues";
    }
}
