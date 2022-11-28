package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Integer> {

}
