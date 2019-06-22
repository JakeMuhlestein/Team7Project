package edu.byui.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Vehicle extends Budget {
// I don't know if we can extend budget properly with Room. I will need to research this.
//    @PrimaryKey(autoGenerate = true)
//    private int ID;
//    // could make this an enum?
    private String make;
    // also an enum?
    private String model;
    // the whole date? why not just an int?
    private Date year;
    private int miles;

    public Vehicle(String make, String model, Date year, int miles) {
        super();
        this.make = make;
        this.model = model;
        this.year = year;
        this.miles = miles;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

}
