package edu.byui.myapplication;

import org.junit.Test;

import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.presenter.VendorPresenter;

public class VendorPrestenterTest {

    @Test
    public void createNewUserTest() {
        VendorPresenter presenter = new VendorPresenter();
        assert(presenter.updateVendor(new Vendor()) != null);
    }
}
