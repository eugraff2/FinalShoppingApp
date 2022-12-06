package edu.uga.cs.finalshoppingapp;

// basic POJO to represent a shopping item

public class Item {

    private String key, name, user;
    private double price;

    public Item() {
        this.key = null;
        this.name = null;
        this.price = 0.0;
    }

    public Item(String name, double price) {
        this.key = null;
        this.name = name;
        this.price = price;
    }

    public String getKey() {return this.key;}
    public String getName() {return this.name;}
    public double getPrice() {return this.price;}

    public void setKey(String keyIn) {this.key = keyIn;}
    public void setName(String nameIn) {this.name = nameIn;}
    public void setPrice(double priceIn) {this.price = priceIn;}

    public void setUser(String userIn) {this.user = userIn;}
    public String getUser() {return this.user;}

    public String toString() {return name + ": " + price;}

} // Item
