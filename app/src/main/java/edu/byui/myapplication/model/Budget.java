package edu.byui.myapplication.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget")
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;

    public Budget(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Ignore
    public Budget() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

}
