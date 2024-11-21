package com.logrex.jobSeeker.jpa;

import com.logrex.jobSeeker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByJobPosting_jobId(Long jobPostingId);
//    List<Application> findByRecruiterId(Long recruiterId);
}
