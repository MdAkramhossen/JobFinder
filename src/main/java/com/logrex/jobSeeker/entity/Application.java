package com.logrex.jobSeeker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.logrex.jobSeeker.utils.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "application")
@Data

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    @JsonBackReference("jobseeker-application")
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name = "job_posting_id")
    @JsonBackReference("jobposting-application")
    private JobPosting jobPosting;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  // Pending, Accepted, Rejected

}
