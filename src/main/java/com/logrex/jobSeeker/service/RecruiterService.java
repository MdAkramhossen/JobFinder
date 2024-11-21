package com.logrex.jobSeeker.service;

import com.logrex.jobSeeker.dto.JobSeekerDTO;
import com.logrex.jobSeeker.dto.RecruiterDTO;
import com.logrex.jobSeeker.entity.JobPosting;

import java.util.List;

public interface RecruiterService {
    void createRecruiters(RecruiterDTO recruiterDTO);

    List<JobSeekerDTO> getJobSeekersByJobPost(Long jobId);

    void updateRecruiters(RecruiterDTO recruiterDTO);

    List<RecruiterDTO> getAllRecruiters();

    List<JobPosting> getAllJobPostingsByRecruiter();


}
