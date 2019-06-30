package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity (foreignKeys = {
            @ForeignKey(
                    entity = User.class,
                    parentColumns = "id",
                    childColumns = "user_id",
                    onDelete = ForeignKey.NO_ACTION
            ),
            @ForeignKey(
                    entity = Vendor.class,
                    parentColumns = "id",
                    childColumns = "vendor_id",
                    onDelete = ForeignKey.NO_ACTION
            ),
            @ForeignKey(
                    entity = PayMethod.class,
                    parentColumns = "id",
                    childColumns = "paymethod_id",
                    onDelete = ForeignKey.NO_ACTION
            ),
            @ForeignKey(
                    entity = Budget.class,
                    parentColumns = "id",
                    childColumns = "budget_id",
                    onDelete = ForeignKey.NO_ACTION
            )
        },
        indices = {
            @Index("user_id"), @Index("vendor_id"), @Index("paymethod_id"), @Index("budget_id")
        })
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    private Date date;
    @ColumnInfo(name = "vendor_id")
    private int vendorId;
    @ColumnInfo(name = "paymethod_id")
    private int payMethodId;
    @ColumnInfo(name = "budget_id")
    private int budgetId;
    private double amount;
    private String notes;


    public Transaction(int userId, Date date, int vendorId, int payMethodId, int budgetId, double amount, String notes) {
        this.userId = userId;
        this.date = date;
        this.vendorId = vendorId;
        this.payMethodId = payMethodId;
        this.budgetId = budgetId;
        this.amount = amount;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(int payMethodId) {
        this.payMethodId = payMethodId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", userId=" + userId +
                ", date=" + date +
                ", vendorId=" + vendorId +
                ", payMethodId=" + payMethodId +
                ", budgetId=" + budgetId +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
