package edu.byui.myapplication.viewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.repositories.VendorRepository;

public class VendorViewModel extends AndroidViewModel {

    private VendorRepository vendorRepository;
    private LiveData<List<Vendor>> allVendors;

    public VendorViewModel(@NonNull Application application) {
        super(application);
        vendorRepository = new VendorRepository(application);
        allVendors = vendorRepository.getAllVendors();
    }

    public void insert(Vendor vendor) {
        vendorRepository.insert(vendor);
    }

    public void update(Vendor vendor) {
        vendorRepository.update(vendor);
    }

    public void delete(Vendor vendor) {
        vendorRepository.delete(vendor);
    }

    public void deleteAll() {
        vendorRepository.deleteAllVendors();
    }

    public LiveData<List<Vendor>> getAllVendors() {
        return allVendors;
    }
}
