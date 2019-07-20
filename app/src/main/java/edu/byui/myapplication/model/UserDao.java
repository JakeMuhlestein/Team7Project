package edu.byui.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Used by ROOM library to access database for Users. This is where all user SQL queries go.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id  = :userId")
    User loadUserById(int userId);

    @Query("SELECT * FROM user WHERE username  = :username and password = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM user WHERE username  = :username")
    List<User> loadUserByUsername(String username);

    @Insert
    void insert(User users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    // no blanket updates
}
