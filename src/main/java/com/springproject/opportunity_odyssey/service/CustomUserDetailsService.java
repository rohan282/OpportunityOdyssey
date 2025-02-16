package com.springproject.opportunity_odyssey.service;

import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.repository.UsersRepository;
import com.springproject.opportunity_odyssey.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UsersRepository usersRepository;
    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("could not find user"));
        return new CustomUserDetails(user);
    }
}
