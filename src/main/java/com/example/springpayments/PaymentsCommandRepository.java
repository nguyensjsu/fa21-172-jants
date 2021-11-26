package com.example.springpayments;

// code referenced from lab 5 
import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand, Long> {

}