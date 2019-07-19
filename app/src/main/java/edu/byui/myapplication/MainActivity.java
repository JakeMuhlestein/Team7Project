package edu.byui.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.view.MenuActivity;
import edu.byui.myapplication.view.SignUpActivity;
import edu.byui.myapplication.viewModel.AddVendorActivity;
import edu.byui.myapplication.viewModel.VendorAdapter;
import edu.byui.myapplication.viewModel.VendorViewModel;

//Mark Tobler comment
public class MainActivity extends AppCompatActivity {
    public final String USER_KEY = "edu.byu.myapplication.USER_KEY";
    public final String USER_PREFERENCES_KEY = "UserPreferences";

    private VendorViewModel vendorViewModel;

    private EditText edtUsername;
    private EditText edtPassword;
    private TeamDatabase database;

    private Button btLogin;
    private Button btSignUp;

    private UserDao userDao;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Login...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        database = Room.databaseBuilder(this, TeamDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        userDao = database.getUserDao();

        btLogin = findViewById(R.id.login);
        btSignUp = findViewById(R.id.register);

        edtUsername = findViewById(R.id.logUsername);
        edtPassword = findViewById(R.id.logpassword);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.getUser(edtUsername.getText().toString(), edtPassword.getText().toString());
                            if(user!=null){
                                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                                i.putExtra("User", (Parcelable) user);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }else{
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        //startActivityForResult(intent, 1);
        /*
        FloatingActionButton buttonAddVendor = findViewById(R.id.button_add_vendor);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVendorActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final VendorAdapter adapter = new VendorAdapter();
        recyclerView.setAdapter(adapter);

        vendorViewModel = ViewModelProviders.of(this).get(VendorViewModel.class);
        vendorViewModel.getAllVendors().observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(List<Vendor> vendors) {
                adapter.setVendors(vendors);
            }
        });
        */


        /*User user = null;
        String userJson = "";
        Gson g = new Gson();

        //Get the user preferences, or create if not created
        SharedPreferences sharedPreferences = getApplicationContext().
                getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);

        userJson = sharedPreferences.getString(USER_KEY,"");

        if (!userJson.equals("")) {
            //Save the user and go to the menu, when we have saved the preference
            user = new User();
            user = g.fromJson(userJson,User.class);
        }


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        */

        // for Stetho - Facebook's db tool
        Stetho.initializeWithDefaults(this);
        // right now we need DataRepository for Stetho
        DataRepository.getInstance(TeamDatabase.getInstance(this)).doNothing();
        
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddVendorActivity.EXTRA_NAME);

            Vendor vendor = new Vendor(name);
            vendorViewModel.insert(vendor);

            Toast.makeText(this, "Vendor saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vendor not saved", Toast.LENGTH_SHORT).show();
        }

    }
    */

    //TODO: onResume needs to include a call to DataRepository's doNothing() method

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUsername.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){
            return true;
        } else {
            return false;
        }
    }


}

