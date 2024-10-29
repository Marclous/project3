 /**
 * The RecordList class stores Record objects and allows retrieval
 * of the first and last session for a specific user.
 *
 * @author Puyuan Song
 */
package project3;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class RecordList extends ArrayList<Record> {

    // Default constructor to create an empty RecordList object
    public RecordList() {
        super();
    }

    /**
     * Retrieves the first session for the specified user.
     * The first session is defined as the session with the earliest login time.
     * If no matching user is found, throws NoSuchElementException.
     * 
     * @param user The username to search for.
     * @return The first Session object for the user.
     * @throws IllegalArgumentException If the user is null or empty.
     * @throws NoSuchElementException If no matching session for the user is found.
     */
    public Session getFirstSession(String user) {
        // Validate the input
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        Record loginRecord = null;
        Record logoutRecord = null;

        // Iterate through the list of records to find the first login and logout for the user
        for (Record record : this) {
            if (record.getUsername().equals(user)) {
                if (record.isLogin()) {
                    // If we haven't found any previous login for this user, this is the earliest one.
                    if (loginRecord == null || record.getTime().before(loginRecord.getTime())) {
                        loginRecord = record;
                        logoutRecord = null; // Reset the logoutRecord to search for a new matching logout
                    }
                } else if (record.isLogout() && loginRecord != null && record.getTerminal() == loginRecord.getTerminal()) {
                    // If we find a logout for the same terminal, pair it with the current login
                    logoutRecord = record;
                    break; 
                }else if (record.isLogout() && loginRecord == null) {
                    // Logout found without a prior matching login, this is an error
                    throw new IllegalArgumentException("Found logout without a matching login for user: " + user);
                }
            }
        }

        // If we found a login record but no corresponding session, the user may still be logged in
        if (loginRecord != null) {
            return new Session(loginRecord, logoutRecord);
        }

        // If no matching user was found, throw an exception
        throw new NoSuchElementException("No matching session found for user: " + user);
    }

    /**
     * Retrieves the last session for the specified user.
     * The last session is defined as the session with the latest login time.
     * If no matching user is found, throws NoSuchElementException.
     * 
     * @param user The username to search for.
     * @return The last Session object for the user.
     * @throws IllegalArgumentException If the user is null or empty.
     * @throws NoSuchElementException If no matching session for the user is found.
     */
    public Session getLastSession(String user) {
        // Validate the input
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        Record loginRecord = null;
        Record logoutRecord = null;

        // Iterate through the list of records to find the last login and logout for the user
        for (Record record : this) {
            if (record.getUsername().equals(user)) {
                if (record.isLogin()) {
                    // This is a newer login, update the login record
                    if (loginRecord == null || record.getTime().after(loginRecord.getTime())) {
                        loginRecord = record;
                        logoutRecord = null; // Reset logout to search for the new matching logout
                    }
                } else if (record.isLogout() && loginRecord != null && record.getTerminal() == loginRecord.getTerminal()) {
                    // Pair the login with this logout
                    logoutRecord = record;
                }else if (record.isLogout() && loginRecord == null) {
                    // Logout found without a prior matching login, this is an error
                    throw new IllegalArgumentException("Found logout without a matching login for user: " + user);
                }
            }
        }

        // If we found a login record but no corresponding session, the user may still be logged in
        if (loginRecord != null) {
            return new Session(loginRecord, logoutRecord);
        }

        // If no matching user was found, throw an exception
        throw new NoSuchElementException("No matching session found for user: " + user);
    }
}

