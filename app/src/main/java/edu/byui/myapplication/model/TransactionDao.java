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

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Update
    void editTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("DELETE FROM `Transaction`")
    void deleteAll();

    @Query("SELECT * FROM Vendor WHERE id = :vendorId")
    List<Vendor> getVendorName(int vendorId);

    /**
     * For reports. Gets the total spent on a budget
     *
     */
    @Query("SELECT SUM(amount) from 'Transaction' where budget_id = :budgetId")
    public double getTotalInBudget(int budgetId);

    //for reports:
    @Query("SELECT * FROM ReportView")
    public LiveData<List<ReportView>> getReports();

}
