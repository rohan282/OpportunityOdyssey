package com.springproject.opportunity_odyssey.controller;


import com.springproject.opportunity_odyssey.entity.*;
import com.springproject.opportunity_odyssey.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {

    private UsersService usersService;
    private JobPostActivityService jobPostActivityService;
    private JobSeekerSaveService jobSeekerSaveService;
    private JobSeekerApplyService jobSeekerApplyService;
    private RecruiterProfileService recruiterProfileService;
    private JobSeekerProfileService jobSeekerProfileService;


    @Autowired
    public JobSeekerApplyController(UsersService usersService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService, JobSeekerApplyService jobSeekerApplyService, RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id")int id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if(user!=null){
                    model.addAttribute("applyList", jobSeekerApplyList);

                }


            }
            else{
                JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
                if(user!=null){
                    boolean exists=false;
                    boolean saved =false;
                    for(JobSeekerApply jobSeekerApply: jobSeekerApplyList){
                        if(jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()){
                            exists=true;
                            break;

                        }

                    }
                    for(JobSeekerSave jobSeekerSave: jobSeekerSaveList){
                        if(jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()){
                            saved=true;
                            break;

                        }

                    }
                    model.addAttribute("alreadySaved",saved);
                    model.addAttribute("alreadyApplied",exists);


                }
            }

        }
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob",jobSeekerApply);



        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user",usersService.getCurrentUserProfile());

        return "job-details";

    }
    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id")int id, JobSeekerApply jobSeekerApply){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Optional<Users> user = usersService.getUsersByEmail(username);

            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.findById(user.get().getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if(seekerProfile.isPresent() && jobPostActivity!=null){
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setUserId(seekerProfile.get());
                jobSeekerApply.setApplyDate(new Date());
            }
            else{
                throw new RuntimeException("user not found");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);

        }
        return "redirect:/dashboard/";

    }


}
