package com.aziz.Issue_tracking_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title must not be left empty")
    private String title;


    @NotBlank(message = "Description must not be left empty")
    private String description;


    @ManyToOne
    private User user;//created by this user;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "issue")
    private List<ResolvedIssue> resolvedIssues;

    private int priority;

    private String priorityText;

    private String status;

    public Issue(){
        this.resolvedIssues = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", priority='" + priority + '\'' +
                ", priorityText='" + priorityText + '\'' +
                ", status='" + status + '\'' +
                ", resolvedIssues='" + resolvedIssues + '\'' +


                '}';
    }
}
