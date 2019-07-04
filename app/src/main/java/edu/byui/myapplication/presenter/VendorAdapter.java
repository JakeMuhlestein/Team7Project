package edu.byui.myapplication.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Vendor;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorHolder> {

    private List<Vendor> vendors = new ArrayList<>();

    @NonNull
    @Override
    public VendorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_item, parent, false);

        return new VendorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorHolder holder, int position) {
        Vendor currentVendor = vendors.get(position);
        holder.vendorName.setText(currentVendor.getName());
    }

    @Override
    public int getItemCount() {
        return vendors.size();
    }

    public void setVendors (List<Vendor> vendors) {
        this.vendors = vendors;
        notifyDataSetChanged();
    }

    class VendorHolder extends RecyclerView.ViewHolder {
        private TextView vendorName;

        public VendorHolder(@NonNull View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.vendorName);
        }
    }
}
