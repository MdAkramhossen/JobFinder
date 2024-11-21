package com.logrex.jobSeeker.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.logrex.jobSeeker.entity.Application;
import com.logrex.jobSeeker.entity.Recruiter;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class JobPostingDTO {

    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String jobType;  // Full-time, Part-time, etc.
    private String workMode;  // Remote, On-site, Hybrid
    private LocalDate postingDate;
    private LocalDate closingDate;

    private Long recruiterId;  // Linking back to the RecruiterDTO without circular reference

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApplicationDTO> applicationsReceived = new ArrayList<>();
}
