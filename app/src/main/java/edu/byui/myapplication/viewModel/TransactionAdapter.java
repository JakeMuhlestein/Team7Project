package edu.byui.myapplication.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    private List<Transaction> transactionList = new ArrayList<>();

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item,parent,false);
        return new TransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        Transaction currentTransaction = transactionList.get(position);

        holder.transactionAmount.setText(String.valueOf(currentTransaction.getAmount()));
        holder.transactionDate.setText(format.format(currentTransaction.getDate()));
        holder.transactionVendorName.setText(String.valueOf(currentTransaction.getVendorId()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transactionList = transactions;
        notifyDataSetChanged();
    }

    public Transaction getTransactionAt(int position) {
        return transactionList.get(position);
    }

    class TransactionHolder extends RecyclerView.ViewHolder {
        private TextView transactionVendorName;
        private TextView transactionDate;
        private TextView transactionAmount;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            transactionVendorName = itemView.findViewById(R.id.text_view_transaction_vendor);
            transactionDate = itemView.findViewById(R.id.text_view_transaction_date);
            transactionAmount = itemView.findViewById(R.id.text_view_transaction_amount);
        }
    }
}
