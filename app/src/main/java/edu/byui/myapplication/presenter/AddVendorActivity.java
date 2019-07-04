package edu.byui.myapplication.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.byui.myapplication.R;

public class AddVendorActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "edu.byui.myapplication.presenter.EXTRA_NAME";

    private EditText vendorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);

        vendorName = findViewById(R.id.edit_text_vendor_name);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Vendor");
    }

    @Override
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

    }
}
