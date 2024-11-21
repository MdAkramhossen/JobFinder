package com.logrex.jobSeeker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "work_experience")
@Data
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String company;
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;

}