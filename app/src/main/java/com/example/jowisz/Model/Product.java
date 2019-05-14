package com.example.jowisz.Model;

public class Product {

    private int id;
    private String name;
    private String category;
    private String producer;
    private double priceUnit;
    private int avaibility;
    private int howMany = 1;

    public Product(int id, String name, String category, String producer, double priceUnit, int avaibility, int howMany) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.priceUnit = priceUnit;
        this.avaibility = avaibility;
        this.howMany = howMany;
    }

    public String getName(){
        return name+" - "+producer;
    }

    public String getPriceXHowMany(){
        return Double.toString(priceUnit)+" x "+howMany;
    }

    public String getTotalPrice(){
        return String.valueOf(priceUnit*howMany);
    }
}
