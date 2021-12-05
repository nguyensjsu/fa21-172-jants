package com.example.springrewards;

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

import com.example.jants.User;
import com.example.jants.UserInfo;
import com.example.jants.UserRepository;

@Controller
@RequestMapping("/")
public class RewardController {
    @Autowired
    private RewardRepository reward_repository;

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
    
    @GetMapping("/rewards")
    public String rewardPage(Model model){
        model.addAttribute("reward", new Reward());

        return "rewards";
    }

    // post mapping to reset password
    @GetMapping("/enter_rewards")
    public String enterReward(Reward reward, @AuthenticationPrincipal UserInfo user)
    {
        Reward find_reward = reward_repository.findByEmail(user.getUsername());
        // if(find_reward == null)
        // {
        //     reward.setEmail(user.getUsername());
        //     reward.setStarsBalance(100);
        //     reward.setRedeemedPoints(0);
        //     reward.setRedeemedItem("N/A");
        //     reward_repository.save(reward);
        // }
        
        return "enter_rewards";
    }

    @PostMapping("/reward_redeemed")
    public String rewardRedeemed(Model model, Reward reward, @AuthenticationPrincipal UserInfo user)
    {
        Reward find_reward = reward_repository.findByEmail(user.getUsername());
        if(reward.getRedeemedPoints() == 25 && (find_reward.getStarsBalance() - reward.getRedeemedPoints() >= 0))
        {
            find_reward.setRedeemedItem("Free Cafe Latte");
            find_reward.setRedeemedPoints(25);
            model.addAttribute("message1", "Free Cafe Latte");
            model.addAttribute("message2", find_reward.getStarsBalance() - reward.getRedeemedPoints());
            find_reward.setStarsBalance(find_reward.getStarsBalance() - reward.getRedeemedPoints());
            reward_repository.save(find_reward);
            return "reward_redeemed";
        }
        if(reward.getRedeemedPoints() == 50 && (find_reward.getStarsBalance() - reward.getRedeemedPoints() >= 0))
        {
            find_reward.setRedeemedItem("Free Starbucks Tumbler");
            find_reward.setRedeemedPoints(50);
            model.addAttribute("message1", "Free Starbucks Tumbler");
            model.addAttribute("message2", find_reward.getStarsBalance() - reward.getRedeemedPoints());
            find_reward.setStarsBalance(find_reward.getStarsBalance() - reward.getRedeemedPoints());
            reward_repository.save(find_reward);
            return "reward_redeemed";
        }
        if(reward.getRedeemedPoints() == 75 && (find_reward.getStarsBalance() - reward.getRedeemedPoints() >= 0))
        {
            find_reward.setRedeemedItem("Free Starbucks Coffee Mug");
            find_reward.setRedeemedPoints(75);
            model.addAttribute("message1", "Free Starbucks Coffee Mug");
            model.addAttribute("message2", find_reward.getStarsBalance() - reward.getRedeemedPoints());
            find_reward.setStarsBalance(find_reward.getStarsBalance() - reward.getRedeemedPoints());
            reward_repository.save(find_reward);
            return "reward_redeemed";
        }
        if(reward.getRedeemedPoints() == 100 && (find_reward.getStarsBalance() - reward.getRedeemedPoints() >= 0))
        {
            find_reward.setRedeemedItem("Free Bag of Coffee");
            find_reward.setRedeemedPoints(100);
            model.addAttribute("message1", "Free Bag of Cofee");
            model.addAttribute("message2", find_reward.getStarsBalance() - reward.getRedeemedPoints());
            find_reward.setStarsBalance(find_reward.getStarsBalance() - reward.getRedeemedPoints());
            reward_repository.save(find_reward);
            return "reward_redeemed";
        }
        return "enter_rewards";
    }
}
