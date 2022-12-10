package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.entity.ResolvedIssue;

public interface ResolvedIssueService {
    ResolvedIssue getResolvedIssue(int id);
    void saveResolvedIssue(ResolvedIssue resolvedIssue);
}
