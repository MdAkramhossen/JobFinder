package com.logrex.jobSeeker.service;

import com.logrex.jobSeeker.dto.JobSeekerDTO;

import java.util.List;

public interface JobSeekerService {
    JobSeekerDTO saveJobSeeker(JobSeekerDTO jobSeekerDTO);

    List<JobSeekerDTO> getAllJobSeekerrs();

    JobSeekerDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO);
}
