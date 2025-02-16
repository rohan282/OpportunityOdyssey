package com.springproject.opportunity_odyssey.controller;
import com.springproject.opportunity_odyssey.entity.Users;
import com.springproject.opportunity_odyssey.entity.UsersType;
import com.springproject.opportunity_odyssey.service.UsersService;
import com.springproject.opportunity_odyssey.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private UsersTypeService usersTypeService;
    private UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }


    @GetMapping("/register")
    public String register(Model theModel){
        List<UsersType> usersType = usersTypeService.findAll();
        theModel.addAttribute("getAllTypes",usersType);
        theModel.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration( @Valid Users users,Model theModel ){
        //System.out.println("users" +users);
        Optional<Users> optionalUsers = usersService.getUsersByEmail(users.getEmail());
        if(optionalUsers.isEmpty()){
            usersService.addNew(users);
            return "redirect:/dashboard/";

        }
        else{
            String message = "email already exists";
            theModel.addAttribute("error", message);
            List<UsersType> usersType = usersTypeService.findAll();
            theModel.addAttribute("getAllTypes",usersType);
            theModel.addAttribute("user", new Users());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);

        }
        return "redirect:/";

}




//    @GetMapping("/login")
//    public String login(@ModelAttribute("user") Users user, @RequestParam("email") String email){
//        users
//
//
//
//        return "index";
//        }
//    }





}
