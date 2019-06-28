package edu.byui.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;

public class DataRepository {
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
     * Creates an account.
     * @param user
     * @return User newly persisted to the database with ID and all.
     */
    public User createAccount(User user) {
        db.getUserDao().insert(user);
        return db.getUserDao().loadUserByUsername(user.getUsername()).get(0);
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
     * Inner class to run tasks in the background.
     * still working on this. Haven't decided how to go.
     */
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * This is a static, non-async task thread executor inner class.
     * still working on it.
     */
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }



}
