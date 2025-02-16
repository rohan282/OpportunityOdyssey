package com.springproject.opportunity_odyssey.entity;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="job_seeker_save", uniqueConstraints ={@UniqueConstraint(columnNames = {"job","userId"})})
public class JobSeekerSave {


    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="job")
    private JobPostActivity job;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private JobSeekerProfile userId;

    public JobSeekerSave() {
    }

    @Autowired
    public JobSeekerSave(Integer id, JobPostActivity job, JobSeekerProfile userId) {
        this.id = id;
        this.job = job;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public JobSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JobSeekerProfile userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JobSeekerSave{" +
                "id=" + id +
                ", jobPostId=" + job.toString() +
                ", userId=" + userId.toString() +
                '}';
    }
}


