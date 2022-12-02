package com.aziz.Issue_tracking_system.controller;
import com.aziz.Issue_tracking_system.dao.IssueRepository;
import com.aziz.Issue_tracking_system.dao.ProjectRepository;
import com.aziz.Issue_tracking_system.dao.UserRepository;
import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {
    ProjectRepository projectRepository;
    IssueRepository issueRepository;
    UserRepository userRepository;
    final String redirectionPath = "redirect:projects/index";

    @Autowired
    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository, UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String getIssues(@RequestParam("id") Optional<Integer> id, Model model){
        if(id.isEmpty()) return redirectionPath;
        int theId = id.get();
        Project project = projectRepository.getReferenceById(theId);
        if(project == null) return redirectionPath;
        model.addAttribute("project", project);
        model.addAttribute("issues", project.getIssues());
        model.addAttribute("projectName", " - ".concat(project.getName()));
        return "issues/index";
    }

    @GetMapping("/showFormAdd")
    public String showForm(@RequestParam("id") Optional<Integer> id, Model model){
        if(id.isEmpty())
            return redirectionPath;
        int theId = id.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Issue issue = new Issue();
        String username = auth.getName();

        //get the project that the issue will be related to
        Project project = projectRepository.getReferenceById(theId);

        //get the currently logged in user
        User user = userRepository.findByUsername(username);


        if(project == null || user == null) return redirectionPath;


        issue.setProject(project);//link the new issue to its corresponding project.
        issue.setUser(user);//link the new issue to its corresponding user.

        model.addAttribute("issue", issue);
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        return "issues/form-add";
    }


    @PostMapping("/save")
    public String saveIssue(@ModelAttribute("issue") Issue issue){

        int id = issue.getProject().getId();
        issue.setPriorityText(Map.of(1, "High", 2, "Medium", 3, "Low").get(issue.getPriority()));
        issue.setStatus("New");
        issueRepository.save(issue);
        return "redirect:/issues?id="+id;
    }


    @GetMapping("/delete")
    public String deleteIssue(@RequestParam("issueId") Optional<Integer> issueId, @RequestParam("projectId") Optional<Integer> projectId){
        System.out.println("the issue id = " + issueId+" and the project id = " + projectId);
        if(issueId.isEmpty() || projectId.isEmpty()) return redirectionPath;
        issueRepository.deleteById(issueId.get());
        return "redirect:/issues?id="+projectId.get();
    }


}
