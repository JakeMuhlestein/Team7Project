package edu.byui.myapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.byui.myapplication.MainActivity;
import edu.byui.myapplication.R;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtDisplayname;
    private EditText edtPassword;
    //private EditText edtComPassword;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtBday;


    private Button btRCancel;
    private Button btRRegister;

    private ProgressDialog progressDialog;

    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        edtUsername = findViewById(R.id.usernameInput);
        edtDisplayname = findViewById(R.id.disnameInput);
        edtPassword = findViewById(R.id.passwordInput);
        //edtComPassword = findViewById(R.id.compasswordInput);
        edtEmail = findViewById(R.id.emailInput);
        edtPhone = findViewById(R.id.phoneInput);
        edtAddress = findViewById(R.id.bdayInput);
        edtBday = findViewById(R.id.bdayInput);

        btRCancel = findViewById(R.id.cancelReg);
        btRRegister = findViewById(R.id.submitReg);

        userDao = Room.databaseBuilder(this, TeamDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();


        btRCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });

        btRRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(
                                    edtUsername.getText().toString(),
                                    edtDisplayname.getText().toString(),
                                    edtPassword.getText().toString(),
                                    edtEmail.getText().toString(),
                                    edtPhone.getText().toString(),
                                    edtAddress.getText().toString(),
                                    edtBday.getText().toString());
                            userDao.insert(user);
                            progressDialog.dismiss();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                    }, 1000);

                } else {
                    Toast.makeText(SignUpActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean isEmpty(){
        if (TextUtils.isEmpty(edtPhone.getText().toString()) || TextUtils.isEmpty(edtAddress.getText().toString()) ||
                TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString()) || /*TextUtils.isEmpty(edtComPassword.getText().toString())
        ||*/ TextUtils.isEmpty(edtUsername.getText().toString()) || TextUtils.isEmpty(edtDisplayname.getText().toString()) || TextUtils.isEmpty(edtBday.getText().toString())){
            return true;
        }else{
            return false;
        }

    }
}



