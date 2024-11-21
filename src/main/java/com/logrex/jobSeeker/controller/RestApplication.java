package com.logrex.jobSeeker.controller;

import com.logrex.jobSeeker.utils.ApplicationStatus;
import com.logrex.jobSeeker.dto.ApplicationDTO;
import com.logrex.jobSeeker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class RestApplication {

    @Autowired
    private ApplicationService applicationService;


    @PreAuthorize("hasAnyRole('ROLE_JOBSEEKER','ROLE_RECRUITER')")
    @PostMapping("/apply/{jobPostingId}")
    public ResponseEntity<ApplicationDTO> applyForJob(

            @PathVariable Long jobPostingId,
            @RequestBody ApplicationDTO applicationDTO
    ) {

        applicationDTO.setStatus(ApplicationStatus.PENDING);
        ApplicationDTO savedApplication = applicationService.applyForJob( jobPostingId, applicationDTO);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    

    // Method for recruiters to update the status of an application (Accepted/Rejected)
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status // "Accepted", "Rejected", or "Pending"
    ) {
        // Only recruiters should be able to update application status

        ApplicationDTO updatedApplication = applicationService.updateApplicationStatus(applicationId, status);

        return ResponseEntity.ok(updatedApplication);
    }

}
