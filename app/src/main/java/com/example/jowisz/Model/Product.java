package com.example.jowisz.Model;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    public String name;
    private String description;
    private double priceUnit;
    private int availability;
    private String category;
    private String producer;
    private int howMany = 0;

    public Product(int id, String name, String description, double priceUnit, String zdjecie,
                   int availability, String producer, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.description = description;
        this.priceUnit = priceUnit;
        this.availability = availability;
    }

    public String getNameAndProducer(){
        return name+" - "+producer;
    }

    public String getPriceXHowMany(){
        return Double.toString(priceUnit)+" x "+howMany;
    }

    public String getTotalPrice(){
        return String.valueOf(priceUnit*howMany);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPriceUnit() {
        return priceUnit;
    }

    public int getAvaibility() {
        return availability;
    }

    public String getCategory() {
        return category;
    }

    public String getProducer() {
        return producer;
    }

    public int getHowMany() {
        return howMany;
    }
}
