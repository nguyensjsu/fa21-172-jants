package com.example.restAPI;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class StarbucksOrderController {
    private final StarbucksOrderRepository repository;

    private HashMap<String, StarbucksOrder> orders = new HashMap<>();

    @Autowired
    private StarbucksCardRepository card_repository;

    StarbucksOrderController(StarbucksOrderRepository repository){
        this.repository = repository;
    }

    class Message {
		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String msg) {
			status = msg;
		}
	}

    // Created the get request of orders
    // utilized spring-rest tutorial + video recording 
    @GetMapping("/orders")
    List<StarbucksOrder> all(){
        return repository.findAll();
    }


    // Created the delete request of all orders
    // utilized spring-rest tutorial + video recording
    @DeleteMapping("/orders")
    public Message deleteAll(){
        repository.deleteAllInBatch();
        orders.clear();
        Message message = new Message();
		message.setStatus("All Orders Deleted");
		return message;
    }


    // Created the post request for a new order
    // utilized spring-rest tutorial + video recording
    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    StarbucksOrder newOrder(@PathVariable String regid, @RequestBody StarbucksOrder order){
        if(order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order request");
        }

        StarbucksOrder active = orders.get(regid);
        if(active != null){
            if(active.getStatus().equals("Ready for Payment.")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Exists");
            }
        }

        double price = 0.0;

        switch(order.getDrink()){
        case "Latte":
            switch(order.getSize()){
                case "Tall":
                    price = 2.95;
                    break;
                case "Grande":
                    price = 3.65;
                    break;
                case "Venti":
                case "Your Own Cup":
                    price = 3.95;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            switch(order.getMilk()){
                case "Whole":
                    break;
                case "Fat Free":
                    break;
                    default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid milk choice!");
            }
            break;

        case "Mocha":
            switch(order.getSize()){
                case "Tall":
                    price = 3.45;
                    break;
                case "Grande":
                    price = 4.15;
                    break;
                case "Venti":
                case "Your Own Cup":
                    price = 4.45;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            switch(order.getMilk()){
                case "Whole":
                    break;
                case "Fat Free":
                    break;
                    default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid milk choice!");
            }
            break;

        case "Cappuccino":
            switch(order.getSize()){
                case "Tall":
                    price = 2.95;
                    break;
                case "Grande":
                    price = 3.65;
                    break;
                case "Venti":
                case "Your Own Cup":
                    price = 3.95;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            switch(order.getMilk()){
                case "Whole":
                    break;
                case "Fat Free":
                    break;
                    default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid milk choice!");
            }
            break;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink!");
        }

        double tax = 0.0725;
        double total = price + (price * tax);
        double scale = Math.pow(10,2);
        double rounded = Math.round(total * scale)/scale;
        order.setTotal(rounded);
        order.setStatus("Ready for Payment.");
        StarbucksOrder new_order = repository.save(order);
        orders.put(regid, new_order);
        return new_order;
    }

    // Created the get request of details of a Starbucks card
    // utilized spring-rest as well as video recording
    @GetMapping("/order/register/{regid}")
    StarbucksOrder getActiveOrder(@PathVariable String regid, HttpServletResponse response){
        StarbucksOrder active = orders.get(regid);
        if(active == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
        return active;
    }

    // Created the delete active order
    // utilized spring-rest as well as video recording
    @DeleteMapping("/order/register/{regid}")
    void deleteActiveOrder(@PathVariable String regid){
        StarbucksOrder active = orders.get(regid);
        if(active != null){
            active.setStatus("Order cleared");
            repository.save(active);
            orders.remove(regid);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
    }

    // Created the post request for the payment of the active order
    // utilzied the spring-rest as well as video recording
    @PostMapping("/order/register/{regid}/pay/{cardnum}")
    StarbucksCard processOrder(@PathVariable String regid, @PathVariable String cardnum){
        StarbucksOrder active = orders.get(regid);
        if(active==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }

        if (cardnum.equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number Not Provided!");
        }

        if(active.getStatus().startsWith("Paid with Card")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clear Paid Active Order!");
        }

        StarbucksCard card = card_repository.findByCardNumber(cardnum);
        if(card == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Found!");
        }

        if(!card.isActivated()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Is Not Activated!");
        }

        double price = active.getTotal();
        double balance = card.getBalance();
        if(balance - price < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Funds On Card.");
        }
        double new_balance = balance - price;
        card.setBalance(new_balance);
        String status="Paid with Card: " + cardnum + " Balance: $" + new_balance + ".";
        active.setStatus(status);
        card_repository.save(card);
        repository.save(active);
        return card;
    }
}
