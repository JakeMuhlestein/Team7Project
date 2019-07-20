package edu.byui.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import edu.byui.myapplication.R;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //The drawer is the menu object itself
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //This activity has not action bar, so we need to set it

        //get the Toolbar by the id
        Toolbar toolbar = findViewById(R.id.toolbar);
        //set the toolbar
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.menu_layout);

        //Set the listener, to know when the user click the menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //The object that recognize the item selected
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.menu_drawer_open,
                R.string.menu_drawer_close);

        //set the listener into the drawer
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Get the selected item
        switch(menuItem.getItemId()) {
            case R.id.transaction:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TransactionFragment()).commit();
                break;
            case R.id.vendor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VendorFragment()).commit();
                break;
            case R.id.credit_card:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PayMethodFragment()).commit();
                break;
            case R.id.rewards:
                break;
            case R.id.budgets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BudgetFragment()).commit();
                break;
            case R.id.vehicle:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VehicleFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
