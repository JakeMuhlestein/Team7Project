package edu.byui.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.view.MenuActivity;
import edu.byui.myapplication.viewModel.AddVendorActivity;
import edu.byui.myapplication.viewModel.VendorAdapter;
import edu.byui.myapplication.viewModel.VendorViewModel;

//Mark Tobler comment
public class MainActivity extends AppCompatActivity {
    public final String USER_KEY = "edu.byu.myapplication.USER_KEY";
    public final String USER_PREFERENCES_KEY = "UserPreferences";

    private VendorViewModel vendorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivityForResult(intent, 1);
        /*
        FloatingActionButton buttonAddVendor = findViewById(R.id.button_add_vendor);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVendorActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final VendorAdapter adapter = new VendorAdapter();
        recyclerView.setAdapter(adapter);

        vendorViewModel = ViewModelProviders.of(this).get(VendorViewModel.class);
        vendorViewModel.getAllVendors().observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(List<Vendor> vendors) {
                adapter.setVendors(vendors);
            }
        });
        */


        /*User user = null;
        String userJson = "";
        Gson g = new Gson();

        //Get the user preferences, or create if not created
        SharedPreferences sharedPreferences = getApplicationContext().
                getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);

        userJson = sharedPreferences.getString(USER_KEY,"");

        if (!userJson.equals("")) {
            //Save the user and go to the menu, when we have saved the preference
            user = new User();
            user = g.fromJson(userJson,User.class);
        }


        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        */

        // for Stetho - Facebook's db tool
        Stetho.initializeWithDefaults(this);
        DataRepository.getInstance(TeamDatabase.getInstance(this)).doNothing();
        
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddVendorActivity.EXTRA_NAME);

            Vendor vendor = new Vendor(name);
            vendorViewModel.insert(vendor);

            Toast.makeText(this, "Vendor saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vendor not saved", Toast.LENGTH_SHORT).show();
        }

    }
    */

    //TODO: onResume needs to include a call to DataRepository's doNothing() method
}
