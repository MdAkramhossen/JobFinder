package com.logrex.jobSeeker.controller;

import com.logrex.jobSeeker.dto.JobSeekerDTO;
import com.logrex.jobSeeker.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseekers")
public class RestJobSeeker {

    @Autowired
    private JobSeekerService jobSeekerService;


    @PreAuthorize("hasAnyRole('ROLE_JOBSEEKER','ROLE_RECRUITER')")
        @PostMapping
         public ResponseEntity<JobSeekerDTO> createJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO) {

             JobSeekerDTO savedJobSeeker = jobSeekerService.saveJobSeeker(jobSeekerDTO);
             return ResponseEntity.status(201).body(savedJobSeeker);
         }

         /// BY Admin
         @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
         @GetMapping
          public ResponseEntity<List<JobSeekerDTO>> getAllJobSeekers() {

        List <JobSeekerDTO> jobSeekerDTOS=jobSeekerService.getAllJobSeekerrs();
        return  new ResponseEntity<>(jobSeekerDTOS, HttpStatus.OK);
         }

    @PreAuthorize("hasAnyRole('ROLE_JOBSEEKER')")
    @PutMapping
    public ResponseEntity<JobSeekerDTO> updateJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO) {
        JobSeekerDTO updatedJobSeeker =  jobSeekerService.updateJobSeeker(jobSeekerDTO);
        return ResponseEntity.ok(updatedJobSeeker);
    }

}
