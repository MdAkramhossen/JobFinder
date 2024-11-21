package com.logrex.jobSeeker.controller;

import com.logrex.jobSeeker.dto.JobSeekerDTO;
import com.logrex.jobSeeker.dto.RecruiterDTO;
import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.Recruiter;
import com.logrex.jobSeeker.entity.User;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.RecruiterRepository;
import com.logrex.jobSeeker.jpa.UserRepository;
import com.logrex.jobSeeker.service.RecruiterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recruiter")
public class RestRecruiter {


    @Autowired
    private RecruiterService recruiterService;

    // Create a new recruiter
    @PreAuthorize("hasAnyRole('ROLE_JOBSEEKER','ROLE_RECRUITER')")
    @PostMapping
    public ResponseEntity<String> createRecruiters(@RequestBody RecruiterDTO recruiterDTO) {

        recruiterService.createRecruiters(recruiterDTO);

       return  new ResponseEntity<>("Recruiter created successfully!",HttpStatus.CREATED);

    }

    // Get all job seekers who applied to a specific job posting
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @GetMapping("/job-postings/{jobId}/applications")
    public ResponseEntity<List<JobSeekerDTO>> getJobSeekersForJobPost(@PathVariable Long jobId) {

        List<JobSeekerDTO> jobSeekers = recruiterService.getJobSeekersByJobPost(jobId);
        return new ResponseEntity<>(jobSeekers, HttpStatus.OK);
    }

    // Update recruiter details
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @PutMapping
    public ResponseEntity<String> updateRecruiters(@RequestBody RecruiterDTO recruiterDTO) {
        recruiterService.updateRecruiters(recruiterDTO);
        return new ResponseEntity<>("Recruiter updated successfully!",HttpStatus.OK);
    }
    // Get all job postings created by a specific recruiter
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @GetMapping("/job-postings")
    public ResponseEntity<List<JobPosting>> getAllJobPostingsByRecruiter() {
        List<JobPosting> jobPostings = recruiterService.getAllJobPostingsByRecruiter();
        return new ResponseEntity<>(jobPostings, HttpStatus.OK);
    }

    // Get all recruiters (Admin)

//    @GetMapping("/recruiters")
//    public ResponseEntity<List<RecruiterDTO>> getAllRecruiters() {
//
//        List<RecruiterDTO> recruiterDTOS= recruiterService.getAllRecruiters();
//
//        return new ResponseEntity<>(recruiterDTOS, HttpStatus.OK);
//    }




}
