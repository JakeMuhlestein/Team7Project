package edu.byui.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.BudgetDao;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.presenter.UserPresenter;

public class DataRepository {
    final String TAG = "DataRepository: ";
    private static DataRepository instance;
    private User user;
    private final TeamDatabase db;

    //inplementing a private constructor. Going for a singleton here.
    private DataRepository(final TeamDatabase db) {
        this.db = db;

        // think about loading the user here. Always available unless its the first time running the app.
    }


    public static DataRepository getInstance(final TeamDatabase db) {
        if(instance == null) {
            // can only be one
            synchronized (DataRepository.class) {
                // checking again to make sure something didn't sneak in before synch.
                if (instance == null) {
                    instance = new DataRepository(db);
                }
            }
        }
        return instance;
    }

    public static DataRepository getInstance() throws Exception {
        if(instance == null) {
            // can only be one
            synchronized (DataRepository.class) {
                // checking again to make sure something didn't sneak in before synch.
                if (instance == null) {
                    throw new Exception("DataRepositoryException: Database has not been initialized.");
                }
            }
        }
        return instance;
    }

    public void doNothing() {
        return;
    }

    // this is where all the presenters will come for their DAO. I figured this would be easier
    // than managing the DAOs individually. These are just a series of wrappers

    //USER
    /**
     * Creates an account. This can't be run on the main thread as is.
     * @param user
     * @return User newly persisted to the database with ID and all.
     */
    public User createAccount(User user) {
        //asynctask code
        AsyncTask task = new createAccountAsyncTask(db.getUserDao()).execute(user);
        Log.d(TAG, task.getStatus().toString());
        // currently returning the passed in user as we don't have an observer to notify
        // when this is done. >,<
        return user;

//        db.getUserDao().insert(user);
//        return db.getUserDao().loadUserByUsername(user.getUsername()).get(0);
    }

    //BUDGET
    /**
     * Creates a budget, returns the completed Budget with ID
     * @param budget
     * @return budget with ID
     */
    public Budget createBudget(Budget budget) {
        db.getBudgetDao().insertCategory(budget);
        return db.getBudgetDao().getCategoryByName(budget.getName());
    }


    /**
     * Updates a budget/category
     * @param category
     * @return true if 1 budget was updated.
     */
    public boolean updateCategory(Budget category) {
        return (db.getBudgetDao().updateCategory(category) == 1);
    }

    /**
     * Get All Categories
     * Uses LiveData to return a List of all categories/budgets
     */
    public LiveData<List<Budget>> getAllCategories() {
        return db.getBudgetDao().getAllCategories();
    }

    /**
     * Inner class to run tasks in the background.
     * still working on this. Haven't decided how to go.
     * Parameters are the types for: input for doInBackGround; progress; what we expect back
     */
    private static class createAccountAsyncTask extends AsyncTask<User, Void, User> {

        private UserDao mAsyncTaskDao;

        //Constructor needs the Dao passed in as it is static.
        createAccountAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return mAsyncTaskDao.loadUserByUsername(params[0].getUsername()).get(0);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            // need to return this user.
            // We normally would do this by notifying the presenter class.
            // This is a UserPresenter extends ViewModel. Look into that.
            //UserPresenter presenter = new UserPresenter().notify();

        }
    }

    /**
     * Inner class to run tasks in the background.
     * still working on this. Haven't decided how to go.
     * Parameters are the types for: input for doInBackGround; progress; what we expect back
     */
    private static class createBudgetAsyncTask extends AsyncTask<Budget, Void, Budget> {

        private BudgetDao mAsyncTaskDao;

        //Constructor needs the Dao passed in as it is static.
        createBudgetAsyncTask(BudgetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Budget doInBackground(final Budget... params) {
            mAsyncTaskDao.insertCategory(params[0]);
            return mAsyncTaskDao.getCategoryByName(params[0].getName());
        }
    }

    /**
     * Inner class to run tasks in the background.
     * still working on this. Haven't decided how to go.
     * Parameters are the types for: input for doInBackGround; progress; what we expect back
     */
    private static class UpdateCategoryAsyncTask extends AsyncTask<Budget, Void, Boolean> {

        private BudgetDao mAsyncTaskDao;

        //Constructor needs the Dao passed in as it is static.
        UpdateCategoryAsyncTask(BudgetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Budget... params) {
            mAsyncTaskDao.insertCategory(params[0]);
            return (mAsyncTaskDao.updateCategory(params[0]) == 1);
        }
    }

    /**
     * This is a static, non-async task thread executor inner class option.
     * We can either do threads
     */
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }



}
