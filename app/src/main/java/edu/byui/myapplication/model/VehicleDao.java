package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface VehicleDao {
    // Returns the ID of the vehicle created
    @Insert
    void insertVehicle(Vehicle vehicle);

    // Updates an existing vehicle
    @Update
    void updateVehicle(Vehicle vehicle);

    /**
     * Deletes an existing vehicle
     * @param vehicle - the vehicle to delete. This can be done with pattern matching
     *                so if there is just an ID in the vehicle, it will still delete it.
     *                They delete objects do not have to be complete.
     */
    @Delete
    void deleteVehicle(Vehicle vehicle);

    /**
     * Returns all vehicles. This should later be updated to only desired vehicles.
     * (Like a report?)
     * @return LiveData of all vehicles
     */
    @Query("SELECT * FROM vehicle")
    LiveData<List<Vehicle>> getAllVehicles();

    // Commenting this one out for now. Will need to research implementation.
    //Report getVehicleReportSummary(Vehicle vehicle);
}
