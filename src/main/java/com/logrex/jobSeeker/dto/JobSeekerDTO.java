package com.logrex.jobSeeker.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.logrex.jobSeeker.entity.Application;
import com.logrex.jobSeeker.entity.WorkExperience;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class JobSeekerDTO {

    private Long id;
    private Long userId;  // Linking back to the UserDTO without circular reference

    private String resumeUrl;
    private Set<String> skills = new HashSet<>();
    private Integer experienceYears;
    private String education;
    private List<WorkExperienceDTO> workExperience;
    private String portfolioUrl;
    private String currentSalary;
    private String preferredSalary;
    private String location;
    private LocalDate dateRegistered;

    private List<ApplicationDTO> applicationsMade;
}
