package com.aziz.Issue_tracking_system.controller;

import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.ResolvedIssue;
import com.aziz.Issue_tracking_system.entity.User;
import com.aziz.Issue_tracking_system.service.IssueService;
import com.aziz.Issue_tracking_system.service.ProjectService;
import com.aziz.Issue_tracking_system.service.ResolvedIssueService;
import com.aziz.Issue_tracking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {
    ProjectService projectService;
    IssueService issueService;
    UserService userService;
    ResolvedIssueService resolvedIssueService;
    final String redirectionPath = "redirect:/projects/index";

    @Autowired
    public IssueController(ProjectService projectService, IssueService issueService, UserService userService, ResolvedIssueService resolvedIssueService){
        this.projectService = projectService;
        this.issueService = issueService;
        this.userService = userService;
        this.resolvedIssueService = resolvedIssueService;
    }

    @GetMapping("")
    public String getIssues(@RequestParam("id") Optional<Integer> id, Model model){
        if(id.isEmpty()) return redirectionPath;
        int theId = id.get();
        Project project = projectService.getProject(theId);
        if(project == null) return redirectionPath;
        model.addAttribute("project", project);
        model.addAttribute("issues", project.getIssues());
        model.addAttribute("projectName", " - ".concat(project.getName()));
        return "issues/index";
    }


    @GetMapping("/showFormAdd")
    public String showForm(@RequestParam("id") Optional<Integer> id, Model model){
        if(id.isEmpty()) return redirectionPath;
        int theId = id.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Issue issue = new Issue();
        String username = auth.getName();

        //get the project that the issue will be related to
        Project project = projectService.getProject(theId);

        //get the currently logged in user
        User user = userService.findByUsername(username);
        if(project == null || user == null) return redirectionPath;
        model.addAttribute("issue", issue);
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        return "issues/form-add";
    }



    @PostMapping("/save")
    public String saveIssue(@ModelAttribute("projectId") Optional<Integer> projectId,
                            @ModelAttribute("userId") Optional<Integer> userId,
                            @ModelAttribute("description") Optional<String> description,
                            @ModelAttribute("priority") Optional<Integer> priority,
                            @ModelAttribute("file") Optional<MultipartFile> file) throws IOException {

        if(projectId.isEmpty() || userId.isEmpty() || priority.isEmpty()) return redirectionPath;
        User user = userService.getUser(userId.get());
        Project project = projectService.getProject(projectId.get());

        Issue issue = new Issue();
        issue.setPriorityText(Map.of(1, "High", 2, "Medium", 3, "Low").get(priority.get()));
        issue.setStatus("New");
        issue.setUser(user);
        issue.setProject(project);
        issue.setDescription(description.get());
        if(file.isPresent()){
            issue.setFileName(file.get().getOriginalFilename());
            issue.setFile(file.get().getBytes());
        }
        issueService.saveIssue(issue);
        return "redirect:/issues?id="+projectId.get();
    }

    @PostMapping("/resolve")
    public String resolveIssue(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("issueId") Optional<Integer> issueId) throws IOException {
        if(file.isEmpty() || issueId.isEmpty()) return redirectionPath;
        Issue issue = issueService.getIssue(issueId.get());
        Project project = issue.getProject();
        ResolvedIssue resolvedIssue = new ResolvedIssue();
        resolvedIssue.setIssue(issue);
        resolvedIssue.setFile(file.get().getBytes());
        resolvedIssue.setName(file.get().getOriginalFilename());
        resolvedIssue.setType(file.get().getContentType());
        issue.setResolvedIssue(resolvedIssue);
        issue.setStatus("Resolved");
        resolvedIssueService.saveResolvedIssue(resolvedIssue);
        return "redirect:/issues?id="+project.getId();
    }


    @GetMapping("/delete")
    public String deleteIssue(@RequestParam("issueId") Optional<Integer> issueId, @RequestParam("projectId") Optional<Integer> projectId){
        if(issueId.isEmpty() || projectId.isEmpty()) return redirectionPath;
        Issue issue = issueService.getIssue(issueId.get());
        if(issue == null) return redirectionPath;
        issueService.deleteIssue(issueId.get());
        return "redirect:/issues?id="+projectId.get();
    }

    @GetMapping("/download")
    public  ResponseEntity<Resource> downloadAttachment(@RequestParam("fileId") Optional<Integer> fileId) throws IOException{
        ResolvedIssue resolvedIssue = resolvedIssueService.getResolvedIssue(fileId.get());
        byte[] byteArray = resolvedIssue.getFile();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(resolvedIssue.getName())
                                .build().toString())
                .body(resource);
    }
    @GetMapping("/downloadIssueAttachment")
    public  ResponseEntity<Resource> downloadIssueAttachment(@RequestParam("issueId") Optional<Integer> issueId) throws IOException{
        Issue issue = issueService.getIssue(issueId.get());
        byte[] byteArray = issue.getFile();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(issue.getFileName())
                                .build().toString())
                .body(resource);
    }


    @GetMapping("/showFormResolve")
    public String showResolveForm(@RequestParam("issueId") Optional<Integer> issueId, Model model){
        if(issueId.isEmpty()) return redirectionPath;
        model.addAttribute("issueId", issueId.get());
        return "issues/resolve-issue-form";
    }


}
