package com.aziz.Issue_tracking_system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class ResolvedIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String type;

    @OneToOne
    private Issue issue;

    @Lob
    private byte [] file;

    public ResolvedIssue(String name, String type, Issue issue){
        this.name = name;
        this.type = type;
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "ResolvedIssue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
