package edu.byui.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.ReportView;
import edu.byui.myapplication.repositories.TransactionRepository;
import edu.byui.myapplication.repositories.VehicleRepository;

public class ReportViewModel extends AndroidViewModel {
    private TransactionRepository repository;
    private LiveData<List<ReportView>> allReports;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allReports = repository.getAllReports();
    }

    // livedata manages itself.
    public LiveData<List<ReportView>> getAllReports() { return allReports; }

    // wrappers
    public void getTotalInBudget(Budget budget) {
        if(budget != null && budget.getId() > 0)
            repository.getTotalInBudget(budget.getId());
    }

}
