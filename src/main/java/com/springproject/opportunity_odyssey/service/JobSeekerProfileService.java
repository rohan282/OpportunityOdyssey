package com.springproject.opportunity_odyssey.service;


import com.springproject.opportunity_odyssey.entity.JobSeekerProfile;
import com.springproject.opportunity_odyssey.entity.RecruiterProfile;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.repository.JobSeekerProfileRepository;
import com.springproject.opportunity_odyssey.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {

    private JobSeekerProfileRepository jobSeekerProfileRepository;
    private UsersRepository usersRepository;

    @Autowired
    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository, UsersRepository usersRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<JobSeekerProfile> findById(Integer id){
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);


    }

    public JobSeekerProfile getCurrentSeekerProfile() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> jobSeekerProfile= findById(user.getUserId());
            return jobSeekerProfile.orElse(null);

        } else return null;

    }
}
