package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.byui.myapplication.R;

public class AddEditPayMethodActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "edu.byui.myapplication.viewModel.EXTRA_ID";
    public static final String EXTRA_PAYMETHOD_PAY_TYPE =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMETHOD_PAY_TYPE";
    public static final String EXTRA_PAYMETHOD_ACCT_NUMBER =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMETHOD_ACCT_NUMBER";
    public static final String EXTRA_PAYMETHOD_BALANCE =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMETHOD_BALANCE";
    public static final String EXTRA_PAYMETHOD_EXP_DATE =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMETHOD_EXP_DATE";
    public static final String EXTRA_PAYMETHOD_POINTS =
            "edu.byui.myapplication.viewModel.EXTRA_PAYMETHOD_POINTS";



    private EditText editTextPayMethodPayType;
    private EditText editTextPayMethodAcctNumber;
    private EditText editTextPayMethodBalance;
    private EditText editTextPayMethodExpDate;
    private EditText editTextPayMethodPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paymethod);

        editTextPayMethodPayType = findViewById(R.id.edit_payMethod_name);
        editTextPayMethodAcctNumber = findViewById(R.id.edit_payMethod_acctNum);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Pay Method Item");
            editTextPayMethodPayType.setText(intent.getStringExtra(EXTRA_PAYMETHOD_PAY_TYPE));
            editTextPayMethodAcctNumber.setText(intent.getStringExtra(EXTRA_PAYMETHOD_ACCT_NUMBER));
        } else {
            setTitle("Add Pay Method Item");
        }
    }


    private void savePayMethod()  {
        String payMethodType = editTextPayMethodPayType.getText().toString();
        String payMethodAcctNum = editTextPayMethodAcctNumber.getText().toString();

        if (payMethodType.trim().isEmpty() || payMethodAcctNum.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Pay Method Type and Acct Number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_PAYMETHOD_PAY_TYPE, payMethodType);
        data.putExtra(EXTRA_PAYMETHOD_ACCT_NUMBER, payMethodAcctNum);

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
        menuInflater.inflate(R.menu.add_paymethod_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_payMethod:
                savePayMethod();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
