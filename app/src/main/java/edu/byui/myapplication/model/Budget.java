package edu.byui.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
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
