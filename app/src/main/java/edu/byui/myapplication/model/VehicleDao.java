package edu.byui.myapplication.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.Date;

@Dao
public interface VehicleDao {
    // Returns the int ID of the vehicle created
//    @Insert
//    int createVehicle(String make, String model, Date year, int miles);

    // Returns the ID of the vehicle created
    @Insert
    void createVehicle(Vehicle vehicle);

//    @Query("SELECT * FROM Vehicle WHERE ID = :id")
//    Vehicle getVehicle(int id);

    // Commenting this one out for now. Will need to research implementation.
    //Report getVehicleReportSummary(Vehicle vehicle);
}
