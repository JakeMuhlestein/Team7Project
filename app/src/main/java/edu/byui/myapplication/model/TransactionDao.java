package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {

    /**
     * @param transaction
     * @return ID of the newly created transaction.
     */
    @Insert
    void createTransaction(Transaction transaction);

    @Query("SELECT * FROM [transaction]  WHERE id = :id")
    Transaction getTransaction(int id);

    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllTransactions();

    @Update
    void editTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("DELETE FROM `Transaction`")
    void deleteAll();
}
