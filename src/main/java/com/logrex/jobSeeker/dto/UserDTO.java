package com.logrex.jobSeeker.dto;


import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String fullName;
    private String email;
    private String phoneNumber;

    private JobSeekerDTO jobSeeker;
    private RecruiterDTO recruiter;
}
