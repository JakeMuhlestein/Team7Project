package edu.byui.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.repositories.BudgetRepository;

public class BudgetViewModel extends AndroidViewModel {
    private BudgetRepository repository;
    private LiveData<List<Budget>> allBudgetItems;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetRepository(application);
        allBudgetItems = repository.getAllBudgetItems();
    }

    public void insert(Budget budget) {
        repository.insert(budget);
    }

    public void update(Budget budget) {
        repository.update(budget);
    }

    public void delete(Budget budget) {
        repository.delete(budget);
    }

    public LiveData<List<Budget>> getAllBudgetItems() {
        return allBudgetItems;
    }
}
