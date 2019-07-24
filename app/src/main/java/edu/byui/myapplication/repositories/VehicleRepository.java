package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.model.VehicleDao;

public class VehicleRepository {

    private VehicleDao dao;
    private LiveData<List<Vehicle>> allVehicles;
    private static final String TAG = "VehicleRepository:";

    public VehicleRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        dao = teamDatabase.getVehicleDao();
        allVehicles = dao.getAllVehicles();
    }

    // for the livedata. Since this will keep a current list of all vehicles
    // do we every explicitly call it once it's been created (the LiveData)
    public LiveData<List<Vehicle>> getAllVehicles() { return allVehicles; }

    // for the AsyncClass dependent Methods
    public void deleteVehicle(Vehicle vehicle) { new DeleteVehicleAsyncTask(dao).execute(vehicle);}
    public void updateVehicle(Vehicle vehicle) { new UpdateVehicleAsyncTask(dao).execute(vehicle);}
    public void insertVehicle(Vehicle vehicle) { new InsertVehicleAsyncTask(dao).execute(vehicle);}


    private static class DeleteVehicleAsyncTask extends AsyncTask<Vehicle,Void,Void> {
        private VehicleDao dao;

        private DeleteVehicleAsyncTask(VehicleDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Vehicle... vehicles) {
            dao.deleteVehicle(vehicles[0]);
            // I guess this return statement is needed with the Void types?
            return null;
        }
    }

    private static class InsertVehicleAsyncTask extends AsyncTask<Vehicle,Void,Void> {
        private VehicleDao dao;

        private InsertVehicleAsyncTask(VehicleDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Vehicle... vehicles) {
            dao.insertVehicle(vehicles[0]);
            // I guess this return statement is needed with the Void types?
            return null;
        }
    }

    private static class UpdateVehicleAsyncTask extends AsyncTask<Vehicle,Void,Void> {
        private VehicleDao dao;

        private UpdateVehicleAsyncTask(VehicleDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Vehicle... vehicles) {
            Log.d(TAG, "UpdateVehicleAsyncTask: calling dao.updateVehicle");
            dao.updateVehicle(vehicles[0]);
            // I guess this return statement is needed with the Void types?
            return null;
        }
    }



}
