package com.aziz.Issue_tracking_system.service;


import com.aziz.Issue_tracking_system.dao.IssueRepository;
import com.aziz.Issue_tracking_system.entity.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class IssueServiceImpl implements IssueService {

    IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }



    @Override
    public void deleteAllIssues(List<Issue> issues) {
        issueRepository.deleteAll(issues);
    }

    @Override
    public void saveIssue(Issue issue) {
        issueRepository.save(issue);
    }

    @Override
    public Issue getIssue(int id) {
        return issueRepository.getReferenceById(id);
    }

    @Override
    public void deleteIssue(int id) {
        issueRepository.deleteById(id);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }
}
