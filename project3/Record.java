 /**
 * This class should represent the individual records from the input log file.
 *
 * @author Puyuan Song
 */
package project3;
import java.util.Date;

public class Record {

    private int terminal;
    private boolean login;
    private String username;
    private Date time;
    /**
     * Constructs a new Record object with terminal number, login status, username, and time
     * @param terminal terminal number that the user used to login
     * @param login the user's login status
     * @param username  user's name
     * @param time login or logout time
     */
    public Record(int terminal, boolean login, String username, Date time) {
        if (terminal <= 0) {
            throw new IllegalArgumentException("Terminal number must be a positive integer.");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null.");
        }
        
        this.terminal = terminal;
        this.login = login;
        this.username = username;
        this.time = time;
    }

    // Getter methods (Accessors)

    // Returns the terminal number
    public int getTerminal() {
        return terminal;
    }

    
    /**
     * Returns whether this record represents a login
     * @return user login status
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * Returns whether this record represents a logout (just the negation of login)
     * @return user logout status
     */
    public boolean isLogout() {
        return !login;
    }

     
    /**
     * Returns the username associated with this record
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Returns the time associated with this record
     * @return time
     */
    public Date getTime() {
        return time;
    }
}
