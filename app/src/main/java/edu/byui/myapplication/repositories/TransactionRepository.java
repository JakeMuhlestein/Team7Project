package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.ReportView;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.model.TransactionDao;

public class TransactionRepository {
    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private LiveData<List<ReportView>> allReports;

    public TransactionRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);

        transactionDao = teamDatabase.getTransactionDao();
        allTransactions = transactionDao.getAllTransactions();
        allReports = transactionDao.getReports();
    }

    public void insert(Transaction transaction) {
        new InsertAsyncTask(transactionDao).execute(transaction);
    }

    public void update(Transaction transaction) {
        new UpdateAsyncTask(transactionDao).execute(transaction);
    }

    public void delete(Transaction transaction) {
        new DeleteAsyncTask(transactionDao).execute(transaction);    }

    public void deleteAll() {
        new DeleteAllAsyncTask(transactionDao).execute();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    // For Reports
    public LiveData<List<ReportView>> getAllReports() {
        return allReports;
    }

    public void getTotalInBudget(int budgetId) {
        new GetTotalInBudgetAsyncTask(transactionDao).execute(budgetId);
    }

    private class InsertAsyncTask extends AsyncTask<Transaction,Void,Void> {
        private TransactionDao transactionDao;

        private InsertAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.createTransaction(transactions[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Transaction,Void,Void> {
        private TransactionDao transactionDao;

        private UpdateAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.editTransaction(transactions[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Transaction,Void,Void> {
        private TransactionDao transactionDao;

        private DeleteAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.deleteTransaction(transactions[0]);
            return null;
        }
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private TransactionDao transactionDao;

        private DeleteAllAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDao.deleteAll();
            return null;
        }
    }

    private class GetTotalInBudgetAsyncTask extends AsyncTask<Integer,Void,Void> {
        private TransactionDao transactionDao;

        private GetTotalInBudgetAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Integer... budgetIds) {
            transactionDao.getTotalInBudget(budgetIds[0]);
            return null;
        }
    }


}
