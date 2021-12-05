package com.example.backoffice.springrewards;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface RewardRepository extends JpaRepository<Reward, String> 
{
    Reward findByEmail(String email);
}
