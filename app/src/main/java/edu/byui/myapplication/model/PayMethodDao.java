package edu.byui.myapplication.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PayMethodDao {

    //For this method, just submit the PayMethod object with the proper point amount
//    @Update
//    public void addRewardPoints(PayMethod payMethod, int points);

    @Insert
    public void addPayMethod(PayMethod payMethod);

}
