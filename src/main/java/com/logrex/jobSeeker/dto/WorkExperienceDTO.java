package com.logrex.jobSeeker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceDTO {

    private Long id;
    private String company;
    private String position;
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;

}