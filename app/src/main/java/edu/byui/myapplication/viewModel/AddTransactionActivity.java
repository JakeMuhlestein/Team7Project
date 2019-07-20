package edu.byui.myapplication.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.model.Vendor;

public class AddTransactionActivity extends AppCompatActivity {
    //Extra Keys
    public static final String EXTRA_USER =
            "edu.byui.myapplication.viewModel.EXTRA_USER";
    public static final String EXTRA_BUDGET =
            "edu.byui.myapplication.viewModel.EXTRA_BUDGET";
    public static final String EXTRA_VENDOR =
            "edu.byui.myapplication.viewModel.EXTRA_VENDOR";
    public static final String EXTRA_PAYMENT =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMENT";
    public static final String EXTRA_DATE =
            "edu.byui.myapplication.viewModel.EXTRA_DATE";
    public static final String EXTRA_AMOUNT =
            "edu.byui.myapplication.viewModel.EXTRA_AMOUNT";
    public static final String EXTRA_COMMENT =
            "edu.byui.myapplication.viewModel.EXTRA_COMMENT";

    private BudgetViewModel budgetViewModel;
    private VendorViewModel vendorViewModel;
    private PayMethodViewModel payMethodViewModel;
    private UserViewModel userViewModel;


    private Spinner vendorSpinner;
    private Spinner budgetSpinner;
    private Spinner paymentSpinner;
    private EditText date;
    private EditText amount;
    private EditText comment;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        //Load spinners
        populateBudgets();
        populateVendors();
        populatePayMethods();
        populateUser();

        //Load EditText fields
        date    = findViewById(R.id.transaction_date);
        amount  = findViewById(R.id.transaction_amount);
        comment = findViewById(R.id.transaction_comment);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Transaction");

        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveTransaction();
                return true;
            default:
                finish();
                return true;
            /*super.onOptionsItemSelected(item);*/
        }
    }

    private void saveTransaction() {
        //the 's' means String - i means integer
        String sAmount  = amount.getText().toString();
        String sComment = comment.getText().toString();
        String sDate    = date.getText().toString();

        int iVendor  = (int) vendorSpinner.getSelectedItemId();
        int iBudget  = (int) budgetSpinner.getSelectedItemId();
        int iPayment = (int) paymentSpinner.getSelectedItemId();

        if (sComment.trim().isEmpty()) {
            Toast.makeText(this, "Please Insert a Comment", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COMMENT,sComment);
        data.putExtra(EXTRA_AMOUNT,sAmount);
        data.putExtra(EXTRA_DATE,sDate);

        data.putExtra(EXTRA_VENDOR,iVendor);
        data.putExtra(EXTRA_BUDGET,iBudget);
        data.putExtra(EXTRA_PAYMENT,iPayment);
        data.putExtra(EXTRA_USER,userID);

        setResult(RESULT_OK,data);
        finish();
    }

    private void populateVendors() {
        vendorSpinner = findViewById(R.id.transaction_vendor_spinner);

        final TransactionVendorSpinnerAdapter baseAdapterVendor = new TransactionVendorSpinnerAdapter();

        vendorViewModel = ViewModelProviders.of(this).get(VendorViewModel.class);
        vendorViewModel.getAllVendors().observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(List<Vendor> vendors) {
                baseAdapterVendor.setVendorList(vendors);
            }
        });
        vendorSpinner.setAdapter(baseAdapterVendor);

    }

    private void populateBudgets() {
        budgetSpinner = findViewById(R.id.transaction_budget_spinner);

        final TransactionBudgetSpinnerAdapter baseAdapterBudget = new TransactionBudgetSpinnerAdapter();

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetItems().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                baseAdapterBudget.setBudgetList(budgets);
            }
        });
        budgetSpinner.setAdapter(baseAdapterBudget);
    }

    private void populatePayMethods() {
        paymentSpinner = findViewById(R.id.transaction_pay_method_spinner);

        final TransactionPaymentSpinnerAdapter baseAdapterPayment = new TransactionPaymentSpinnerAdapter();

        payMethodViewModel = ViewModelProviders.of(this).get(PayMethodViewModel.class);
        payMethodViewModel.getAllPayMethodItems().observe(this, new Observer<List<PayMethod>>() {
            @Override
            public void onChanged(List<PayMethod> payMethods) {
                baseAdapterPayment.setPaymentList(payMethods);
            }
        });
        paymentSpinner.setAdapter(baseAdapterPayment);
    }

    private void populateUser() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userID = users.get(0).getUserID();
            }
        });
    }

}
