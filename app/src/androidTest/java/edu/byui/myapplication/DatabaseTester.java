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
import edu.byui.myapplication.model.ReportView;
import edu.byui.myapplication.model.TeamDatabase;
import edu.byui.myapplication.model.Transaction;
import edu.byui.myapplication.model.TransactionDao;
import edu.byui.myapplication.model.User;
import edu.byui.myapplication.model.UserDao;
import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.model.Vehicle;
import edu.byui.myapplication.model.VehicleDao;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.model.VendorDao;

@RunWith(AndroidJUnit4.class)
public class DatabaseTester {
    private UserDao userDao;
    private BudgetDao budgetDao;
    private VehicleDao vehicleDao;
    private PayMethodDao payMethodDao;
    private TransactionDao transactionDao;
    private VendorDao vendorDao;
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
        transactionDao = db.getTransactionDao();
        vendorDao = db.getVendorDao();
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
        Vehicle vehicle = new Vehicle("Ford", "Mustang", 2014, 32333);
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
        User user = new User("ILikeDisco", "IlikeDisco4Realz", "passwordsInOpenText-REALLY",
                "ilikedisco@4realz.edu", "801-555-DISCO", "555 EazeOnDownThe Road", "BIRTHDAYTEXT");
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

    @Test
    public void getTotalInBudgetTransactions() throws Exception {
        //need to create some transaction entries for a budget.
        //create vendor, user, payMethod
        Budget budget = new Budget("testBudget", 500.05);
        budgetDao.insertCategory(budget);
        Vendor vendor = new Vendor("TestVendor");
        vendorDao.insert(vendor);
        User user = new User("1LikeDisco", "1likeDisco1Realz", "passwordsInOpenText-REALLY",
                "1likedisco@4realz.edu", "101-155-DISCO1", "111 EazeOnDownThe Road", "BIRTHDAYTEXT1DontMakeSense");
        userDao.insert(user);
        PayMethod payMethod = new PayMethod("Visa Miles", "55577t7", 3500.00, 6200);
        payMethod.setRewardsType(PayMethod.RewardsType.MILES);
        payMethodDao.insertPayMethod(payMethod);

        double testAmount = 0.00;


        for (int i = 0; i < 6; i++) {
            Transaction transaction = new Transaction(1, new java.sql.Date(System.currentTimeMillis()),
                    1, 1, 1, 5.00 + Double.valueOf(i), "Trans. Total test #" + i);
            transactionDao.createTransaction(transaction);
            testAmount += transaction.getAmount();
        }

        double doubleAmount = transactionDao.getTotalInBudget(1);
        Log.d(TAG, "transaction amount: $" + doubleAmount + " ;  test amount: $" + testAmount);
        assert(doubleAmount == testAmount);

        // now create a different budget and put a bunch of transactions on that budget.
        budgetDao.insertCategory(new Budget("anotherTestBudget", 200.02));

        for (int i = 0; i < 5; i++) {
            Transaction transaction = new Transaction(1, new java.sql.Date(System.currentTimeMillis()),
                    1, 1, 2, 2.00 + Double.valueOf(i), "Trans. Total test2 #" + i);
            transactionDao.createTransaction(transaction);
            testAmount += transaction.getAmount();
}

        double doubleAmount2 = transactionDao.getTotalInBudget(1);

        Log.d(TAG, "2nd transaction amount: $" + doubleAmount2 + " ;  test amount: $" + testAmount);
        assert(doubleAmount == doubleAmount2);
        assert(doubleAmount != testAmount);

        // now let's get a budget from id, get it's amount, and display the difference between the two.
        budget = null;
        budget = budgetDao.getCategoryById(1).get(0);
        double doubleAmount3 = transactionDao.getTotalInBudget(budget.getId());

        Log.d(TAG, "getTotalInBudgetTransactions: budget amount is:" + budget.getAmount() +
                "; transactions total: $" + doubleAmount3);

        assert(doubleAmount3 > 2.00);

        //  ReportView test
        ReportView reportView = null;

        try {
            reportView = transactionDao.getReports().getValue().get(0);
        } catch (Exception e) {
            Log.d(TAG, "ReportView test: Exception:" + e);
        }
        if(reportView!=null)
            Log.d(TAG, "ReportView test: reportview:" + reportView.toString());
        else
            fail();



    }
}