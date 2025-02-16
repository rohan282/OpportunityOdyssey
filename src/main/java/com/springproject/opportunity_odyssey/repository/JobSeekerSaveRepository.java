package com.springproject.opportunity_odyssey.repository;


import com.springproject.opportunity_odyssey.entity.JobPostActivity;
import com.springproject.opportunity_odyssey.entity.JobSeekerProfile;
import com.springproject.opportunity_odyssey.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {


    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);




}
