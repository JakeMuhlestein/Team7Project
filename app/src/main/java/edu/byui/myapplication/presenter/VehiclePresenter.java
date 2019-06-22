package edu.byui.myapplication.presenter;

import java.sql.Date;

import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.model.VehicleDao;

/**
 * This class may just be an abstraction layer between the fragment and the VehicleDAO.
 */
public class VehiclePresenter {
    public Vehicle createVehicle(String make, String model, Date year, int miles) {
        VehicleDao vehicleDao;
        //vehicleDao.createVehicle(make, model, year, miles);
        return new Vehicle(make, model, year, miles);
    }
}
