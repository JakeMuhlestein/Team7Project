package edu.byui.myapplication;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;

@RunWith(AndroidJUnit4.class)
public class DatabaseTester {
    private UserDao userDao;
    private TeamDatabase db;
    private final String TAG = "DatabaseTester";

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TeamDatabase.class).build();
        userDao = db.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
//        User user = TestUtil.createUser(3);
        User user = new User("ILikeDisco", "IlikeDisco4Realz",
                "ilikedisco@4realz.edu", "801-555-DISCO", "555 EazeOnDownThe Road");
        userDao.insert(user);
        Log.d(TAG, "user:" + user.toString());
        List<User> byName = userDao.loadUserByUserame("ILikeDisco");
        Log.d(TAG, "user from database:" + byName.get(0).toString());
        assert(byName.get(0).getUsername().contentEquals(user.getUsername()));

    }
}