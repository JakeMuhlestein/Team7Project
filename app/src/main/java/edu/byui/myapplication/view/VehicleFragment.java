package edu.byui.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.viewModel.AddEditVehicleActivity;
import edu.byui.myapplication.viewModel.VehicleAdapter;
import edu.byui.myapplication.viewModel.VehicleViewModel;

import static android.app.Activity.RESULT_OK;
import static edu.byui.myapplication.viewModel.VehicleActivity.ADD_VEHICLE_REQUEST;
import static edu.byui.myapplication.viewModel.VehicleActivity.EDIT_VEHICLE_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleFragment} interface
 * to handle interaction events.
 * Use the {@link VehicleFragment} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment {
    private final String TAG = "** ** VehicleFragment: ";
    public static final int ADD_VEHICLE_REQUEST = 1;
    public static final int EDIT_VEHICLE_REQUEST = 2;
    private VehicleViewModel vehicleViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vehicle,container,false);

        FloatingActionButton buttonAddVehicle = view.findViewById(R.id.button_add_vehicle);
        buttonAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditVehicleActivity.class);
                startActivityForResult(intent, ADD_VEHICLE_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.vehicle_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "onCreate: About to initialize Vehicle Adapter");
        final VehicleAdapter adapter = new VehicleAdapter();
        recyclerView.setAdapter(adapter);

        vehicleViewModel = ViewModelProviders.of(getActivity()).get(VehicleViewModel.class);
        vehicleViewModel.getAllVehicles().observe(getViewLifecycleOwner(), new Observer<List<Vehicle>>() {
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
                Intent intent = new Intent(getActivity(), AddEditVehicleActivity.class);

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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_VEHICLE_REQUEST && resultCode == RESULT_OK) {
            String vehicleName = data.getStringExtra(AddEditVehicleActivity.EXTRA_NAME);
            String vehicleMake = data.getStringExtra(AddEditVehicleActivity.EXTRA_MAKE);
            String vehicleModel = data.getStringExtra(AddEditVehicleActivity.EXTRA_MODEL);
            int vehicleYear = data.getIntExtra(AddEditVehicleActivity.EXTRA_YEAR, 1999);
            int vehicleMileage = data.getIntExtra(AddEditVehicleActivity.EXTRA_MILEAGE, 0);

            //verify
            double vehicleAmount = Double.parseDouble(data.getStringExtra(AddEditVehicleActivity.EXTRA_AMOUNT));
                //double vehicleAmount = data.getDoubleExtra(AddEditVehicleActivity.EXTRA_AMOUNT, 0.00);

                // for the Year date
                //Calendar calYear = new GregorianCalendar(vehicleYear, 1, 1);

                //Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, new Date(calYear.getTimeInMillis()), vehicleMileage, vehicleName, vehicleAmount);
            Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, vehicleYear,
                    vehicleMileage, vehicleName, vehicleAmount);
            vehicleViewModel.insertVehicle(vehicle);
            Toast.makeText(getActivity(), "Vehicle Saved", Toast.LENGTH_SHORT).show();

        } else if(requestCode== EDIT_VEHICLE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditVehicleActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Vehicle can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String vehicleName = data.getStringExtra(AddEditVehicleActivity.EXTRA_NAME);
            String vehicleMake = data.getStringExtra(AddEditVehicleActivity.EXTRA_MAKE);
            String vehicleModel = data.getStringExtra(AddEditVehicleActivity.EXTRA_MODEL);
            int vehicleYear = data.getIntExtra(AddEditVehicleActivity.EXTRA_YEAR, 1999);
            int vehicleMileage = data.getIntExtra(AddEditVehicleActivity.EXTRA_MILEAGE, 0);

            //verify
            double vehicleAmount = data.getDoubleExtra(AddEditVehicleActivity.EXTRA_AMOUNT, 0.00);

            // for the Year date
            //Calendar calYear = new GregorianCalendar(vehicleYear, 1, 1);

            Vehicle vehicle = new Vehicle(vehicleMake, vehicleModel, vehicleYear,
                        vehicleMileage, vehicleName, vehicleAmount);
            vehicle.setId(id);
            vehicleViewModel.updateVehicle(vehicle);

            Toast.makeText(getActivity(), "Vehicle updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Vehicle not saved", Toast.LENGTH_SHORT).show();
        }

    }

}
