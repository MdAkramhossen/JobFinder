package com.logrex.jobSeeker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruiter")
@Data
@ToString
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-recruiter")
    private User user;

    private String companyName;
    private String companyDescription;
    private String companyLogoUrl;
    private String linkedInProfileUrl;
    private LocalDate dateRegistered;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    @JsonManagedReference("recruiter-jobposting")
    private List<JobPosting> jobPostings = new ArrayList<>();


}