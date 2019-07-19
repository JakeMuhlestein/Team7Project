package edu.byui.myapplication.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vehicle;

public class VehicleAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {

    private List<Vehicle> vehicles = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_item, parent, false);

        return new VehicleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        Vehicle currentVehicle = vehicles.get(position);
        holder.vehicleName.setText(currentVehicle.getName());
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public void setVehicles (List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }


    // inner class which the enclosing class extends ? a little chicken and egg thing going on here.
    class VehicleHolder extends RecyclerView.ViewHolder {
        private TextView vehicleName;

        VehicleHolder(@NonNull View vehicleView) {
            super(vehicleView);
            // this is where the vehicle assignment happens.
            vehicleName = vehicleView.findViewById(R.id.vehicleName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(vehicles.get(position));
                    }
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Vehicle vehicle);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
