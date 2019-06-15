package edu.byui.myapplication;

import org.junit.Test;

import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.presenter.PayMethodPresenter;

public class PayMethodPresenterTest {

    @Test
    public void createNewUserTest() {
        PayMethodPresenter presenter = new PayMethodPresenter();
        PayMethod payMethod = new PayMethod();
        //assert(presenter.addRewardPoints(payMethod, 1000) );

    }
}
