package com.example.jants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepository user_repository;

    // homePage view when first entering the site
    @GetMapping(path = "/")
    public String homePage() {
        return "home_page";
    }

    // when the register button is clicked on the homepage, the register page will show up
    @GetMapping(path = "/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    // post mapping to register user in the database
    // uses BCrypt encoding from Spring Security lab
    @PostMapping(path = "/registration_success")
    public String registerUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(user.getPassword());
        String encoded_password = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded_password);
        user_repository.save(user);

        return "registration_success";



    }
}
