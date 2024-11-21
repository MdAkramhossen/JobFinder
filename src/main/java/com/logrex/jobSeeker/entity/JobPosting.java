package com.logrex.jobSeeker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_posting")
@Data

public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String jobTitle;
    private String jobDescription;
    private String jobType;  // Full-time, Part-time, etc.
    private String workMode;  // Remote, On-site, Hybrid
    private LocalDate postingDate;
    private LocalDate closingDate;
    private String location;
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonBackReference("recruiter-jobposting")
    private Recruiter recruiter;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("jobposting-application")
    private List<Application> applicationsReceived = new ArrayList<>();
}
