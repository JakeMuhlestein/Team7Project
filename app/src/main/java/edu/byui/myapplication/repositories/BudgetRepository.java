package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.BudgetDao;
import edu.byui.myapplication.model.TeamDatabase;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<List<Budget>> allBudgetItems;

    public BudgetRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        budgetDao = teamDatabase.getBudgetDao();
        allBudgetItems = budgetDao.getAllCategories();
    }

    public void insert(Budget budget) {
        new InsertBudgetAsyncTask(budgetDao).execute(budget);
    }

    public void update(Budget budget) {
        new UpdateBudgetAsyncTask(budgetDao).execute(budget);
    }

    public void delete(Budget budget) {
        new DeleteBudgetAsyncTask(budgetDao).execute(budget);
    }

    public LiveData<List<Budget>> getAllBudgetItems() {
        return allBudgetItems;
    }

    private static class InsertBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private InsertBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.insertCategory(budgets[0]);
            return null;
        }
    }

    private static class UpdateBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private UpdateBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.updateCategory(budgets[0]);
            return null;
        }
    }

    private static class DeleteBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        private DeleteBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.deleteCategory(budgets[0]);
            return null;
        }
    }

    private static class DeleteAllBudgetsAsyncTask extends AsyncTask<Void, Void, Void> {
        private BudgetDao budgetDao;

        private DeleteAllBudgetsAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            budgetDao.deleteAllBudgetItems();
            return null;
        }
    }

}