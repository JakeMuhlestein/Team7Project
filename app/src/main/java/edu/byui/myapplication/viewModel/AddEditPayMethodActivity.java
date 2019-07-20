package edu.byui.myapplication.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        editTextPayMethodBalance = findViewById(R.id.edit_payMethod_balance);
        editTextPayMethodExpDate = findViewById(R.id.edit_payMethod_expDate);
        editTextPayMethodPoints = findViewById(R.id.edit_payMethod_points);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Pay Method Item");
            editTextPayMethodPayType.setText(intent.getStringExtra(EXTRA_PAYMETHOD_PAY_TYPE));
            editTextPayMethodAcctNumber.setText(intent.getStringExtra(EXTRA_PAYMETHOD_ACCT_NUMBER));
            double balance = intent.getDoubleExtra(EXTRA_PAYMETHOD_BALANCE, 100);
            editTextPayMethodBalance.setText(Double.toString(balance));

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = dateFormat.format(intent.getSerializableExtra(EXTRA_PAYMETHOD_EXP_DATE));
            editTextPayMethodExpDate.setText(strDate);
            //editTextPayMethodExpDate.setText(intent.getStringExtra(EXTRA_PAYMETHOD_EXP_DATE));
            double points = intent.getDoubleExtra(EXTRA_PAYMETHOD_POINTS, 100);
            editTextPayMethodPoints.setText(Double.toString(points));

        } else {
            setTitle("Add Pay Method Item");
        }
    }


    private void savePayMethod()  {
        String payMethodType = editTextPayMethodPayType.getText().toString();
        String payMethodAcctNum = editTextPayMethodAcctNumber.getText().toString();
        String payMethodBalance = editTextPayMethodBalance.getText().toString();
        String payMethodExpDate = editTextPayMethodExpDate.getText().toString();
        String payMethodPoints = editTextPayMethodPoints.getText().toString();

        if (payMethodType.trim().isEmpty() || payMethodAcctNum.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Pay Method Type and Acct Number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_PAYMETHOD_PAY_TYPE, payMethodType);
        data.putExtra(EXTRA_PAYMETHOD_ACCT_NUMBER, payMethodAcctNum);
        data.putExtra(EXTRA_PAYMETHOD_BALANCE, Double.parseDouble(payMethodBalance));
        data.putExtra(EXTRA_PAYMETHOD_EXP_DATE, payMethodExpDate);
        data.putExtra(EXTRA_PAYMETHOD_POINTS, Double.parseDouble(payMethodPoints));

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
            case R.id.save:
                savePayMethod();
                return true;
            default:
                finish();
                return true;
        }

    }
}
