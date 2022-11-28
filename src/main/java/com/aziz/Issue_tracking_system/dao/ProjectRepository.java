package com.aziz.Issue_tracking_system.dao;

import com.aziz.Issue_tracking_system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
