package com.logrex.jobSeeker.controller;

import com.logrex.jobSeeker.dto.JobPostingDTO;
import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.Recruiter;
import com.logrex.jobSeeker.jpa.JobPostingRepository;
import com.logrex.jobSeeker.jpa.RecruiterRepository;
import com.logrex.jobSeeker.service.JobPostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/job-postings")
public class RestPosting {

    @Autowired
    private JobPostingService jobPostingService;

    // Create a new job posting
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @PostMapping("/create")
    public ResponseEntity<String> createJobPosting(@RequestBody JobPostingDTO jobPostingDTO) {

        jobPostingService.createJobPosting(jobPostingDTO);


        return ResponseEntity.status(HttpStatus.CREATED).body("Job Posting created successfully");
    }

    // Update a job posting
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @PutMapping("/{jobId}")
    public ResponseEntity <String> updateJobPostingByJobId(Long jobId, JobPostingDTO jobPostingDTO){

        jobPostingService.updateJobPosting(jobId,jobPostingDTO);

        return new ResponseEntity<>("Job Posting updated successfully", HttpStatus.OK);
    }

    // Delete a job posting
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @DeleteMapping("/{jobId}")
    public ResponseEntity <String> deleteJobPostingByJobId(Long jobId){

             jobPostingService.deleteJobPosting(jobId);

        return new ResponseEntity<>("Job Posting deleted successfully", HttpStatus.OK);
    }

    // Get all job postings (Admin)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getAllJobPosting(){

        List<JobPostingDTO> jobPostingDTOS= jobPostingService.getAllJobs();
        return new ResponseEntity<>(jobPostingDTOS, HttpStatus.OK);
    }
    // Get all job postings by a specific recruiter
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @GetMapping("/recruiters")
    public ResponseEntity<List<JobPostingDTO>> listJobPostingByRecruiter() {

        List<JobPostingDTO> jobs= jobPostingService.listJobPostingByRecruiter();
        return ResponseEntity.ok(jobs);
    }

    // Public method for jobseekers to view and filter job postings
    @GetMapping("/search")
    public ResponseEntity<List<JobPostingDTO>> searchJobPostings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String jobType) {
        System.out.println(jobType);
        List<JobPostingDTO> jobPostings = jobPostingService.searchJobPostings(title, location, company, jobType);
        return new ResponseEntity<>(jobPostings, HttpStatus.OK);
    }


}
