package com.logrex.jobSeeker.jpa;

import com.logrex.jobSeeker.entity.JobPosting;
import com.logrex.jobSeeker.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByRecruiter(Recruiter recruiter);




    @Query("SELECT j FROM JobPosting j WHERE "
            + "(:title IS NULL OR j.jobTitle = :title) AND "
            + "(:location IS NULL OR (j.location IS NULL OR j.location = :location)) AND "
            + "(:company IS NULL OR j.recruiter.companyName = :company) AND "
            + "(:jobType IS NULL OR j.jobType = :jobType)")
    List<JobPosting> findJobPostings(@Param("title") String title,
                                     @Param("location") String location,
                                     @Param("company") String company,
                                     @Param("jobType") String jobType);


    List<JobPosting> findByRecruiterId(Long email);
}
