package edu.byui.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.viewModel.AddEditTransactionActivity;
import edu.byui.myapplication.viewModel.TransactionAdapter;
import edu.byui.myapplication.viewModel.TransactionViewModel;

public class TransactionFragment extends Fragment {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private TransactionViewModel transactionViewModel;
    public final String USER_PREFERENCES_KEY = "edu.byu.myapplication.USER_PREFERENCES";
    public final String USER_KEY             = "edu.byu.myapplication.USER_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction,container,false);

        FloatingActionButton buttonAddVendor = view.findViewById(R.id.button_add_transaction);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditTransactionActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int uniqueID = viewHolder.getAdapterPosition();

                transactionViewModel.delete(adapter.getTransactionAt(uniqueID));
                Toast.makeText(getActivity(), "Transaction Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TransactionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transaction transaction) {
                Intent intent = new Intent(getActivity(), AddEditTransactionActivity.class);

                intent.putExtra(AddEditTransactionActivity.EXTRA_ID,transaction.getUserId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_AMOUNT,transaction.getAmount());
                intent.putExtra(AddEditTransactionActivity.EXTRA_COMMENT,transaction.getNotes());
                intent.putExtra(AddEditTransactionActivity.EXTRA_DATE,transaction.getDate());
                intent.putExtra(AddEditTransactionActivity.EXTRA_VENDOR,transaction.getVendorId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_BUDGET,transaction.getBudgetId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_PAYMENT,transaction.getPayMethodId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_USER,transaction.getUserId());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPreferences =
                this.getActivity().getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            String sAmount  = data.getStringExtra(AddEditTransactionActivity.EXTRA_AMOUNT);
            String sComment = data.getStringExtra(AddEditTransactionActivity.EXTRA_COMMENT);
            String sDate    = data.getStringExtra(AddEditTransactionActivity.EXTRA_DATE);
            String sUser    = sharedPreferences.getString(USER_KEY,"dongvt");

            int iVendor  = data.getIntExtra(AddEditTransactionActivity.EXTRA_VENDOR,-1);
            int iBudget  = data.getIntExtra(AddEditTransactionActivity.EXTRA_BUDGET,-1);
            int iPayment = data.getIntExtra(AddEditTransactionActivity.EXTRA_PAYMENT,-1);
            int iUser    = data.getIntExtra(AddEditTransactionActivity.EXTRA_USER,-1);

            Date dDate = convertStringToDate(sDate);

            Transaction newTransaction = new Transaction(iUser,
                    dDate,
                    iVendor,
                    iPayment,
                    iBudget,
                    Double.parseDouble(sAmount),
                    sComment);
            transactionViewModel.insert(newTransaction);

            Toast.makeText(getActivity(), "Transaction Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra(AddEditTransactionActivity.EXTRA_ID,-1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Error: please check with the administrator", Toast.LENGTH_SHORT).show();
                return;
            }

            String sAmount  = data.getStringExtra(AddEditTransactionActivity.EXTRA_AMOUNT);
            String sComment = data.getStringExtra(AddEditTransactionActivity.EXTRA_COMMENT);
            String sDate    = data.getStringExtra(AddEditTransactionActivity.EXTRA_DATE);
            String sUser    = sharedPreferences.getString(USER_KEY,"dongvt");

            int iVendor  = data.getIntExtra(AddEditTransactionActivity.EXTRA_VENDOR,-1);
            int iBudget  = data.getIntExtra(AddEditTransactionActivity.EXTRA_BUDGET,-1);
            int iPayment = data.getIntExtra(AddEditTransactionActivity.EXTRA_PAYMENT,-1);
            int iUser    = data.getIntExtra(AddEditTransactionActivity.EXTRA_USER,-1);

            Date dDate = convertStringToDate(sDate);

            Transaction newTransaction = new Transaction(iUser,
                    dDate,
                    iVendor,
                    iPayment,
                    iBudget,
                    Double.parseDouble(sAmount),
                    sComment);
            newTransaction.setId(id);

            transactionViewModel.update(newTransaction);

            Toast.makeText(getActivity(), "Transaction updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Transaction not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private Date convertStringToDate(String sDate) {
        Date dDate = null;
        try {
            //conversion from String to Java.SQL.date (outdated)
            //https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date

            dDate = new Date(new SimpleDateFormat("MM/dd/yyyy").
                    parse(sDate)
                    .getTime());
        } catch (Exception e) {
            Log.e("AddTransactionError",e.getMessage());
        }
        return dDate;
    }
}
