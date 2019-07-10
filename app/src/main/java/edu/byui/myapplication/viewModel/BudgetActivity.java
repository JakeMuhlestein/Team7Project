package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Budget;

public class BudgetActivity extends AppCompatActivity {
    public static final int ADD_BUDGET_REQUEST = 1;
    public static final int EDIT_BUDGET_REQUEST = 2;

    private BudgetViewModel budgetViewModel;
    //public static final String EXTRA_NAME =
    //        "edu.byui.myapplication.viewModel.EXTRA_NAME";

    //private EditText vendorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_items);

        //vendorName = findViewById(R.id.edit_text_vendor_name);

        FloatingActionButton buttonAddBudget = findViewById(R.id.button_add_budget);
        buttonAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetActivity.this, AddEditBudgetActivity.class);
                startActivityForResult(intent, ADD_BUDGET_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BudgetAdapter adapter = new BudgetAdapter();
        recyclerView.setAdapter(adapter);

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetItems().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                adapter.setBudgets(budgets);
            }
        });


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //setTitle("Add Vendor");

        adapter.setOnItemClickListener(new BudgetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Budget budget) {
                Intent intent = new Intent(BudgetActivity.this, AddEditBudgetActivity.class);

                intent.putExtra(AddEditBudgetActivity.EXTRA_ID, budget.getId());
                intent.putExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME, budget.getName());
                intent.putExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, budget.getAmount());
                startActivityForResult(intent, EDIT_BUDGET_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== ADD_BUDGET_REQUEST && resultCode == RESULT_OK) {
            String budgetName = data.getStringExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME);

            //verify
            int budgetAmount = data.getIntExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, 1);


            Budget budget = new Budget(budgetName, budgetAmount);
            budgetViewModel.insert(budget);

            Toast.makeText(this, "Budget Saved", Toast.LENGTH_SHORT).show();
        } else if(requestCode== EDIT_BUDGET_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditBudgetActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Budget can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String budgetName = data.getStringExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME);

            //verify
            int budgetAmount = data.getIntExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, 1);

            Budget budget = new Budget(budgetName, budgetAmount);
            budget.setId(id);
            budgetViewModel.update(budget);

            Toast.makeText(this, "Budget item updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Budget Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    /*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_vendor_menu, menu);
        return true;
    }

    private void saveVendor() {
        String name = vendorName.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "PLease Insert Vendor Name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_vendor:
                saveVendor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/
}
