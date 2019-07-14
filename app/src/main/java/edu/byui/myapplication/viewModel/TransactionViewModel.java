package edu.byui.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.repositories.TransactionRepository;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);

        transactionRepository = new TransactionRepository(application);
        allTransactions = transactionRepository.getAllTransactions();
    }

    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);
    }

    public void update(Transaction transaction) {
        transactionRepository.update(transaction);
    }

    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
}
