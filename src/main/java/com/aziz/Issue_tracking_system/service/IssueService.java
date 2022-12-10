package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.entity.Issue;

import java.util.List;

public interface IssueService {
    void deleteAllIssues(List<Issue> issues);
    void saveIssue(Issue issue);
    Issue getIssue(int id);
    void deleteIssue(int id);
}
