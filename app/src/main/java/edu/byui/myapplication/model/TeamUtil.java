package edu.byui.myapplication.model;

/**
 * This singleton is for Project utilities. Can be called to get the next unique ID for entities.
 * There is room for other utilities.
 * Only one will exist per JVM.
 * This class may be deprecated.
 */
public class TeamUtil {

    // for now (shoot me if you want later) we're just going to pull from this common pool
    // of id's. Meaning every ID will be unique even from other entities.
    private static final int max = 2147483647;
    private int currentID = 0;
    private static TeamUtil singleton = new TeamUtil(); //static?

    /**
     * Accessor to get handle on singleton.
     */
    public TeamUtil instance() {
        return singleton;
    }

    /**
     * Cannot instantiate.  Must use instance() method to get a handle to the object.
     */
    private TeamUtil() {
        // GET HANDLE TO UNIQUEID EJB
    }

    /**
     * Returns a unique ID to the caller. This is synchronized to prevent
     * multiple callers from getting the same ID back if they called getNextID() at the
     * same time.
     * to use: TeamUtil.instance().getNextID();
     *
     * @return long        next ID
     */
    public synchronized int getNextID() throws Exception {
        currentID++;
        if (currentID > max) {
            // oh, no! we've gone over our max... oops!
            throw new IndexOutOfBoundsException("Unique IDs surpassed int limit. ∴ SOL.");
        }
        return currentID;
    }

}
