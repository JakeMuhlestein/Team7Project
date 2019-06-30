package edu.byui.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.R;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;

public class Login extends AppCompatActivity {

    public final String USER_KEY = "edu.byu.myapplication.USER_KEY";
    public final String USER_PREFERENCES_KEY = "UserPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void onPause() {
        super.onPause();
    }

    private void saveSharedPrederences(String json) {

        //Get the user preferences, or create if not created
        SharedPreferences sharedPreferences = getApplicationContext().
                getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);
        //Because we need to write we create an editor of the preferences got in line 43
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Set the preference
        editor.putString(USER_KEY,json);

        //Apply changes into the preferences (This is the part that really saves the data)
        editor.apply();

        Toast.makeText(getApplicationContext(),
                "User saved",
                Toast.LENGTH_SHORT).show();
    }

    public void logIn(View view) {
        Gson g = new Gson();

        //Create the new user (This is stub)
        User user = new User("Team7",
                "The team seven",
                "team7@team7.org",
                "30023107254",
                "This is the address");
        //Create JSON syntax
        String jsonUser = g.toJson(user);

        //Save preferences
        saveSharedPrederences(jsonUser);

        //storing user in database
        DataRepository.getInstance(TeamDatabase.getInstance(this)).createAccount(user);
//        TeamDatabase.getInstance(this).getUserDao().
    }
}
