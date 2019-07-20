package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vehicle;

public class VehicleActivity extends AppCompatActivity  {
    private final String TAG = "** ** VehicleActivity: ";
    public static final int ADD_VEHICLE_REQUEST = 1;
    public static final int EDIT_VEHICLE_REQUEST = 2;
    private VehicleViewModel vehicleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vehicle);

        FloatingActionButton buttonAddVehicle = findViewById(R.id.button_add_vehicle);
        buttonAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleActivity.this, AddEditVehicleActivity.class);
                startActivityForResult(intent, ADD_VEHICLE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.vehicle_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "onCreate: About to initialize Vehicle Adapter");
        final VehicleAdapter adapter = new VehicleAdapter();
        recyclerView.setAdapter(adapter);

        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        vehicleViewModel.getAllVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                adapter.setVehicles(vehicles);
            }
        });

        Log.d(TAG, "onCreate: right before adapter.setOnItemClickListener");
        adapter.setOnItemClickListener(new VehicleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vehicle vehicle) {
                Log.d(TAG, "adapter.setOnItemClickListener: onItemClick has been fired!");
                Intent intent = new Intent(VehicleActivity.this, AddEditVehicleActivity.class);

                intent.putExtra(AddEditVehicleActivity.EXTRA_ID, vehicle.getId());
                intent.putExtra(AddEditVehicleActivity.EXTRA_NAME, vehicle.getName());
                intent.putExtra(AddEditVehicleActivity.EXTRA_AMOUNT, vehicle.getAmount());
                intent.putExtra(AddEditVehicleActivity.EXTRA_MAKE, vehicle.getMake());
                intent.putExtra(AddEditVehicleActivity.EXTRA_MODEL, vehicle.getModel());
                intent.putExtra(AddEditVehicleActivity.EXTRA_YEAR, vehicle.getYear());
                intent.putExtra(AddEditVehicleActivity.EXTRA_MILEAGE, vehicle.getMiles());

                startActivityForResult(intent, EDIT_VEHICLE_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_VEHICLE_REQUEST && resultCode == RESULT_OK) {
            String vehicleName = data.getStringExtra(AddEditVehicleActivity.EXTRA_NAME);
            String vehicleMake = data.getStringExtra(AddEditVehicleActivity.EXTRA_MAKE);
            String vehicleModel = data.getStringExtra(AddEditVehicleActivity.EXTRA_MODEL);
            int vehicleYear = data.getIntExtra(AddEditVehicleActivity.EXTRA_YEAR, 1999);
            int vehicleMileage = data.getIntExtra(AddEditVehicleActivity.EXTRA_MILEAGE, 0);

            //verify
            double vehicleAmount = data.getDoubleExtra(AddEditVehicleActivity.EXTRA_AMOUNT, 0);

            // for the Year date
            //Calendar calYear = new GregorianCalendar(vehicleYear, 1, 1);

            Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, vehicleYear, vehicleMileage, vehicleName, vehicleAmount);
            vehicleViewModel.insertVehicle(vehicle);

            Toast.makeText(this, "Vehicle Saved", Toast.LENGTH_SHORT).show();

        } else if(requestCode == EDIT_VEHICLE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditVehicleActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Vehicle can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String vehicleName = data.getStringExtra(AddEditVehicleActivity.EXTRA_NAME);
            String vehicleMake = data.getStringExtra(AddEditVehicleActivity.EXTRA_MAKE);
            String vehicleModel = data.getStringExtra(AddEditVehicleActivity.EXTRA_MODEL);
            int vehicleYear = data.getIntExtra(AddEditVehicleActivity.EXTRA_YEAR, 1999);
            int vehicleMileage = data.getIntExtra(AddEditVehicleActivity.EXTRA_MILEAGE, 0);

            //verify
            double vehicleAmount = data.getDoubleExtra(AddEditVehicleActivity.EXTRA_AMOUNT, 0);

            // for the Year date
            //Calendar calYear = new GregorianCalendar(vehicleYear, 1, 1);

            Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, vehicleYear,
                    vehicleMileage, vehicleName, vehicleAmount);

            vehicle.setId(id);
            vehicleViewModel.updateVehicle(vehicle);

            Toast.makeText(this, "Vehicle item updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Vehicle Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        // Stub for Vehicle
//    }


}
