package edu.byui.myapplication.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactionDao {

    /**
     * Just provide an instance of a transaction.class object ready to be created.
     *
     * @param transaction
     * @return ID of the newly created transaction.
     */
    @Insert
    public void createTransaction(Transaction transaction);


    @Query("SELECT * FROM [transaction]  WHERE id = :id")
    public Transaction getTransaction(int id);

    /**
     *
     * @param transaction
     * @return the numbner of rows affected. In this case it should always be 1.
     * Throw an exception if it isn't
     */
    @Update
    public int editTransaction(Transaction transaction);

    @Delete
    public int deleteTransaction(Transaction transaction);

    @Query("DELETE FROM `Transaction`")
    public void deleteAll();
}
