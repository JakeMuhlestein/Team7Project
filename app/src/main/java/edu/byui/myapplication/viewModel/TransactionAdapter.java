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
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.model.TransactionDao;
import edu.byui.myapplication.model.Vendor;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    TransactionDao transactionDao;

    private List<Transaction> transactionList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);

        transactionDao = TeamDatabase.getInstance(itemView.getContext()).getTransactionDao();

        return new TransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionHolder holder, final int position) {


        final List<Vendor>[] vendorList = new List[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                Transaction currentTransaction = transactionList.get(position);
                vendorList[0] = transactionDao.getVendorName(currentTransaction.getVendorId());
                holder.transactionVendorName.setText(vendorList[0].get(0).getName());
                holder.transactionAmount.setText(String.valueOf(currentTransaction.getAmount()));
                holder.transactionDate.setText(format.format(currentTransaction.getDate()));
            }
        }).start();

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(transactionList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
