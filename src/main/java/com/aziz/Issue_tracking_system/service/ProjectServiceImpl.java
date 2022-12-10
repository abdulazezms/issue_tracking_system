package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.ProjectRepository;
import com.aziz.Issue_tracking_system.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ProjectServiceImpl implements ProjectService{

    ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getProject(int id) {
        return projectRepository.getReferenceById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }
}
