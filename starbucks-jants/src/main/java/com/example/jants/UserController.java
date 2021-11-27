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
 
    // when the login button is clicked on the homepage, the login page will show up
    @GetMapping(path = "/login")
    public String loginPage(User user)
    {
        return "login";
    }

    // when login button is clicked on Login page, it will go here for validation
    @GetMapping(path = "/validation")
    public String validationPage(User user)
    {
        ErrorMessages msgs = new ErrorMessages();
        User find_user = user_repository.findByEmail(user.getEmail());
        if(find_user == null)
        {
            msgs.add("Email Address Not Found");
            msgs.print();
            return "login";
        }
        String savedPassword = user.getPassword();
        if(savedPassword != user.getPassword()) {
            msgs.add("Incorrect Password");
            msgs.print();
            return "login";
        }
        return "drinks";
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
