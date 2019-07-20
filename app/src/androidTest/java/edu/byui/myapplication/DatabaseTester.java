package edu.byui.myapplication;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.BudgetDao;
import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.model.PayMethodDao;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.model.VehicleDao;

@RunWith(AndroidJUnit4.class)
public class DatabaseTester {
    private UserDao userDao;
    private BudgetDao budgetDao;
    private VehicleDao vehicleDao;
    private PayMethodDao payMethodDao;
    private TeamDatabase db;
    private final String TAG = "DatabaseTester";

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TeamDatabase.class).build();
        userDao = db.getUserDao();
        budgetDao = db.getBudgetDao();
        vehicleDao = db.getVehicleDao();
        payMethodDao = db.getPayMethodDao();

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void createVehicleTest() {
        // first fire-up the test database in memory
        // doesn't affect the actual database.
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TeamDatabase.class).build();
        vehicleDao = db.getVehicleDao();
        // create an object that we want to save in the database.
        Vehicle vehicle = new Vehicle("Ford", "Mustang", new Date(System.currentTimeMillis()), 32333);
        vehicleDao.insertVehicle(vehicle);
        LiveData<List<Vehicle>> vehicles = vehicleDao.getAllVehicles();
        if(vehicles.getValue() != null) {
            Log.d(TAG, "Vehicle: " + vehicles.getValue().get(0).toString());
        } else {
            Log.d(TAG, "Vehicle: failed to create");
            assert(false);
            // didn't pass
        }

    }

    @Test
    public void writeUserAndReadInList() throws Exception {
//        User user = TestUtil.createUser(3);
        User user = new User("ILikeDisco", "IlikeDisco4Realz",
                "ilikedisco@4realz.edu", "801-555-DISCO", "555 EazeOnDownThe Road");
        userDao.insert(user);
        Log.d(TAG, "user:" + user.toString());
        List<User> byName = userDao.loadUserByUsername("ILikeDisco");
        Log.d(TAG, "user from database:" + byName.get(0).toString());
        assert(byName.get(0).getUsername().contentEquals(user.getUsername()));

    }

    @Test
    public void testPayMethodRewardsType() throws Exception {
        PayMethod payMethod = new PayMethod("Discover Rewards", "55577t7", 3100.00, 6200);
        payMethod.setRewardsType(PayMethod.RewardsType.MILES);
        Log.d(TAG, "***payMethod: inserting payMethod into db: " + payMethod.toString());
        payMethodDao.insertPayMethod(payMethod);
        List<PayMethod> payMethods = payMethodDao.getAllPaymentMethods().getValue();
        int i = 0;
        if(payMethods != null && payMethods.size() > 0) {
            for (PayMethod pm : payMethods
            ) {
                Log.d(TAG, "payMethod " + i++ + ": " + pm.toString());
            }
            assert (payMethods.get(0).getRewardsType() == PayMethod.RewardsType.MILES);
        } else {
            fail();
        }
    }

    @Test
    public void createCategory() throws Exception {
        Budget budget = new Budget();
        budget.setName("New Budget");
        budget.setAmount(5000.00);
        Log.d(TAG, "Checking ID is 0 before database insert.");

        assert(budget.getId() == 0);
        budgetDao.insertCategory(budget);
        Budget anotherBudget = budgetDao.getCategoryByName(budget.getName());
        Log.d(TAG, "anotherBudget:" + anotherBudget.toString());
        assert(anotherBudget.getId() != 0);

    }

    @Test
    public void createCategoriesAndRetrieve() throws Exception {
        Budget budget = new Budget();
        budget.setName("500 Dollar Budget");
        budget.setAmount(500.00);
        Log.d(TAG, "createCategoriesAndRetrieve: Creating 500 Dollar Budget");
        budgetDao.insertCategory(budget);
        // creating another
        Budget anotherBudget = new Budget();
        anotherBudget.setName("900 Euro Budget");
        anotherBudget.setAmount(900.00);
        Log.d(TAG, "createCategoriesAndRetrieve: Creating 900 Euro Budget");
        budgetDao.insertCategory(anotherBudget);
        // now retrieve all budgets
        Log.d(TAG, "createCategoriesAndRetrieve: Getting all budgets from database through getAllCategories.");
        LiveData<List<Budget>> collectionOfBudgets = budgetDao.getAllCategories();
        List<Budget> budgets = collectionOfBudgets.getValue();
        // Let's see what we can do with the LiveData on the testing end.
        if (budgets != null) {
            Log.d(TAG, "createCategoriesAndRetrieve: checking size is greater than 1:" + budgets.size()); // .getValue().size());
//            assert (collectionOfBudgets.getValue().size() > 1);
            assert (budgets.size() > 1);
            // print out budgets.
            int i = 0;
            for (Budget b : budgets) {
                Log.d(TAG, "Collection of Budgets #:" + i++ + ": " + b.toString());
                assert (b.getId() > 0);
            }
        } else {
            Log.d(TAG, "createCategoriesAndRetrieve: LiveData collectionOfBudgets is null.");
            fail();
        }

        Log.d(TAG, "createCategoriesAndRetrieve: Finished createCategoriesAndRetrieve:" + anotherBudget.toString());


    }
}