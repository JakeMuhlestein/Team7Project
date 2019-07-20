package edu.byui.myapplication.viewModel;

import android.util.Log;
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

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {
    private final static String TAG = "** ** VehicleAdapter: ";
    private List<Vehicle> vehicles = new ArrayList<>();
    private VehicleAdapter.OnItemClickListener listener;

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
        holder.vehicleAmount.setText(String.valueOf(currentVehicle.getAmount()));
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
        private TextView vehicleAmount;

        public VehicleHolder(@NonNull View vehicleView) {
            super(vehicleView);
            // this is where the vehicle assignment happens.
            vehicleName = vehicleView.findViewById(R.id.recycler_view_vehicle_name);
            vehicleAmount = vehicleView.findViewById(R.id.amount);

            vehicleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d(TAG, "vehicleView.setOnClickListener: onClick has been fired! Position is "
                            + position);
                    // at this point i think listener is always set to null. Why?
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

    /**
     * Arg!
     * @param listener
     */
    public void setOnItemClickListener(VehicleAdapter.OnItemClickListener listener) {
        
        this.listener = listener;
    }


}
