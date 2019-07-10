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

import edu.byui.myapplication.MainActivity;
import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.viewModel.AddVendorActivity;
import edu.byui.myapplication.viewModel.VendorAdapter;
import edu.byui.myapplication.viewModel.VendorViewModel;

public class VendorFragment extends Fragment {

    private VendorViewModel vendorViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vendor,container,false);

        FloatingActionButton buttonAddVendor = view.findViewById(R.id.button_add_vendor);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVendorActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        return inflater.inflate(R.layout.fragment_vendor,container,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra(AddVendorActivity.EXTRA_NAME);

            Vendor vendor = new Vendor(name);
            vendorViewModel.insert(vendor);

            Toast.makeText(getActivity(), "Vendor saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Vendor not saved", Toast.LENGTH_SHORT).show();
        }

    }
}
