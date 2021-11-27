package com.example.jants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository user_repository;

    // homePage view when first entering the site
    @GetMapping(path = "/")
    public String homePage() {
        return "home_page";
    }

    // post mapping to reset password
    @PostMapping(path = "/reset_password")
    public String resetPassword()
    {
        return "reset_password";
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
    public String registerUser(User user) throws Exception{
        User find_user = user_repository.findByEmail(user.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_password = passwordEncoder.encode(user.getPassword());
        if(find_user == null) {
            user.setEmail(user.getEmail());
            user.setPassword(encoded_password);
            user.setFirst_name(user.getFirst_name());
            user.setLast_name(user.getLast_name());
            user_repository.save(user);
        }
        return "registration_success";
    }

    @GetMapping(path = "/drinks")
    public String drinksPage(Model model){

        return "drinks";
    }

}
