package com.aziz.Issue_tracking_system.dao;
import com.aziz.Issue_tracking_system.entity.ResolvedIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolvedIssueRepository extends JpaRepository<ResolvedIssue, Integer> {
}
