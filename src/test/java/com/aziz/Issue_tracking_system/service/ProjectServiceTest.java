package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.ProjectRepository;
import com.aziz.Issue_tracking_system.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    private ProjectService projectServiceUnderTest;
    private Project projectUnderTest = new Project("<project-name>", new ArrayList<>(), "<project-description>");


    @BeforeEach
    void setUp() {
        projectServiceUnderTest = new ProjectServiceImpl(projectRepository);
    }

    @Test
    void testGetProject() {
        projectServiceUnderTest.getProject(1);
        verify(projectRepository).getReferenceById(1);
    }

    @Test
    void testGetAllProjects() {
        projectServiceUnderTest.getAllProjects();
        verify(projectRepository).findAll();
    }

    @Test
    void testDeleteProject() {
        projectServiceUnderTest.deleteProject(1);
        verify(projectRepository).deleteById(1);
    }

    @Test
    void testSaveProject() {
        projectServiceUnderTest.saveProject(projectUnderTest);
        verify(projectRepository).save(projectUnderTest);
    }
}