package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.model.VendorDao;

public class VendorRepository {
    private VendorDao vendorDao;
    private LiveData<List<Vendor>> allVendors;

    public VendorRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        vendorDao = teamDatabase.getVendorDao();
        allVendors = vendorDao.getAllVendors();
    }

    public void insert(Vendor vendor) {
        new InsertVendorAsyncTask(vendorDao).execute(vendor);
    }

    public void update(Vendor vendor) {
        new UpdateVendorAsyncTask(vendorDao).execute(vendor);
    }

    public void delete(Vendor vendor) {
        new DeleteVendorAsyncTask(vendorDao).execute(vendor);
    }

    public void deleteAllVendors() {
        new DeleteAllVendorAsyncTask(vendorDao).execute();
    }

    public LiveData<List<Vendor>> getAllVendors() {
        return allVendors;
    }

    private static class InsertVendorAsyncTask extends AsyncTask<Vendor,Void,Void> {

        private VendorDao vendorDao;

        private InsertVendorAsyncTask(VendorDao vendorDao) {
            this.vendorDao = vendorDao;
        }

        @Override
        protected Void doInBackground(Vendor... vendors) {
            vendorDao.insert(vendors[0]);
            return null;
        }
    }

    private static class UpdateVendorAsyncTask extends AsyncTask<Vendor,Void,Void> {

        private VendorDao vendorDao;

        private UpdateVendorAsyncTask(VendorDao vendorDao) {
            this.vendorDao = vendorDao;
        }

        @Override
        protected Void doInBackground(Vendor... vendors) {
            vendorDao.update(vendors[0]);
            return null;
        }
    }

    private static class DeleteVendorAsyncTask extends AsyncTask<Vendor,Void,Void> {

        private VendorDao vendorDao;

        private DeleteVendorAsyncTask(VendorDao vendorDao) {
            this.vendorDao = vendorDao;
        }

        @Override
        protected Void doInBackground(Vendor... vendors) {
            vendorDao.delete(vendors[0]);
            return null;
        }
    }

    private static class DeleteAllVendorAsyncTask extends AsyncTask<Void,Void,Void> {

        private VendorDao vendorDao;

        private DeleteAllVendorAsyncTask(VendorDao vendorDao) {
            this.vendorDao = vendorDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            vendorDao.deleteAll();
            return null;
        }
    }


}
