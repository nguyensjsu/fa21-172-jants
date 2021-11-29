package com.example.springpayments;

import java.util.List;

// code referenced from lab 5 
import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand, Long> {
    List<PaymentsCommand> findByEmail(String email);
}