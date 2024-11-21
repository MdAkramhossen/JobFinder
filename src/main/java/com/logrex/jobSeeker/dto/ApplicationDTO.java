package com.logrex.jobSeeker.dto;


import com.logrex.jobSeeker.utils.ApplicationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ApplicationDTO {

    private Long applicationId;

    private Long jobSeekerId;  // Linking back to the JobSeekerDTO without circular reference
    private Long jobPostingId;  // Linking back to the JobPostingDTO without circular reference

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  // Pending, Accepted, Rejected
}
