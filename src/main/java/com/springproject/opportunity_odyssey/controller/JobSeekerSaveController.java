package com.springproject.opportunity_odyssey.controller;


import com.springproject.opportunity_odyssey.entity.*;
import com.springproject.opportunity_odyssey.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {

    private UsersService usersService;
    private JobSeekerProfileService jobSeekerProfileService;
    private JobSeekerSaveService jobSeekerSaveService;
    private JobPostActivityService jobPostActivityService;

    @Autowired
    public JobSeekerSaveController(UsersService usersService, JobSeekerProfileService jobSeekerProfileService, JobSeekerSaveService jobSeekerSaveService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.jobPostActivityService = jobPostActivityService;
    }


    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id")int id, JobSeekerSave jobSeekerSave){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Optional<Users> user = usersService.getUsersByEmail(username);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.findById(user.get().getUserId());

            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if(seekerProfile.isPresent() && jobPostActivity!=null){
                //jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile.get());

            }
            else{
                throw new RuntimeException("user not found");
            }
            jobSeekerSaveService.addNew(jobSeekerSave);

        }
        return "redirect:/dashboard/";

    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model){
        List<JobPostActivity> jobPost = new ArrayList<>();

        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<JobSeekerSave> savedJobs = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);

        for(JobSeekerSave jobSeekerSave:savedJobs){
            jobPost.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost",jobPost);
        model.addAttribute("user",currentUserProfile);

        return "saved-jobs";


    }

}
