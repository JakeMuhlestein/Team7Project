package edu.byui.myapplication.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.Vendor;

public class TransactionVendorSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<Vendor> vendorList = new ArrayList<>();

    @Override
    public int getCount() {
        return vendorList.size();
    }

    @Override
    public Object getItem(int position) {
        return vendorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vendorList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VendorHolder vendorHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transactoin_vendor_spinner,parent,false);
            vendorHolder = new VendorHolder();
            vendorHolder.vendorName = convertView.findViewById(R.id.spinner_vendor_name);
            convertView.setTag(vendorHolder);
        } else {
            vendorHolder = (VendorHolder) convertView.getTag();
        }

        Vendor vendor = vendorList.get(position);
        vendorHolder.vendorName.setText(vendor.getName());

        return convertView;
    }

    public void setVendorList(List<Vendor>  vendorList) {
        this.vendorList = vendorList;
        notifyDataSetChanged();
    }

    class VendorHolder {
        private TextView vendorName;
    }
}
