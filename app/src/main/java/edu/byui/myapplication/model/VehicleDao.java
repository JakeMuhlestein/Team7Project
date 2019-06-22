package edu.byui.myapplication.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.Date;

@Dao
public interface VehicleDao {
    // THIS MAY NEED to return void
    @Insert
    Vehicle createVehicle(String make, String model, Date year, int miles);

    @Query("SELECT * FROM Vehicle WHERE ID = :ID")
    Vehicle getVehicle(int ID);

    // Commenting this one out for now. Will need to research implementation.
    //Report getVehicleReportSummary(Vehicle vehicle);
}
