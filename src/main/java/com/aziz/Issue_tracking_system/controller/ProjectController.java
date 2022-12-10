package com.aziz.Issue_tracking_system.controller;

import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.service.IssueService;
import com.aziz.Issue_tracking_system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    ProjectService projectService;
    IssueService issueService;

    @Autowired
    public ProjectController(ProjectService projectService, IssueService issueService){
        this.projectService = projectService;
        this.issueService = issueService;
    }


    @GetMapping("/index")
    public String getProjects(Model model){
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects/index";
    }

    @GetMapping("/showFormAdd")
    public String showForm(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        return "projects/form-add";
    }

    @GetMapping("/update")
    public String updateProject(@RequestParam("id") Optional<Integer> id, Model model){
        if(id.isEmpty()) return "projects/index";
        int theId = id.get();
        Project project = projectService.getProject(theId);
        model.addAttribute("project", project);
        return "projects/form-add";

    }

    @PostMapping("/save")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
                              BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("error_message", bindingResult.getFieldError().getDefaultMessage());
            return "projects/form-add";
        }
        projectService.saveProject(project);
        return "redirect:/projects/index";
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("id") Optional<Integer> id){
        if(id.isEmpty()) return "projects/index";
        Project project = projectService.getProject(id.get());
        issueService.deleteAllIssues(project.getIssues());
        projectService.deleteProject(id.get());
        return "redirect:/projects/index";
    }


}
