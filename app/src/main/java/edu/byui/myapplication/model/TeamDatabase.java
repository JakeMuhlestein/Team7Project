package edu.byui.myapplication.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Contains the database holder and serves as the main access
 * point for the underlying connection to your app's persisted, relational data.
 *
 * The annotations below are for ROOM database. The version # needs to increase each time the
 * schema changes (any structural changes at all, even simple table name changes). See notes in
 * class below
 *
 */
@Database(entities = {Budget.class, PayMethod.class, Report.class, User.class, Vendor.class}, version = 1)
public abstract class TeamDatabase extends RoomDatabase {

    // I'll need to create an abstract method with 0 arguments returning the @DAO
    // annotated class for each model DAO. I haven't created the DAOs yet. I believe
    // we'll need one for each of the above: Budget, PayMethod, Report, User, Vendor & and any others
    // that haven't been added/completed yet (cough)Govert(cough). They will look like this however:
    public abstract UserDao userDao();

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
 */

}
