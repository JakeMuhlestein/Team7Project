package edu.byui.myapplication.presenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.model.*;


public class UserPresenter extends AndroidViewModel {
    private DataRepository repository;

    UserPresenter(Application application) {
        super(application);
        repository = DataRepository.getInstance(
                TeamDatabase.getInstance(application.getApplicationContext())
        );
    }

    public User createAccount(User user) {
        return repository.createAccount(user);
    }

}
