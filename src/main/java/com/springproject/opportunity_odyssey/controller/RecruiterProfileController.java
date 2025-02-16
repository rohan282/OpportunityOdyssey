package com.springproject.opportunity_odyssey.controller;


import com.springproject.opportunity_odyssey.entity.RecruiterProfile;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.repository.UsersRepository;
import com.springproject.opportunity_odyssey.service.RecruiterProfileService;
import com.springproject.opportunity_odyssey.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("username not found"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.findById(users.getUserId());

            if(!recruiterProfile.isEmpty()){
                model.addAttribute("profile",recruiterProfile.get());
            }

        }
        return "recruiter_profile";

    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());

            model.addAttribute("profile", recruiterProfile);

            String fileName="";

            if(!(multipartFile.getOriginalFilename().equals(""))){
                fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

                recruiterProfile.setProfilePhoto(fileName);
            }
            RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
            String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();

            try{
                FileUploadUtil.saveFile( uploadDir,fileName, multipartFile);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return "redirect:/dashboard/";
    }



}
