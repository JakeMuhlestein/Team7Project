package edu.byui.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.repositories.PayMethodRepository;

public class PayMethodViewModel extends AndroidViewModel {
    private PayMethodRepository repository;
    private LiveData<List<PayMethod>> allPayMethodItems;

    public PayMethodViewModel(@NonNull Application application) {
        super(application);
        repository = new PayMethodRepository(application);
        allPayMethodItems = repository.getAllPayMethodItems();
    }

    public void insert(PayMethod payMethod) {
        repository.insert(payMethod);
    }

    public void update(PayMethod payMethod) {
        repository.update(payMethod);
    }

    public void delete(PayMethod payMethod) {
        repository.delete(payMethod);
    }

    public LiveData<List<PayMethod>> getAllPayMethodItems() {
        return allPayMethodItems;
    }
}
