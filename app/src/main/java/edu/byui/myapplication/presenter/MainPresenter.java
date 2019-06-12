package edu.byui.myapplication.presenter;

import java.lang.ref.WeakReference;

import edu.byui.myapplication.MainActivity;

public class MainPresenter {
    // weak references to the MainActivity
    WeakReference<MainActivity> mainActivity;

    public MainPresenter(MainActivity activity) {
        mainActivity = new WeakReference<MainActivity>(activity);
    }
}
