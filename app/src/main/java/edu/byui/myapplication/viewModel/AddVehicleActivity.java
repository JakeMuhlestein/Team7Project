package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.byui.myapplication.R;

public class AddVehicleActivity extends AppCompatActivity {
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

    private EditText vehicleName;
    private EditText vehicleMake;
    private EditText vehicleModel;
    private EditText vehicleYear;
    private EditText vehicleMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleName = findViewById(R.id.edit_text_vehicle_name);
        vehicleMake = findViewById(R.id.edit_text_vehicle_make);
        vehicleModel = findViewById(R.id.edit_text_vehicle_model);
        vehicleYear = findViewById(R.id.edit_text_vehicle_year);
        vehicleMileage = findViewById(R.id.edit_text_vehicle_mileage);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Vehicle");
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
        String year = vehicleYear.getText().toString();
        String mileage = vehicleMileage.getText().toString();

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

        if (year.trim().isEmpty()) {
            Toast.makeText(this, "Please insert valid vehicle year.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mileage.trim().isEmpty()) {
            Toast.makeText(this, "Please insert valid vehicle mileage.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_MAKE,name);
        data.putExtra(EXTRA_MODEL,name);
        data.putExtra(EXTRA_YEAR,name);
        data.putExtra(EXTRA_MILEAGE,name);

        setResult(RESULT_OK,data);
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
