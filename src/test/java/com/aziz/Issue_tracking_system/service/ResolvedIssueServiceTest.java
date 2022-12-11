package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.ResolvedIssueRepository;
import com.aziz.Issue_tracking_system.entity.Issue;
import com.aziz.Issue_tracking_system.entity.Project;
import com.aziz.Issue_tracking_system.entity.ResolvedIssue;
import com.aziz.Issue_tracking_system.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ResolvedIssueServiceTest {

    @Mock
    private ResolvedIssueRepository resolvedIssueRepository;
    private ResolvedIssueService resolvedIssueServiceUnderTest;

    private ResolvedIssue resolvedIssue;

    @BeforeEach
    void setUp() {
        resolvedIssueServiceUnderTest = new ResolvedIssueServiceImpl(resolvedIssueRepository);
        resolvedIssue = new ResolvedIssue("<name>", "<type>",
                            new Issue("<description>",
                                new User("<name>", "<username>", "<password>", "<roles>", "<permissions>"),
                                new Project("<name>", new ArrayList<>(), "<description>")
                            )
                        );
    }

    @Test
    void getResolvedIssue() {
        resolvedIssueServiceUnderTest.getResolvedIssue(1);
        verify(resolvedIssueRepository).getReferenceById(1);
    }

    @Test
    void saveResolvedIssue() {
        resolvedIssueServiceUnderTest.saveResolvedIssue(resolvedIssue);
        verify(resolvedIssueRepository).save(resolvedIssue);
    }
}