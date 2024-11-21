package com.logrex.jobSeeker.service;

import com.logrex.jobSeeker.dto.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO applyForJob(Long jobPostingId, ApplicationDTO applicationDTO);

    ApplicationDTO updateApplicationStatus(Long applicationId, String status);

    // List<ApplicationDTO> getJobSeekersByJobPosting(Long jobId);
}
