package edu.byui.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.viewModel.AddVehicleActivity;
import edu.byui.myapplication.viewModel.VehicleAdapter;
import edu.byui.myapplication.viewModel.VehicleViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleFragment} interface
 * to handle interaction events.
 * Use the {@link VehicleFragment} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment {
    // TODO: I haven't implemented much of this except the skeleton. =/
    // TODO: Rename parameter arguments, choose names that match

    private VehicleViewModel vehicleViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vehicle,container,false);

        FloatingActionButton buttonAddVehicle = view.findViewById(R.id.button_add_vehicle);
        buttonAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVehicleActivity.class);
                // TODO: request code reserarch = 1?
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.vehicle_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final VehicleAdapter adapter = new VehicleAdapter();
        recyclerView.setAdapter(adapter);

        vehicleViewModel = ViewModelProviders.of(getActivity()).get(VehicleViewModel.class);
        vehicleViewModel.getAllVehicles().observe(getViewLifecycleOwner(), new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                adapter.setVehicles(vehicles);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // TODO: need to plug in the extra strings from intent
            // TODO: AddVehicleActivity needs doing.
            /*
            String name = data.getStringExtra(AddVehicleActivity.EXTRA_NAME);

            Vehicle vehicle = new Vehicle(name);
            vehicleViewModel.insert(vehicle);
            Toast.makeText(getActivity(), "Vehicle saved", Toast.LENGTH_SHORT).show();
            */
        } else {
            Toast.makeText(getActivity(), "Vehicle not saved", Toast.LENGTH_SHORT).show();
        }

    }

}
