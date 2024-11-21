package com.logrex.jobSeeker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job_seekers")
@Data
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumeUrl;

    @ElementCollection
    private Set<String> skills = new HashSet<>();

    @Column(name = "experience_years")
    private Integer experienceYears;

    private String education;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id")
    private List<WorkExperience> workExperience = new ArrayList<>();

    private String portfolioUrl;
    private String currentSalary;
    private String preferredSalary;
    private String location;
    private LocalDate dateRegistered;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("jobseeker-application")
    private List<Application> applicationsMade = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-jobseeker")
    private User user;
}
