package com.logrex.jobSeeker.jpa;

import com.logrex.jobSeeker.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {

    Optional<JobSeeker> findByUser_UserId(Long userId);

    Optional<JobSeeker> findByUser_Email(String email);

}
