package com.aziz.Issue_tracking_system.dao;
import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class IssueRepositoryTest {

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
    }


    @Test
    void testInsertNewIssue(){
        issueUnderTest.setProject(projectUnderTest);
        issueUnderTest.setUser(userUnderTest);
        issueRepository.save(issueUnderTest);
        Issue fromDb = issueRepository.getReferenceById(issueUnderTest.getId());
        assertThat(fromDb).isEqualTo(issueUnderTest);
    }

    @Test
    void testDeleteIssue(){
        issueUnderTest.setProject(projectUnderTest);
        issueUnderTest.setUser(userUnderTest);
        issueRepository.save(issueUnderTest);

        Issue retrieved = issueRepository.getReferenceById(issueUnderTest.getId());
        issueRepository.deleteById(retrieved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> issueRepository.getReferenceById(retrieved.getId()), "Should've thrown an object retrieval failure exception!");
    }

    @Test
    void testUpdateIssue(){
        issueUnderTest.setProject(projectUnderTest);
        issueUnderTest.setUser(userUnderTest);
        issueRepository.save(issueUnderTest);

        Issue fromDb = issueRepository.getReferenceById(issueUnderTest.getId());
        fromDb.setDescription("New Description!");

        issueRepository.save(fromDb);

        Issue updated = issueRepository.getReferenceById(issueUnderTest.getId());
        assertThat(updated.getDescription()).isEqualTo("New Description!");
    }

    @Test
    void testDeleteAllIssues(){
        Issue a = new Issue();
        Issue b = new Issue();
        Issue c = new Issue();

        issueRepository.saveAll(Arrays.asList(a, b, c));

        List<Issue> issues = issueRepository.findAll();
        List<Integer> ids = issues.stream().map(Issue::getId).toList();

        issueRepository.deleteAllById(ids);
        assertThat(issueRepository.findAll()).isEmpty();
    }

    @AfterEach
    void tearDown() {
        issueRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }
}