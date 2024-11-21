package com.logrex.jobSeeker.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.logrex.jobSeeker.entity.JobPosting;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class RecruiterDTO {

    private Long id;
    private Long userId;  // Linking back to the UserDTO without circular reference

    private String companyName;
    private String companyDescription;
    private String companyLogoUrl;
    private String linkedInProfileUrl;
    private LocalDate dateRegistered;

    private List<JobPostingDTO> jobPostings = new ArrayList<>();
}