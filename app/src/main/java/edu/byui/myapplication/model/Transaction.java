package edu.byui.myapplication.model;

import java.util.Date;

public class Transaction {
    private int id;
    private User user;
    private Date date;
    private Vendor vendor;
    private PayMethod payMethod;
    private Budget budget;
    private double amount;
    private String notes;

    public Transaction(int id,
                       User user,
                       Date date,
                       Vendor vendor,
                       PayMethod payMethod,
                       Budget budget,
                       double amount,
                       String notes) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.vendor = vendor;
        this.payMethod = payMethod;
        this.budget = budget;
        this.amount = amount;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", vendor=" + vendor +
                ", payMethod=" + payMethod +
                ", budget=" + budget +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
