package com.aziz.Issue_tracking_system.controller;

import com.aziz.Issue_tracking_system.dao.IssueRepository;
import com.aziz.Issue_tracking_system.dao.ProjectRepository;
import com.aziz.Issue_tracking_system.entity.Project;
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
    ProjectRepository projectRepository;
    IssueRepository issueRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, IssueRepository issueRepository){
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
    }


    @GetMapping("/index")
    public String getProjects(Model model){
        System.out.println("i received the call from get projects!");
        List<Project> projects = projectRepository.findAll();
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
        Project project = projectRepository.getReferenceById(theId);
        model.addAttribute("project", project);
        return "projects/form-add";

    }

    @PostMapping("/save")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
                              BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            System.out.println("field error of name = " + bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("error_message", bindingResult.getFieldError().getDefaultMessage());
            return "projects/form-add";
        }
        projectRepository.save(project);
        System.out.println("the project received is " + project);
        return "redirect:/projects/index";
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("id") Optional<Integer> id){
        System.out.println("receiving delete request with id = !" + id);
        if(id.isEmpty()) return "projects/index";
        //delete the issues first related to the project, then delete the project.
        Project project = projectRepository.getReferenceById(id.get());
        issueRepository.deleteAll(project.getIssues());
        projectRepository.deleteById(id.get());

        return "redirect:/projects/index";
    }


}
