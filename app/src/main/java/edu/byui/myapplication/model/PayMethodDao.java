package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PayMethodDao {

    //For this method, just submit the PayMethod object with the proper point amount
//    @Update
//    public void addRewardPoints(PayMethod payMethod, int points);



    @Insert
    public void insertPayMethod(PayMethod payMethod);

    @Update
    public void updatePayMethod(PayMethod payMethod);

    @Delete
    public void deletePayMethod(PayMethod payMethod);

    //Delete all from table
    @Query("DELETE FROM paymethod")
    void deleteAllPayMethodItems();

    @Query("DELETE FROM paymethod")
    public void deleteAll();

    @Query("SELECT * FROM paymethod")
    LiveData<List<PayMethod>> getAllPaymentMethods();

    @Query("SELECT * FROM paymethod")
    List<PayMethod> getAllSub();

}
