package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepositoryUnderTest;
    private Project projectUnderTest = new Project("<project-name>", new ArrayList<>(), "<project-description>");
    @BeforeEach
    void setup() {
        //Before each test, delete all tuples from the db, to start in a clean state in the next test.
        projectRepositoryUnderTest.deleteAll();
    }

    @Test
    void testAddProject(){
        projectRepositoryUnderTest.save(projectUnderTest);
        Project fromDb = projectRepositoryUnderTest.getReferenceById(projectUnderTest.getId());
        assertThat(fromDb).isEqualTo(projectUnderTest);
    }

    @Test
    void testDeleteProject(){
        projectRepositoryUnderTest.save(projectUnderTest);
        Project retrieved = projectRepositoryUnderTest.getReferenceById(projectUnderTest.getId());
        projectRepositoryUnderTest.deleteById(retrieved.getId());
        assertThrows(JpaObjectRetrievalFailureException.class, () -> projectRepositoryUnderTest.getReferenceById(retrieved.getId()), "Should've thrown an object retrieval failure exception!");
    }

    @Test
    void testUpdateProject(){
        projectRepositoryUnderTest.save(projectUnderTest);
        projectUnderTest.setDescription("New Description!");
        projectRepositoryUnderTest.save(projectUnderTest);
        Project retrieved = projectRepositoryUnderTest.getReferenceById(projectUnderTest.getId());
        assertThat(retrieved.getDescription()).isEqualTo("New Description!");
    }

    @Test
    void testDeleteAllProjects(){
        Project a = new Project("<project-name>", new ArrayList<>(), "<project-description>")
                , b = new Project("<project-name>", new ArrayList<>(), "<project-description>"),
                c = new Project("<project-name>", new ArrayList<>(), "<project-description>");

        projectRepositoryUnderTest.saveAll(Arrays.asList(a, b, c));

        List<Project> projects = projectRepositoryUnderTest.findAll();
        List<Integer> ids = projects.stream().map(Project::getId).toList();

        projectRepositoryUnderTest.deleteAllById(ids);
        assertThat(projectRepositoryUnderTest.findAll()).isEmpty();
    }



}