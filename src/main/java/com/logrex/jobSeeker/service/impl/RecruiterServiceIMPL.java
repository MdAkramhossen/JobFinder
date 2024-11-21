package com.logrex.jobSeeker.service.impl;

import com.logrex.jobSeeker.dto.JobSeekerDTO;
import com.logrex.jobSeeker.dto.RecruiterDTO;
import com.logrex.jobSeeker.entity.Application;
import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.Recruiter;
import com.logrex.jobSeeker.entity.User;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.*;
import com.logrex.jobSeeker.service.RecruiterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruiterServiceIMPL implements RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Override
    public void createRecruiters(RecruiterDTO recruiterDTO) {
        Recruiter recruiter = modelMapper.map(recruiterDTO, Recruiter.class);

        // Get the currently authenticated user
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Fetch user details from the database using the username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username",null));

        System.out.println(user.getEmail()
        );
        // Set the user to recruiter and save
        recruiter.setUser(user);
        recruiterRepository.save(recruiter);
    }

    @Override
    public List<JobSeekerDTO> getJobSeekersByJobPost(Long jobId) {

        List<Application> applications= applicationRepository.findByJobPosting_jobId(jobId);

        return applications.stream()
                .map(application -> {

                    JobSeekerDTO jobSeekerDTO = modelMapper.map(application.getJobSeeker(), JobSeekerDTO.class);
                    return jobSeekerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateRecruiters(RecruiterDTO recruiterDTO) {
        //  currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Recruiter recruiter= recruiterRepository.findByUser_Email(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        Recruiter recruiters = modelMapper.map(recruiterDTO, Recruiter.class);

        recruiters.setId(recruiter.getId());
        recruiters.setUser(recruiter.getUser());
        recruiterRepository.save(recruiters);

    }
///Admin section
    @Override
    public List<RecruiterDTO> getAllRecruiters() {

       List<Recruiter> recruiters= recruiterRepository.findAll();
        return recruiters.stream().map(recruiter -> modelMapper.map(recruiter, RecruiterDTO.class)).collect(Collectors.toList()) ;
    }

    @Override
    public List<JobPosting> getAllJobPostingsByRecruiter() {


        //  currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        /// recruiter will come form log in section this for testing parpus
       Recruiter recruiter= recruiterRepository.findByUser_Email(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        System.out.println(recruiter.getId());
        List<JobPosting> jobPostings = jobPostingRepository.findByRecruiterId(recruiter.getId());

        return jobPostings.stream().map(jp -> modelMapper.map(jp, JobPosting.class)).collect(Collectors.toList());
    }
}
