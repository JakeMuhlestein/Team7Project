package edu.byui.myapplication.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the database holder and serves as the main access
 * point for the underlying connection to your app's persisted, relational data.
 *
 * The annotations below are for ROOM database. The version # needs to increase each time the
 * schema changes (any structural changes at all, even simple table name changes). See notes in
 * class below
 *
 */
@Database(entities = {Budget.class, PayMethod.class, Report.class, Transaction.class, User.class, Vendor.class, Vehicle.class}, version = 2)
@TypeConverters({DateTypeConverter.class})
public abstract class TeamDatabase extends RoomDatabase {
    final static String TAG = "TeamDatabase: ";
    // This is the actual database instance.
    private static TeamDatabase INSTANCE;

    // abstract methods with 0 arguments returning a @Dao
    // for each class/entity.
    // we'll need one for each of the entities above
    public abstract BudgetDao getBudgetDao();
    public abstract PayMethodDao getPayMethodDao();
    public abstract TransactionDao getTransactionDao();
    public abstract UserDao getUserDao();
    public abstract VehicleDao getVehicleDao();
    public abstract VendorDao getVendorDao();

    // for synchronization of database creation
    private static final Object sLock = new Object();


/**
 * Migration notes:
 * If there is data in existing databases, rather than overwrite it because it is useful
 * data we want to keep in the database, we need to provide migration plans in the form of
 * anonymous like classes between the versions. These can reside as nested classes
 * inside this Database extending ,abstract class. Each migration follows this format:
 * // migrating from version 1 => 2
 * static final Migration MIGRATION_1_2 = new Migration(1, 2) {
 *     @Override
 *     public void migrate(SupportSQLiteDatabase database) {
 *         database.execSQL("CREATE TABLE `Records` (`id` INTEGER, "
 *                 + "`name` TEXT, PRIMARY KEY(`id`))");
 *     }
 * };
 * // migrating from version 2 => 3
 * static final Migration MIGRATION_2_3 = new Migration(2, 3) {
 *     @Override
 *     public void migrate(SupportSQLiteDatabase database) {
 *         database.execSQL("ALTER TABLE Vendors "
 *                 + " ADD COLUMN budget_id INTEGER, FOREIGN KEY("Budget ");
 *     }
 * };
 *
 */

    /**
     * Returns an instance of the database managed by room.
     * @param context application context
     * @return TeamDatabase instance
     *
     */
    public static TeamDatabase getInstance(Context context) {
        Log.i(TAG,"Geting instance");
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TeamDatabase.class, "Team.db")
                        .fallbackToDestructiveMigration()   // This deletes all the data when the database changes.
                        .addCallback(roomCallback)
                        .build();
            }
            return INSTANCE;
        }
    }

    public static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) { //if the database is not created
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {//if the database is created
            super.onOpen(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        final String TAG = "PopulateDB Thread: ";

        private VendorDao vendor;
        private UserDao user;
        private PayMethodDao payMethod;
        private BudgetDao budget;
        private TransactionDao transaction;

        private PopulateDBAsyncTask(TeamDatabase db) {
            transaction = db.getTransactionDao();
            vendor = db.getVendorDao();
            user = db.getUserDao();
            payMethod = db.getPayMethodDao();
            budget = db.getBudgetDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG,"Executing background");
            deleteAll();
            createStubUsers();
            createStubVendors();
            createStubBudgets();
            createStubPayMethods();
            createStubTransactions();
            return null;
        }

        private void deleteAll() {
            transaction.deleteAll();
            user.deleteAll();
            payMethod.deleteAll();
            budget.deleteAllBudgetItems();
            vendor.deleteAll();
        }

        private void createStubUsers() {
            user.insert(new User("dongvt",
                                 "Govert",
                                 "email@email.com",
                                 "5555555",
                                 "av siempre viva"
                        ));
        }

        private void createStubPayMethods() {
            payMethod.insert(new PayMethod("Cash","Number555",555555,60000));
            payMethod.insert(new PayMethod("Credit","84728727",88888,10000));
            payMethod.insert(new PayMethod("Card","272975",9999,20000));
        }

        private void createStubBudgets() {
            budget.insertCategory(new Budget("Home",50));
            budget.insertCategory(new Budget("Car",300));
            budget.insertCategory(new Budget("Food",500));
        }

        private void createStubVendors() {
            vendor.insert(new Vendor("Govert"));
            vendor.insert(new Vendor("Armando"));
            vendor.insert(new Vendor("Julio"));
        }

        private void createStubTransactions() {
            //The t means "transaction"
            List<Vendor> tVendor = vendor.getAllVendorsStub();
            List<Budget> tBudget = budget.getAllCategoriesStub();
            List<PayMethod> tPayMethod = payMethod.getAllSub();
            List<User> tUser =  user.loadUserByUsername("dongvt");

            transaction.createTransaction(new Transaction(tUser.get(0).getUserID(),
                    new Date(000000000),
                    tVendor.get(1).getId(),
                    tPayMethod.get(1).getId(),
                    tBudget.get(1).getId(),
                    25,
                    "First example"));

            transaction.createTransaction(new Transaction(tUser.get(0).getUserID(),
                    new Date(33333),
                    tVendor.get(2).getId(),
                    tPayMethod.get(1).getId(),
                    tBudget.get(2).getId(),
                    12,
                    "Second example"));

            transaction.createTransaction(new Transaction(tUser.get(0).getUserID(),
                    new Date(0000020000),
                    tVendor.get(0).getId(),
                    tPayMethod.get(1).getId(),
                    tBudget.get(2).getId(),
                    20,
                    "Third example"));
        }

    }



}
