package com.springproject.opportunity_odyssey.service;


import com.springproject.opportunity_odyssey.entity.RecruiterProfile;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.repository.RecruiterProfileRepository;
import com.springproject.opportunity_odyssey.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private RecruiterProfileRepository recruiterProfileRepository;
    private UsersRepository usersRepository;




    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository, UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfile> findById(Integer id){
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            Optional<RecruiterProfile> recruiterProfile= findById(user.getUserId());
            return recruiterProfile.orElse(null);

        } else return null;

    }
}
