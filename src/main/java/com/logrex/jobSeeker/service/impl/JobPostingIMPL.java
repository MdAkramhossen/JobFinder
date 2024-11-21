package com.logrex.jobSeeker.service.impl;

import com.logrex.jobSeeker.dto.JobPostingDTO;
import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.Recruiter;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.JobPostingRepository;
import com.logrex.jobSeeker.jpa.RecruiterRepository;
import com.logrex.jobSeeker.service.JobPostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostingIMPL implements JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Override
    public void createJobPosting(JobPostingDTO jobPostingDTO) {

        //  currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        // Fetch the recruiter by the logged-in user's details
        Recruiter recruiter = recruiterRepository.findByUser_Email(username).orElseThrow(() -> new ResourceNotFoundException("Recruiter", "email", null));

      JobPosting jobPosting = modelMapper.map(jobPostingDTO, JobPosting.class);
        jobPosting.setRecruiter(recruiter);
        jobPostingRepository.save(jobPosting);
    }


    @Override
    public void updateJobPosting(Long jobId, JobPostingDTO jobPostingDTO) {

        JobPosting jobPosting = jobPostingRepository.findById(jobId).orElseThrow(() ->new ResourceNotFoundException("Job", "id", jobId));
        jobPostingRepository.save(jobPosting);

    }

    @Override
    public void deleteJobPosting(Long jobId) {

        JobPosting jobPosting = jobPostingRepository.findById(jobId).orElseThrow(() ->new ResourceNotFoundException("Job", "id", jobId));
        jobPostingRepository.delete(jobPosting);

    }

    @Override
    public List<JobPostingDTO> getAllJobs() {


        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        return jobPostings.stream().map(jobs->modelMapper.map(jobs, JobPostingDTO.class)).collect(Collectors.toList());
    }

 // Make sure to import this

    @Override
    public List<JobPostingDTO> searchJobPostings(String title, String location, String company, String jobType) {

        // Check if all parameters are empty or null
        if (!StringUtils.hasText(title) && !StringUtils.hasText(location)
                && !StringUtils.hasText(company) && !StringUtils.hasText(jobType)) {
            List<JobPosting> jobPostings = jobPostingRepository.findAll();
            return jobPostings.stream()
                    .map(jobPosting -> modelMapper.map(jobPosting, JobPostingDTO.class))
                    .collect(Collectors.toList());
        }

        // If any filter is provided, apply the custom query
        List<JobPosting> jobPostings = jobPostingRepository.findJobPostings(title, location, company, jobType);
        return jobPostings.stream()
                .map(jobPosting -> modelMapper.map(jobPosting, JobPostingDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<JobPostingDTO> listJobPostingByRecruiter() {

        //  currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        // Fetch the recruiter by the logged-in user's details
        Recruiter recruiter = recruiterRepository.findByUser_Email(username).orElseThrow(() -> new ResourceNotFoundException("Recruiter", "email", null));
        List<JobPosting> jobPostings = jobPostingRepository.findByRecruiterId(recruiter.getId());
        if (jobPostings.isEmpty()) {
            return (List<JobPostingDTO>) ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<JobPostingDTO> jobPostingDTOS = jobPostings.stream().map(jobPosting -> {

            return modelMapper.map(jobPosting, JobPostingDTO.class);
        }).collect(Collectors.toList());
        System.out.println(recruiter.getCompanyName());
        return jobPostingDTOS;
    }

}
