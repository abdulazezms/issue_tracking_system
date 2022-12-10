package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.IssueRepository;
import com.aziz.Issue_tracking_system.dao.UserRepository;
import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;
    private IssueService issueServiceUnderTest;
    final Issue issueUnderTest = new Issue(
            "x", new User("<full-name>", "<username>", "<password>", "<roles>", "<permissions>"),
            new Project("<project-name>", new ArrayList<>(), "<project-description>")
    );

    @BeforeEach
    void setUp() {
        issueServiceUnderTest = new IssueServiceImpl(issueRepository);

    }

    @Test
    void testGetAllIssues() {
        //when
        issueServiceUnderTest.getAllIssues();
        //then we want to verify that findAll() was invoked on the issueRepository/mock instance.
        verify(issueRepository).findAll();
    }

    @Test
    void testSaveIssue(){
        issueServiceUnderTest.saveIssue(issueUnderTest);
        ArgumentCaptor<Issue> issueArgumentCaptor = ArgumentCaptor.forClass(Issue.class);
        verify(issueRepository).save(issueArgumentCaptor.capture());
        Issue capturedIssue = issueArgumentCaptor.getValue();
        assertThat(capturedIssue).isEqualTo(issueUnderTest);
    }

    @Test
    void testGetIssue(){
        issueServiceUnderTest.getIssue(10);
        verify(issueRepository).getReferenceById(10);
    }

    @Test
    void testDeleteIssue(){
        issueServiceUnderTest.deleteIssue(10);
        verify(issueRepository).deleteById(10);
    }

    @Test
    void testDeleteAllIssues(){
        List<Issue> issueList = Arrays.asList(issueUnderTest);
        issueServiceUnderTest.deleteAllIssues(issueList);
        verify(issueRepository).deleteAll(issueList);
    }



}
