package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCategory(Budget budget);

    //@Query("UPDATE budget SET name = :budgetName, amount = :budgetAmount WHERE id = :id")
    // Simply pass it the updated Budget object
    @Update
    public void updateCategory(Budget budget);

    // this will delete based on the ID.
    @Delete
    public void deleteCategory(Budget budget);

    //Delete all from table
    @Query("DELETE FROM budget")
    void deleteAllBudgetItems();

    //We're probably going to need another column called "active" or something
    @Query("SELECT * FROM budget")
    public LiveData<List<Budget>> getAllCategories();


    @Update
    public void updateCategories(List<Budget> budgets);


    @Query("SELECT * FROM budget WHERE name = :name")
    public Budget getCategoryByName(String name);

    /**
     * perhaps this conflict strategy should be abort?
     * @param budget
     * @return void
     * old return value: returns the ID of the budget inserted.
     */



}
