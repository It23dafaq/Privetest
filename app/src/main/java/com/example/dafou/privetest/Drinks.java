package com.example.dafou.privetest;

public class Drinks {
    private String drink;
    private Double price;

    Drinks(){

    }
    Drinks(String drink,Double price){
            this.drink=drink;
            this.price=price;

    }
    public String getDrink() {
        return drink;
    }
    public Double getPrice(){
        return price;
    }
    public void setDrink(String drink) {
        this.drink = drink;
    }
    public void setPrice(Double price) {
        this.price = price;
    }



}
