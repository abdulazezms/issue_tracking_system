package com.aziz.Issue_tracking_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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




}
