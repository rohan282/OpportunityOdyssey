package com.springproject.opportunity_odyssey.repository;

import com.springproject.opportunity_odyssey.entity.JobPostActivity;
import com.springproject.opportunity_odyssey.entity.JobSeekerApply;
import com.springproject.opportunity_odyssey.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);


    List<JobSeekerApply> findByJob(JobPostActivity job);







}
