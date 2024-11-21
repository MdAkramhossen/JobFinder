package com.logrex.jobSeeker.service;

import com.logrex.jobSeeker.dto.JobPostingDTO;

import java.util.List;

public interface JobPostingService {
    void createJobPosting(JobPostingDTO jobPostingDTO);

    List<JobPostingDTO> listJobPostingByRecruiter();

    void updateJobPosting(Long jobId, JobPostingDTO jobPostingDTO);

    void deleteJobPosting(Long jobId);

    List<JobPostingDTO> getAllJobs();

    List<JobPostingDTO> searchJobPostings(String title, String location, String company, String jobType);
}
