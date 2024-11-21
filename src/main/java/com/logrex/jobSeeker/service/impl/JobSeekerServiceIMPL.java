package com.logrex.jobSeeker.service.impl;

import com.logrex.jobSeeker.dto.JobSeekerDTO;
import com.logrex.jobSeeker.entity.JobSeeker;
import com.logrex.jobSeeker.entity.Role;
import com.logrex.jobSeeker.entity.User;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.JobSeekerRepository;
import com.logrex.jobSeeker.jpa.RoleRepository;
import com.logrex.jobSeeker.jpa.UserRepository;
import com.logrex.jobSeeker.service.JobSeekerService;
import com.logrex.jobSeeker.utils.USER_ROLE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekerServiceIMPL implements JobSeekerService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
     private JobSeekerRepository jobSeekerRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public JobSeekerDTO saveJobSeeker(JobSeekerDTO jobSeekerDTO) {

        JobSeeker jobSeeker=modelMapper.map(jobSeekerDTO, JobSeeker.class);
        // Get the currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        // Fetch user details from the database using the username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email"));
        Role recruiterRome=roleRepository.findByName(USER_ROLE.ROLE_RECRUITER.name()).orElseThrow(()-> new ResourceNotFoundException("Role", "recruiter"));
        jobSeeker.setUser(user);
        jobSeekerRepository.save(jobSeeker);

        return modelMapper.map(jobSeekerDTO, JobSeekerDTO.class);
    }

    @Override
    public List<JobSeekerDTO> getAllJobSeekerrs() {
        List<JobSeeker> jobSeekers=jobSeekerRepository.findAll();
        List<JobSeekerDTO> jobSeekerDTOs= jobSeekers.stream().map(jobSeeker ->
                modelMapper.map(jobSeeker, JobSeekerDTO.class)).collect(Collectors.toList());
        return jobSeekerDTOs;
    }

    @Override
    public JobSeekerDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO) {

        // Get the currently authenticated user's username
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        // user will find by login users
        JobSeeker existingJobSeeker= jobSeekerRepository.findByUser_Email(username).orElseThrow(() -> new UsernameNotFoundException("This user does not exist by this email"));

        JobSeeker jobSeeker=modelMapper.map(jobSeekerDTO, JobSeeker.class);
        jobSeeker.setUser(existingJobSeeker.getUser());
        JobSeeker updatedJobSeeker = jobSeekerRepository.save(jobSeeker);
        return modelMapper.map(updatedJobSeeker, JobSeekerDTO.class);
    }
}
