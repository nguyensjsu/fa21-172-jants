package com.example.springpayments;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.example.drinkslist.DrinkOrder;
import com.example.drinkslist.DrinksList;
import com.example.jants.User;
import com.example.jants.UserInfo;
import com.example.jants.UserRepository;
import com.example.springcybersource.*;
import com.example.springrewards.Reward;
import com.example.springrewards.RewardRepository;

// referenced code from spring-cybersource as well as follows lab structure of recording on zoom

@Slf4j
@Controller
@RequestMapping("/")
public class PaymentsController {  

    private static boolean DEBUG = true ;

    @Value("${cybersource.apihost}")            private String apiHost;
    @Value("${cybersource.merchantkeyid}")      private String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}")  private String merchantsecretKey;
    @Value("${cybersource.merchantid}")         private String merchantId;

    private CyberSourceAPI api = new CyberSourceAPI();

    private static Map<String, String> months = new HashMap<>();
    static {
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");
    }

    private static Map<String, String> states = new HashMap<>();
    static {
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("RI", "Rhode Island");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
    }

    @Autowired
    private PaymentsCommandRepository payments_repository;

    @Autowired
    private RewardRepository reward_repository;


    @GetMapping("/creditcards/{drink_order_name}")
    public String getAction(@Valid @ModelAttribute("command") PaymentsCommand command, 
                            Model model, @PathVariable("drink_order_name") String drink_order_name) {
        DrinksList drink = new DrinkOrder(drink_order_name).set();
        model.addAttribute("price", drink.getPrice());

        return "creditcards" ;

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

    @GetMapping("/orders")
    public String getListOfPayments(@Valid @ModelAttribute("command") PaymentsCommand command, 
                                                    Model model, HttpServletRequest request, @AuthenticationPrincipal UserInfo user){
        List<PaymentsCommand> list_of_payments = payments_repository.findByEmail(user.getUsername());
        // System.out.println(user.getUsername());
        model.addAttribute("list_of_payments", list_of_payments);
        return "orders";
    }

    @PostMapping("/creditcards/{drink_order_name}")
    public String postAction(@Valid @ModelAttribute("command") PaymentsCommand command, @AuthenticationPrincipal UserInfo user, 
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request, @PathVariable("drink_order_name") String drink_order_name){
        DrinksList drink = new DrinkOrder(drink_order_name).set();
        model.addAttribute("price", drink.getPrice());

        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        CyberSourceAPI.setHost(apiHost);
        CyberSourceAPI.setKey(merchantKeyId);
        CyberSourceAPI.setSecret(merchantsecretKey);
        CyberSourceAPI.setMerchant(merchantId);

        CyberSourceAPI.debugConfig();

        ErrorMessages msgs = new ErrorMessages();

        boolean hasErrors = false;
        if(command.firstname().equals(""))      {hasErrors = true; msgs.add("First Name Required");}
        if(command.lastname().equals(""))       {hasErrors = true; msgs.add("Last Name Required");}
        if(command.address().equals(""))        {hasErrors = true; msgs.add("Address Required");}
        if(command.city().equals(""))           {hasErrors = true; msgs.add("City Required");}
        if(command.state().equals(""))          {hasErrors = true; msgs.add("State Required");}
        if(command.zip().equals(""))            {hasErrors = true; msgs.add("Zip Required");}
        if(command.phonenumber().equals(""))    {hasErrors = true; msgs.add("Phone Number Required");}
        if(command.cardnumber().equals(""))     {hasErrors = true; msgs.add("Credit Card Number Required");}
        if(command.expmonth().equals(""))       {hasErrors = true; msgs.add("Credit Card Expiration Month Required");}
        if(command.expyear().equals(""))        {hasErrors = true; msgs.add("Credit Card Expiration Year Required");}
        if(command.cvv().equals(""))            {hasErrors = true; msgs.add("Credit Card CVV Required");}
        if(command.email().equals(""))          {hasErrors = true; msgs.add("Email Address Required");}                        
        
        // the pattern matcher is taken from the link here: https://www.baeldung.com/java-regex-validate-phone-numbers
        if(!command.zip().matches("\\d{5}"))                                    {hasErrors = true; msgs.add("Invalid Zip Code: "+ command.zip());} 
        if(!command.phonenumber().matches("[(]\\d{3}[)] \\d{3}-\\d{4}"))        {hasErrors = true; msgs.add("Invalid Phone Number: "+ command.phonenumber());}
        if(!command.cardnumber().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))        {hasErrors = true; msgs.add("Invalid Card Number: "+ command.cardnumber());}
        if(!command.expyear().matches("\\d{4}"))                                {hasErrors = true; msgs.add("Invalid Card Expiration Year: "+ command.expyear());}
        if(!command.cvv().matches("\\d{3}"))                                    {hasErrors = true; msgs.add("Invalid Card CVV: "+ command.cvv());}
        
        // validate months
        if(months.get(command.expmonth()) == null)      {hasErrors = true; msgs.add("Invalid Card Expiration Month: " + command.expmonth());}
        // validate states
        if(states.get(command.state()) == null)         {hasErrors = true; msgs.add("Invalid State: " + command.state());}

        if(hasErrors){
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "creditcards";
        }   

        // processing payments
        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest();
        auth.reference = order_num;
        auth.billToFirstName = command.firstname();
        auth.billToLastName = command.lastname() ;
        auth.billToAddress = command.address() ;
        auth.billToCity = command.city() ;
        auth.billToState = command.state() ;
        auth.billToZipCode = command.zip() ;
        auth.billToPhone = command.phonenumber() ;
        auth.billToEmail = command.email() ;
        String price = drink.getPrice();
        String new_price = price.replaceAll("[$,]", "");
        auth.transactionAmount = new_price ;
        auth.transactionCurrency = "USD" ;
        auth.cardNumnber = command.cardnumber() ;
        auth.cardExpMonth = months.get(command.expmonth()) ;
        auth.cardExpYear = command.expyear() ;
        auth.cardCVV = command.cvv() ;
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber) ;
        if(auth.cardType.equals("ERROR")){
            System.out.println("Unsupported credit card type");
            model.addAttribute("message", "Unsupported credit card type");
            return "creditcards";
        }

        boolean authValid = false;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Response: " + authResponse.toJson());
        if(authResponse.status.equals("AUTHORIZED")) {
            authValid = true;
        }
        else if(!authResponse.status.equals("AUTHORIZED")) {
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "creditcards";
        }

        boolean captureValid = false ;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if(authValid) {
            capture.reference = order_num ;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = new_price ;
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            if(captureResponse.status.equals("PENDING")){
                captureValid = true;
            }
            else if(!captureResponse.status.equals("PENDING")) {
                System.out.println( captureResponse.message );
                model.addAttribute("message", captureResponse.message);
            return "creditcards";
            }
        }
        
        if(authValid && captureValid) {
            command.setOrderNumber(order_num);
            command.setTransactionAmount(new_price);
            command.setDrink(drink_order_name);
            command.setTransactionCurrency("USD");
            command.setAuthId(authResponse.id);
            command.setAuthStatus(authResponse.status);
            command.setCaptureId(captureResponse.id);
            command.setCaptureStatus(captureResponse.status);
            Reward find_reward = reward_repository.findByEmail(user.getUsername());
            find_reward.setStarsBalance(find_reward.getStarsBalance() + 20);
            reward_repository.save(find_reward);
            payments_repository.save(command);
            System.out.println("Thank You for Your Payment! Your Order Number is: " + order_num);
            model.addAttribute("message", "Thank You for Your Payment! Your Order Number is: " + order_num) ;
        }

        return "creditcards";
    }

}