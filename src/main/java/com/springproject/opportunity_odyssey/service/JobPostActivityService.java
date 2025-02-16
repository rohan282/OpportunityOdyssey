package com.springproject.opportunity_odyssey.service;

import com.springproject.opportunity_odyssey.entity.*;
import com.springproject.opportunity_odyssey.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostActivityService {

    private JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){
        return jobPostActivityRepository.save(jobPostActivity);

    }
    public List<RecruiterJobsDTO> getRecruiterJobs(int recruiter){
        List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDTO> recruiterJobsDTOList = new ArrayList<>();

        for(IRecruiterJobs rec : recruiterJobsDtos){
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getCountry(), rec.getState());
            JobCompany comp = new JobCompany(rec.getCompanyId(),"",rec.getName());
            recruiterJobsDTOList.add(new RecruiterJobsDTO(rec.getTotalCandidates(), loc, comp, rec.getJob_title(), rec.getJob_post_id()));
        }

        return recruiterJobsDTOList;
    }

    public JobPostActivity getOne(int id) {

        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("job not found"));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate) ? jobPostActivityRepository.searchWithoutDate(job,location,remote,type) :
                jobPostActivityRepository.search(job,location,remote,type,searchDate);
    }
}
