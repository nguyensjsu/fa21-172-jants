package com.example.restAPI;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class StarbucksCardController {
    private final StarbucksCardRepository repository;

    StarbucksCardController(StarbucksCardRepository repository){
        this.repository = repository;
    }

    // Creating the post request for a new starbucks card
    // utilized spring-rest guide as well as recording provided
    @PostMapping("/cards")
    StarbucksCard newCard() {
        
        StarbucksCard new_card = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 1000;

        new_card.setCardNumber(String.valueOf(num));
        new_card.setCardCode(String.valueOf(code));
        new_card.setBalance(50.00);
        new_card.setActivated(false);
        new_card.setStatus("New Card");
        return repository.save(new_card);
    }

    // Creating the get request for the list of starbucks cards
    // utilized spring-rest guide as well as recording provided
    @GetMapping("/cards")
    List<StarbucksCard> all() {
        return repository.findAll();
    }

    // Creating the delete request for all starbucks cards
    // utilized spring-rest guide as well as recording provided
    // unable to instantiate Message thus set to void method as the one provided in spring-rest
    @DeleteMapping("/cards")
    public void deleteAll(){
        repository.deleteAllInBatch();
    }

    // Creating the get request to find if starbucks card is valid or invalid
    // utilized spring-rest guide as well as recording provided
    @GetMapping("/card/{num}")
    StarbucksCard one(@PathVariable String num, HttpServletResponse response){
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!");
        }
        return card;
    }

    // Creating the post request to activate a starbucks card
    // utilized spring-rest guide as well as recording provided
    @PostMapping("card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletRequest request){
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!");
        }

        if(card.getCardCode().equals(code)){
            card.setActivated(true);
            repository.save(card);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!");
        }

        return card;
    }

    
}
