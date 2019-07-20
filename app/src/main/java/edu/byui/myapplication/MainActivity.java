package edu.byui.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.view.SignUpActivity;
import edu.byui.myapplication.view.MenuActivity;
import edu.byui.myapplication.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    public final String USER_KEY = "edu.byu.myapplication.USER_KEY";
    public final String USER_PREFERENCES_KEY = "edu.byu.myapplication.USER_PREFERENCES";

    private UserViewModel userViewModel;

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

        /*
        SharedPreferences sharedPreferences =
                getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                editor.putString(USER_KEY, String.valueOf(users.get(0).getUserID()));
            }
        });
        */

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivityForResult(intent, 1);

        // for Stetho - Facebook's db tool
        Stetho.initializeWithDefaults(this);
        // right now we need DataRepository for Stetho
        DataRepository.getInstance(TeamDatabase.getInstance(this)).doNothing();
        
    }

    //TODO: onResume needs to include a call to DataRepository's doNothing() method

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUsername.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){
            return true;
        } else {
            return false;
        }
    }


}

