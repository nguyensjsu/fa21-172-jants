package com.example.restAPI;

import org.springframework.data.jpa.repository.JpaRepository;

interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {

    StarbucksCard findByCardNumber(String cardNumber);

}
