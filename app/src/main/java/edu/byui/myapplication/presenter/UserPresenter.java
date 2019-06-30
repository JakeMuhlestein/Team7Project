package edu.byui.myapplication.presenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.model.*;


public class UserPresenter extends AndroidViewModel {
    private DataRepository repository;

    public UserPresenter(Application application) {
        super(application);
        repository = DataRepository.getInstance(
                TeamDatabase.getInstance(application.getApplicationContext())
        );
    }

    public User createAccount(User user) {
        return repository.createAccount(user);
    }

    public void notifyUserCreated(User user) {
        // the user has been created in the database. Now what do you want to do with it?
    }

}
