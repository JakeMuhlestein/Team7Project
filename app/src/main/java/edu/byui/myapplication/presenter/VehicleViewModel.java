package edu.byui.myapplication.presenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.repositories.VehicleRepository;

public class VehicleViewModel extends AndroidViewModel {

    private VehicleRepository repository;
    private LiveData<List<Vehicle>> allVehicles;

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        repository = new VehicleRepository(application);
        allVehicles = repository.getAllVehicles();
    }

    // livedata manages itself.
    public LiveData<List<Vehicle>> getAllVehicles() { return allVehicles; }

    // wrappers
    public void deleteVehicle(Vehicle vehicle) { repository.deleteVehicle(vehicle);}
    public void updateVehicle(Vehicle vehicle) { repository.updateVehicle(vehicle);}
    public void insertVehicle(Vehicle vehicle) { repository.insertVehicle(vehicle);}

}
