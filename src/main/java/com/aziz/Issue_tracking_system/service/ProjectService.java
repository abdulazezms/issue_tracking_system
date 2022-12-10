package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.entity.Project;

import java.util.List;

public interface ProjectService {
    Project getProject(int id);
    List<Project> getAllProjects();
    void deleteProject(int id);
    void saveProject(Project project);
}
