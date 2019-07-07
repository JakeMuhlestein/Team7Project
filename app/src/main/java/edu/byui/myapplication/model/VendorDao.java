package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VendorDao {

    @Insert
    void insert(Vendor vendor);

    @Update
    void update(Vendor vendor);

    @Delete
    void delete(Vendor vendor);

    @Query("DELETE FROM vendor")
    void deleteAll();

    @Query("SELECT * FROM vendor ORDER BY name")
    LiveData<List<Vendor>> getAllVendors();

    @Query("SELECT * FROM vendor ORDER BY id")
    List<Vendor> getAllVendorsStub();

}
