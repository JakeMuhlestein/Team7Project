package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "paymethod")
public class PayMethod {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "pay_type")
    String payType;
    @ColumnInfo(name = "acct_number")
    String acctNumber;
    double balance;
    @Ignore
    Date expDate;
    double points;

    public int getId() {
        return id;
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

    public PayMethod() {
        // hack workaround please change.
        //expDate = new Date(2006, 6, 14);
    }

//    // Needs fixing! mt.
//    @Ignore
//    PayMethod(Date expDate) {
//       this.expDate = expDate;
//    }
}
