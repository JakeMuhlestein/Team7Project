package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView("SELECT budget_id as budgetId, name, budget.amount, SUM(`Transaction`.amount) as budgetSpent FROM `Transaction` " +
        "INNER JOIN budget ON budget.id = `Transaction`.budget_id GROUP BY budget_id")
public class ReportView {
    private int budgetId;
    private String name;
    private double amount;
    private double budgetSpent;

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int id) {
        this.budgetId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBudgetSpent() {
        return budgetSpent;
    }

    public void setBudgetSpent(double budgetSpent) {
        this.budgetSpent = budgetSpent;
    }

    @Override
    public String toString() {
        return "ReportView{" +
                "budgetId=" + budgetId +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", budgetSpent=" + budgetSpent +
                '}';
    }
}
