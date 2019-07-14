package edu.byui.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.viewModel.AddTransactionActivity;
import edu.byui.myapplication.viewModel.TransactionAdapter;
import edu.byui.myapplication.viewModel.TransactionViewModel;

public class TransactionFragment extends Fragment {

    private TransactionViewModel transactionViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction,container,false);

        FloatingActionButton buttonAddVendor = view.findViewById(R.id.button_add_transaction);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        transactionViewModel = ViewModelProviders.of(getActivity()).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> vendors) {
                adapter.setTransaction(vendors);
            }
        });

        return view;
    }
}
