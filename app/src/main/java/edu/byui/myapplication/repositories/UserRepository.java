package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.model.TeamDatabase;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        userDao = teamDatabase.getUserDao();
        allUsers = (LiveData<List<User>>) userDao.getAllUsers();
    }

    public void insert(User user) {

        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {

        new UpdateBudgetAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllusers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.insert(user[0]);
            return null;
        }
    }

    private static class UpdateBudgetAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private UpdateBudgetAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.update(user[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.delete(user[0]);
            return null;
        }
    }
}

