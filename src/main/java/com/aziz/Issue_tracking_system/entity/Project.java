package com.aziz.Issue_tracking_system.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Project's name must not be empty")
    @Size(max = 50, message = "Project's name must not exceed 50 characters")
    private String name;
    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    @NotBlank(message = "Project's description must not be empty")
    @Size(max = 100, message = "Project's description must not exceed 100 characters")
    private String description;

    public Project(String name, List<Issue> issues, String description){
        this.name = name;
        this.issues = issues;
        this.description = description;
        this.issues = new ArrayList<>();
    }

    public Project(){}






}
