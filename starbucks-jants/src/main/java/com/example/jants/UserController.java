package com.example.jants;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository user_repository;

    @Getter
    @Setter
    class Message{
        private String msg;
        public Message(String m) {this.msg = m;}
    }

    class ErrorMessages{
        private ArrayList<Message> messages = new ArrayList<Message>();
        public void add(String msg) {
            messages.add(new Message(msg));
        }
        public ArrayList<Message> getMessages() {
            return messages;
        }
        public void print(){
            for(Message m: messages){
                System.out.println(m.msg);
            }
        }
    }

    // homePage view when first entering the site
    @GetMapping("/")
    public String homePage() {
        return "home_page";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        List<User> list_of_users = user_repository.findAll();
        model.addAttribute("list_of_users", list_of_users);
        return "users";
    }

     // when the reset button is clicked on the homepage, the reset page will show up
    @GetMapping("/reset_password")
    public String resetPassword(User user)
    {
        return "reset_password";
    }
    
    // post mapping to reset password
    // @PostMapping("/password_changed")
    // public String passwordChanged(User user)
    // {
    //     User find_user = user_repository.findByEmail(user.getEmail());
    //     UserInfo uInfo = new UserInfo(find_user);
    //     ErrorMessages msgs = new ErrorMessages();
    //     BCryptPasswordEncoder newPasswordEncoder = new BCryptPasswordEncoder();
    //     String new_encoded_password = newPasswordEncoder.encode(find_user.getNew_password());
    //     if(find_user == null)
    //      {
    //         msgs.add("Email Address Not Found");
    //         msgs.print();
    //         return "reset_password";
    //     }
    //     String savedPassword = find_user.getPassword();
    //     if(savedPassword.equals(user.getPassword())) {
    //         find_user.setPassword(new_encoded_password);
    //         user_repository.save(find_user);
    //         return "password_changed";
    //     }
    //     return "reset_password";
    // }

    @PostMapping("/password_changed")
    public String passwordChanged(User user){
        User find_user = user_repository.findByEmail(user.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String savedPassword = find_user.getPassword();
        System.out.println(savedPassword);
        if(find_user != null){
            find_user.setPassword(passwordEncoder.encode(find_user.getNew_password()));
            user_repository.save(find_user);
            return "password_changed";
        }
        return null;
    }
 
    // when the login button is clicked on the homepage, the login page will show up
    @GetMapping("/login_page")
    public String loginPage(User user)
    {
        return "login_page";
    }

    // when login button is clicked on Login page, it will go here for validation
    // @PostMapping(path = "/drinks")
    // public String validationPage(User user)
    //  {
    //      ErrorMessages msgs = new ErrorMessages();
    //      User find_user = user_repository.findByEmail(user.getEmail());
    //      if(find_user == null)
    //      {
    //         msgs.add("Email Address Not Found");
    //          msgs.print();
    //          return "login_page";
    //      }
    //     String savedPassword = user.getPassword();
    //      if(!savedPassword.equals(user.getPassword())) {
    //         msgs.add("Incorrect Password");
    //          msgs.print();
    //         return "login_page";
    //      }
    //      return "drinks";
    //  }
    
    // when the register button is clicked on the homepage, the register page will show up
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    // post mapping to register user in the database
    // uses BCrypt encoding from Spring Security lab
    @PostMapping("/registration_success")
    public String registerUser(User user) throws Exception{
        User find_user = user_repository.findByEmail(user.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_password = passwordEncoder.encode(user.getPassword());
        if(find_user == null) {
            user.setEmail(user.getEmail());
            user.setPassword(encoded_password);
            user.setFirst_name(user.getFirst_name());
            user.setLast_name(user.getLast_name());
            user.setNew_password("N/A");
            user_repository.save(user);
        }
        return "registration_success";
    }

    @RequestMapping(path = "/drinks", method = {RequestMethod.GET, RequestMethod.POST})
    public String drinksPage(Model model){

        return "drinks";
    }

    @RequestMapping(path = "/start_page", method = {RequestMethod.GET, RequestMethod.POST})
    public String startPage(Model model){

        return "start_page";
    }
}
