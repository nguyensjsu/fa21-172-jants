package com.example.backoffice.admin_office;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.backoffice.jants.User;
import com.example.backoffice.jants.UserRepository;
import com.example.backoffice.springpayments.PaymentsCommand;
import com.example.backoffice.springpayments.PaymentsCommandRepository;
import com.example.backoffice.springrewards.Reward;
import com.example.backoffice.springrewards.RewardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private PaymentsCommandRepository payments_repository;

    @Autowired 
    private UserRepository user_repository;

    @Autowired 
    private RewardRepository reward_repository;

    @GetMapping("/admin")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "admin";
    }

    @GetMapping("/admin/orders")
    public String getListOfPayments(@Valid @ModelAttribute("command") PaymentsCommand command, Model model, HttpServletRequest request){
        List<PaymentsCommand> list_of_payments = payments_repository.findAll();
        model.addAttribute("list_of_payments", list_of_payments);
        return "admin_orders";
    }

    @GetMapping("/admin/users")
    public String getListOfUsers(Model model) {
        List<User> list_of_users = user_repository.findAll();
        model.addAttribute("list_of_users", list_of_users);
        return "admin_users";
    }

    @GetMapping("/admin/rewards")
    public String getListOfRewards(@Valid @ModelAttribute("command") Reward reward, Model model, HttpServletRequest request){
        List<Reward> list_of_rewards = reward_repository.findAll();
        model.addAttribute("list_of_rewards", list_of_rewards);
        return "admin_rewards";
    }
}
