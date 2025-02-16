package com.springproject.opportunity_odyssey.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class RecruiterJobsDTO {

    private Long totalCandidates;
    private JobLocation jobLocationId;
    private JobCompany jobCompanyId;
    private String jobTitle;
    private Integer jobPostId;

    @Autowired
    public RecruiterJobsDTO(Long totalCandidates, JobLocation jobLocationId, JobCompany jobCompanyId, String jobTitle, Integer jobPostId) {
        this.totalCandidates = totalCandidates;
        this.jobLocationId = jobLocationId;
        this.jobCompanyId = jobCompanyId;
        this.jobTitle = jobTitle;
        this.jobPostId = jobPostId;
    }

    public Long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(Long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public JobLocation getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(JobLocation jobLocationId) {
        this.jobLocationId = jobLocationId;
    }

    public JobCompany getJobCompanyId() {
        return jobCompanyId;
    }

    public void setJobCompanyId(JobCompany jobCompanyId) {
        this.jobCompanyId = jobCompanyId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }
}
