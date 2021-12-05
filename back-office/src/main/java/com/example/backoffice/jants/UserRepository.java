package com.example.backoffice.jants;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface UserRepository extends JpaRepository<User, String> 
{
    User findByEmail(String email);   
    User findByPassword(String password);

}
