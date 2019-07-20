package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "vehicle")
public class Vehicle extends Budget {
// I don't know if we can extend budget properly with Room. I will need to research this.
//    @PrimaryKey(autoGenerate = true)
//    private int ID;
//    // could make this an enum?
    private String make;
    // also an enum?
    private String model;
    // the whole date? why not just an int?
    private int year;
    private int miles;

    // Couldn't this just be budget.name?
//    @ColumnInfo(name = "nickname")
//    private String nickname;

    public Vehicle(String make, String model, int year, int miles) {
        super();
        this.make = make;
        this.model = model;
        this.year = year;
        this.miles = miles;
        // if name hasn't been set in budget, then default to:
        this.setName(make + ": " + model + " " + year);
    }

    @Ignore
    public Vehicle(String make, String model, int year, int miles, String name, double amount) {
        super();
        this.setName(name);
        this.setAmount(amount);
        this.make = make;
        this.model = model;
        this.year = year;
        this.miles = miles;
    }


//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    @Override
    public String toString() {
        return super.toString() + "Vehicle{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", miles=" + miles +
                '}';
    }
}
