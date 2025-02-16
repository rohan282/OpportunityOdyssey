package com.springproject.opportunity_odyssey.service;


import com.springproject.opportunity_odyssey.entity.RecruiterProfile;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.entity.UsersType;
import com.springproject.opportunity_odyssey.repository.JobSeekerProfileRepository;
import com.springproject.opportunity_odyssey.repository.RecruiterProfileRepository;
import com.springproject.opportunity_odyssey.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersTypeService {
    private UsersTypeRepository usersTypeRepository;


    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> findAll(){
        return usersTypeRepository.findAll();
    }



//    public Users save(Users user){
//        return usersTypeRepository.save(user);
//    }
}
