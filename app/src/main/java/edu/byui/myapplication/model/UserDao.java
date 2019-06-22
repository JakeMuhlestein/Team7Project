package edu.byui.myapplication.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Used by ROOM library to access database for Users. This is where all user SQL queries go.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id  = :userId")
    User loadUserByIds(int userId);

//    @Query("SELECT * FROM user WHERE username  = :username")
//    User loadUserByUserName(int userId);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    // no blanket updates
}
