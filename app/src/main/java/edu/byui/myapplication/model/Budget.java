package edu.byui.myapplication.model;

public class Budget {
    int ID;
    String name;
    double amount;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
