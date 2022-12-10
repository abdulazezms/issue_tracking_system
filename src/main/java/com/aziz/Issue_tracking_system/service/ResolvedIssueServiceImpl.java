package com.aziz.Issue_tracking_system.service;

import com.aziz.Issue_tracking_system.dao.ResolvedIssueRepository;
import com.aziz.Issue_tracking_system.entity.ResolvedIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ResolvedIssueServiceImpl implements ResolvedIssueService{

    ResolvedIssueRepository resolvedIssueRepository;

    @Autowired
    public ResolvedIssueServiceImpl(ResolvedIssueRepository resolvedIssueRepository) {
        this.resolvedIssueRepository = resolvedIssueRepository;
    }

    @Override
    public ResolvedIssue getResolvedIssue(int id) {
        return resolvedIssueRepository.getReferenceById(id);
    }

    @Override
    public void saveResolvedIssue(ResolvedIssue resolvedIssue) {
        resolvedIssueRepository.save(resolvedIssue);
    }
}
