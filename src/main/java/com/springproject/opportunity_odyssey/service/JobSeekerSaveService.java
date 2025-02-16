package com.springproject.opportunity_odyssey.service;

import com.springproject.opportunity_odyssey.entity.JobPostActivity;
import com.springproject.opportunity_odyssey.entity.JobSeekerApply;
import com.springproject.opportunity_odyssey.entity.JobSeekerProfile;
import com.springproject.opportunity_odyssey.entity.JobSeekerSave;
import com.springproject.opportunity_odyssey.repository.JobSeekerApplyRepository;
import com.springproject.opportunity_odyssey.repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}



