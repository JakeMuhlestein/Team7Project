package edu.byui.myapplication;

import org.junit.Test;

import edu.byui.myapplication.presenter.UserPresenter;

import static org.junit.Assert.*;


public class UserPresenterTest {
    @Test
    public void createNewUserTest() {
        UserPresenter presenter = new UserPresenter();
        assert(presenter.createNewUser() != null);
    }


}
