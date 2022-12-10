package com.aziz.Issue_tracking_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @ManyToOne
    private User user;//created by this user;

    @ManyToOne
    private Project project;

    @OneToOne(mappedBy = "issue", cascade = CascadeType.ALL)
    private ResolvedIssue resolvedIssue;

    private int priority;

    @Lob
    private byte [] file;

    private String fileName;

    private String priorityText;

    private String status;

    public Issue(String description, User user, Project project){
        this.description = description;
        this.user = user;
        this.project = project;
    }


    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", priority='" + priority + '\'' +
                ", priorityText='" + priorityText + '\'' +
                ", status='" + status + '\'' +
                ", resolvedIssues='" + resolvedIssue + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
