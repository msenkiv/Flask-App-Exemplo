package com.example.flaskappexemplo.model;

public class Console {
    private long id;
    private String name;
    private int year;
    private double price;
    private Boolean active;
    private int quantity;

    public Console() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getActive() {return active;}

    public void setActive(Boolean active) {this.active = active; }

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}
    @Override
    public String toString() {
        return name;
    }
}
