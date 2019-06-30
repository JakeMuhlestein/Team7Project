package edu.byui.myapplication.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.List;

public class UserTypeConverter {
    @TypeConverter
    public String toString(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @TypeConverter
    public User fromString(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, User.class);
    }

//    @TypeConverter
//    public int toInt(User user) {
//        if(user != null) {
//            return user.getUserID();
//        } else {
//            return 0;
//        }
//    }

}
