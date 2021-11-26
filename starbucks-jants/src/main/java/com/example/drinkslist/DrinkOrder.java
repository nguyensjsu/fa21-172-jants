package com.example.drinkslist;

public class DrinkOrder {
    public String drink_order_name;

    public DrinkOrder(String drink_order_name){
        this.drink_order_name = drink_order_name;
    }

    public DrinksList set(){
        if(this.drink_order_name.equals("Applecrisp")){
            return new Applecrisp();
        }
        else if(this.drink_order_name.equals("Caramelbruleelatte")){
            return new Caramelbruleelatte();
        }
        else if(this.drink_order_name.equals("Caramelfrap")){
            return new Caramelfrap();
        }
        else if(this.drink_order_name.equals("Chestnutpralinelatte")){
            return new Chestnutpralinelatte();
        }
        else if(this.drink_order_name.equals("MochaFrap")){
            return new MochaFrap();
        }
        else if(this.drink_order_name.equals("Nitro")){
            return new Nitro();
        }
        else if(this.drink_order_name.equals("Peppermintmocha")){
            return new Peppermintmocha();
        }
        else if(this.drink_order_name.equals("Pumpkincreamlatte")){
            return new Pumpkincreamlatte();
        }
        else if(this.drink_order_name.equals("Toastedwcmocha")){
            return new Toastedwcmocha();
        }
        else if(this.drink_order_name.equals("Tripleshot")){
            return new Tripleshot();
        }
        else{
            return null;
        }
    }
}
