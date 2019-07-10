package edu.byui.myapplication.viewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.byui.myapplication.R;

public class AddEditBudgetActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "edu.byui.myapplication.viewModel.EXTRA_ID";
    public static final String EXTRA_BUDGET_NAME =
            "edu.byui.myapplication.viewModel.EXTRA_BUDGET_NAME";
    public static final String EXTRA_BUDGET_AMOUNT =
            "edu.byui.myapplication.viewModel.EXTRA_BUDGET_AMOUNT";

    private EditText editTextBudgetName;
    private EditText editTextBudgetAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        editTextBudgetName = findViewById(R.id.edit_budget_name);
        editTextBudgetAmount = findViewById(R.id.edit_budget_amount);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Budget Item");
            editTextBudgetName.setText(intent.getStringExtra(EXTRA_BUDGET_NAME));
            editTextBudgetAmount.setText(intent.getStringExtra(EXTRA_BUDGET_AMOUNT));
        } else {
            setTitle("Add Budget Item");
        }
    }


    private void saveBudget()  {
        String budgetName = editTextBudgetName.getText().toString();
        String budgetAmount = editTextBudgetAmount.getText().toString();

        if (budgetName.trim().isEmpty() || budgetAmount.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a budget name and budget amount",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_BUDGET_NAME, budgetName);
        data.putExtra(EXTRA_BUDGET_AMOUNT, budgetAmount);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_budget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_budget:
                saveBudget();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
