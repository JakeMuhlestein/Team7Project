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
import edu.byui.myapplication.model.PayMethod;

public class TransactionPaymentSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<PayMethod> paymentList = new ArrayList<>();

    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return paymentList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentHolder budgetHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_payment_spinner,parent,false);
            budgetHolder = new PaymentHolder();
            budgetHolder.paymentName = convertView.findViewById(R.id.spinner_payment_name);
            convertView.setTag(budgetHolder);
        } else {
            budgetHolder = (PaymentHolder) convertView.getTag();
        }

        PayMethod method = paymentList.get(position);
        budgetHolder.paymentName.setText(method.getPayType());

        return convertView;
    }

    public void setPaymentList(List<PayMethod>  paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }

    class PaymentHolder {
        private TextView paymentName;
    }
}
