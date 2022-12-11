package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.ResolvedIssue;
import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class ResolvedIssueRepositoryTest {
    @Autowired
    private ResolvedIssueRepository resolvedIssueRepositoryUnderTest;
    private ResolvedIssue resolvedIssueUnderTest;


    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    private final String email = "219110520@psu.edu.sa";
    private final User userUnderTest = new User(
            "Abdulaziz Al-Alshaikh",
            email,
            "simple_password",
            "USER",
            ""
    );


    private Project projectUnderTest;
    private Issue issueUnderTest;


    @BeforeEach
    void setUp() {
        projectUnderTest = new Project("<project-name>", new ArrayList<>(), "<project-description>");
        projectRepository.save(projectUnderTest);
        userRepository.save(userUnderTest);
        issueUnderTest = new Issue();
        issueUnderTest.setUser(userUnderTest);
        issueUnderTest.setProject(projectUnderTest);
        issueRepository.save(issueUnderTest);
        resolvedIssueUnderTest = new ResolvedIssue();
    }

    @Test
    void testInsertResolvedIssue(){
        resolvedIssueUnderTest = new ResolvedIssue("<name>", "<type>", issueUnderTest);
        ResolvedIssue stored = resolvedIssueRepositoryUnderTest.save(resolvedIssueUnderTest);
        assertThat(stored).isEqualTo(resolvedIssueUnderTest);
    }

    @Test
    void testDeleteResolvedIssue(){
        resolvedIssueUnderTest = new ResolvedIssue("<name>", "<type>", issueUnderTest);
        ResolvedIssue stored = resolvedIssueRepositoryUnderTest.save(resolvedIssueUnderTest);
        resolvedIssueRepositoryUnderTest.deleteById(stored.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            resolvedIssueRepositoryUnderTest.getReferenceById(stored.getId());
        }, "Should've thrown a JPA object retrieval failure exception!");
    }

}