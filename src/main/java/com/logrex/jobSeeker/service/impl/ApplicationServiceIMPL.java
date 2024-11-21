package com.logrex.jobSeeker.service.impl;

import com.logrex.jobSeeker.utils.ApplicationStatus;
import com.logrex.jobSeeker.dto.ApplicationDTO;
import com.logrex.jobSeeker.entity.Application;
import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.JobSeeker;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.ApplicationRepository;
import com.logrex.jobSeeker.jpa.JobPostingRepository;
import com.logrex.jobSeeker.jpa.JobSeekerRepository;
import com.logrex.jobSeeker.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceIMPL implements ApplicationService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    @Autowired
    private JobPostingRepository postingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Override
    public ApplicationDTO applyForJob(Long jobPostingId, ApplicationDTO applicationDTO) {

        //  currently authenticated user's username
        String username=((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        System.out.println(username);
// Fetch the JobSeeker by the logged-in user's details
        JobSeeker jobSeeker = jobSeekerRepository.findByUser_Email(username).orElseThrow(()->new ResourceNotFoundException("Job post","id",jobPostingId));

        JobPosting jobPosting=postingRepository.findById(jobPostingId).orElseThrow(()->new ResourceNotFoundException("Job post","id",jobPostingId));

        Application application= modelMapper.map(applicationDTO, Application.class);
        application.setJobSeeker(jobSeeker);
        application.setJobPosting(jobPosting);
           applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.class);
    }
/// this section for recruiter //
    @Override
    public ApplicationDTO updateApplicationStatus(Long applicationId, String status) {
        Application application= applicationRepository.findById(applicationId).orElseThrow(()->new ResourceNotFoundException("Application","id",applicationId));
        if (!status.equalsIgnoreCase("Accepted") &&
                !status.equalsIgnoreCase("Rejected") &&
                !status.equalsIgnoreCase("Pending")) {
            throw new IllegalArgumentException("Invalid status value");
        }
        ApplicationStatus applicationStatus;
        try {
            applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        application.setStatus(applicationStatus);
        Application updatedApplication = applicationRepository.save(application);
        return modelMapper.map(updatedApplication, ApplicationDTO.class);
    }

//    @Override
//    public List<ApplicationDTO> getJobSeekersByJobPosting(Long jobId) {
//
//        List<Application> applications= applicationRepository.findByJobPosting_jobId(4L);
//
//        List<ApplicationDTO> applicationDTOs= applications.stream().map(
//                application -> modelMapper.map(application,ApplicationDTO.class)
//        ).collect(Collectors.toList());
//
//        return applicationDTOs;
//    }
}
