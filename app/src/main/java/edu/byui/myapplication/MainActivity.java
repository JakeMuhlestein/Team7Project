package edu.byui.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

import edu.byui.myapplication.model.User;
import edu.byui.myapplication.view.Login;

//Mark Tobler comment
public class MainActivity extends AppCompatActivity {
    public final String USER_KEY = "edu.byu.myapplication.USER_KEY";
    public final String USER_PREFERENCES_KEY = "UserPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = null;
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


        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
