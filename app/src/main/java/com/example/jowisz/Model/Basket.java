package com.example.jowisz.Model;

import java.util.ArrayList;

public class Basket {
    private ArrayList<Product> products;
    private double sumTotal = 0;

    public Basket(){
        products = new ArrayList<>();
    }

//    public void fillTestProducts(){
//        products.add(new Product(1, "Acer Aspire", "laptopy", "Acer", 1399.0, 10, 1));
//        products.add(new Product(2, "Lenovo Super", "myszki", "Lenovo", 25.0, 10, 2));
//        products.add(new Product(3, "bbsdfkhj", "klatiwatury", "HP", 39.0, 10, 3));
//    }

    public int size(){
        return products.size();
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
}
