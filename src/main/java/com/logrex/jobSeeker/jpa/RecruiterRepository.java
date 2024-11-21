package com.logrex.jobSeeker.jpa;

import com.logrex.jobSeeker.entity.Recruiter;
import com.logrex.jobSeeker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter> findByUser_UserId(Long userId);

    Optional<Recruiter> findByUser_Email(String username);
}
