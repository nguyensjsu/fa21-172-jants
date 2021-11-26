package com.example.springpayments;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Payments")
@Data
@RequiredArgsConstructor
class PaymentsCommand {

    private @Id @GeneratedValue Long id;

    transient private String action ;
    private String firstname ;
    private String lastname ;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phonenumber;
    private String cardnumber;
    private String expmonth;
    private String expyear;
    private String cvv;
    private String email;
    private String notes;

    private String orderNumber;
    private String transactionAmount;
    private String transactionCurrency;
    private String authId;
    private String authStatus;
    private String captureId;
    private String captureStatus;

    public String firstname() {return firstname;}
    public String lastname() {return lastname;}
    public String address() {return address;}
    public String city() {return city;}
    public String state() {return state;}
    public String zip() {return zip;}
    public String phonenumber() {return phonenumber;}
    public String cardnumber() {return cardnumber;}
    public String expmonth() {return expmonth;}
    public String expyear() {return expyear;}
    public String cvv() {return cvv;}
    public String email() {return email;}
    public String notes() {return notes;}
    
}
