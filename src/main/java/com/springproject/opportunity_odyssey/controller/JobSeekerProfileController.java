package com.springproject.opportunity_odyssey.controller;


import com.springproject.opportunity_odyssey.entity.JobSeekerProfile;
import com.springproject.opportunity_odyssey.entity.RecruiterProfile;
import com.springproject.opportunity_odyssey.entity.Skills;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.repository.UsersRepository;
import com.springproject.opportunity_odyssey.service.JobSeekerProfileService;
import com.springproject.opportunity_odyssey.service.UsersService;
import com.springproject.opportunity_odyssey.util.FileDownloadUtil;
import com.springproject.opportunity_odyssey.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile/")
public class JobSeekerProfileController {

    private UsersRepository usersRepository;
    private JobSeekerProfileService jobSeekerProfileService;

    @Autowired
    public JobSeekerProfileController(UsersRepository usersRepository, JobSeekerProfileService jobSeekerProfileService) {this.usersRepository = usersRepository;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.findById(user.getUserId());
            if(seekerProfile.isPresent()){
                jobSeekerProfile=seekerProfile.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }

            }
            model.addAttribute("skills",skills);
            model.addAttribute("profile",jobSeekerProfile);

        }
        return "job-seeker-profile";
    }
    @PostMapping("/addNew")
    public String addNew(Model model, @RequestParam("image")MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf, JobSeekerProfile jobSeekerProfile){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());

        }
        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillsList);

        String imageName = "";
        String resumeName= "";
        if(!Objects.equals(image.getOriginalFilename(), "")){
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if(!Objects.equals(pdf.getOriginalFilename(), "")){
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);


        try{
            String uploadDir = "photos/candidate/"+jobSeekerProfile.getUserAccountId();
            if(!Objects.equals(image.getOriginalFilename(),"")){
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }
            if(!Objects.equals(pdf.getOriginalFilename(),"")){
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }



        return "redirect:/dashboard/";

    }
    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model){
        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.findById(id);

        model.addAttribute("profile", seekerProfile.get());

        return "job-seeker-profile";


    }
    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value="fileName") String fileName, @RequestParam(value="userID") String userId){

        FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
        Resource resource=null;


        try{
            resource = fileDownloadUtil.getFileAsResource("photos/candidate/" + userId,fileName);

        }catch(IOException io){
            return ResponseEntity.badRequest().build();

        }

        if(resource==null){
            return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);



        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";


        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);


    }


}
