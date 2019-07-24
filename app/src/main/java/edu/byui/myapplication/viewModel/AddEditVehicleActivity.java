package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import edu.byui.myapplication.R;

public class AddEditVehicleActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_ID";
    public static final String EXTRA_NAME =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_NAME";
    public static final String EXTRA_MAKE =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_MAKE";
    public static final String EXTRA_MODEL =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_MODEL";
    public static final String EXTRA_YEAR =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_YEAR";
    public static final String EXTRA_MILEAGE =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_MILEAGE";
    public static final String EXTRA_AMOUNT =
            "edu.byui.myapplication.viewModel.vehicleActivity.EXTRA_AMOUNT";
    private final String TAG = "AddEditVehicleActivity:";

    private EditText vehicleName;
    private EditText vehicleMake;
    private EditText vehicleModel;
    private EditText vehicleYear;
    private EditText vehicleMileage;
    private EditText vehicleBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleName = findViewById(R.id.edit_text_vehicle_name);
        vehicleMake = findViewById(R.id.edit_text_vehicle_make);
        vehicleModel = findViewById(R.id.edit_text_vehicle_model);
        vehicleYear = findViewById(R.id.edit_text_vehicle_year);
        vehicleMileage = findViewById(R.id.edit_text_vehicle_mileage);
        vehicleBudget = findViewById(R.id.edit_text_vehicle_budget);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Vehicle");
            vehicleName.setText(intent.getStringExtra(EXTRA_NAME));
            vehicleMake.setText(intent.getStringExtra(EXTRA_MAKE));
            vehicleModel.setText(intent.getStringExtra(EXTRA_MODEL));
            vehicleYear.setText(Integer.toString(intent.getIntExtra(EXTRA_YEAR, 1999)) );
            //vehicleYear.setText("1999");
            vehicleMileage.setText(Integer.toString(intent.getIntExtra(EXTRA_MILEAGE, 0)));
            vehicleBudget.setText(String.format(Locale.getDefault(),"%.2f", (intent.getDoubleExtra(EXTRA_AMOUNT, 0.00))));
        } else {
            setTitle("Add Vehicle");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_vehicle_menu, menu);
        return true;
    }

    private void saveVehicle() {
        String name = vehicleName.getText().toString();
        String make = vehicleMake.getText().toString();
        String model = vehicleModel.getText().toString();
        int year = Integer.parseInt(vehicleYear.getText().toString());
        int mileage = Integer.parseInt(vehicleMileage.getText().toString());
        // Need to allow for Vehicle's parent class's members.
        // BudgetName is handled in vehicleName
        Double amount = Double.parseDouble(vehicleBudget.getText().toString());
        Log.d(TAG, "saveVehicle: amount is parsed to " + amount);

        // Vehicle name will be storied in the budgets table as "name"
        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert valid vehicle name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (make.trim().isEmpty()) {
            Toast.makeText(this, "Please insert valid vehicle make.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model.trim().isEmpty()) {
            Toast.makeText(this, "Please insert valid vehicle model.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (year < 1910 ) {
            Toast.makeText(this, "Please insert valid vehicle year.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mileage < 0) {
            Toast.makeText(this, "Please insert valid vehicle mileage.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount < 0) {
            Toast.makeText(this, "Please insert valid budget for this vehicle.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_MAKE, make);
        data.putExtra(EXTRA_MODEL, model);
        data.putExtra(EXTRA_YEAR, year);
        data.putExtra(EXTRA_MILEAGE, mileage);
        data.putExtra(EXTRA_AMOUNT, String.format(Locale.getDefault(), "%.2f", amount));
        // deal with ID. If ID is not set, then the vehicle will not get updated.
        // since it is not in the interface, pull it from the intent THAT WAS PASSED IN.
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // setting ID here as not too mess with a newly created vehicle.
            data.putExtra(EXTRA_ID, id);
        }


        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_vehicle:
                saveVehicle();
                return true;
            default:
                finish();
                return true;
            /*super.onOptionsItemSelected(item);*/
        }

    }

}
