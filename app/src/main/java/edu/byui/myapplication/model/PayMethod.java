package edu.byui.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class PayMethod {
    @PrimaryKey(autoGenerate = true)
    int ID;
    String payType;
    String acctNumber;
    double balance;
    Date expDate;
    double points;

    public int getID() {
        return ID;
    }

    public String getPayType() {
        return payType;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Date getExpDate() {
        return expDate;
    }

    public double getPoints() {
        return points;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addPoints(double points) {
        balance += points;
    }
}
