/**
 * This class should represent a single login session.
 *
 * @author Puyuan Song
 */
package project3;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Session {

    // Private fields
    private Record login;
    private Record logout;

    /**
     * Constructs a Session object based on a login and logout record.
     * Ensures that the usernames and terminal numbers match between login and logout.
     * If the logout record is null, the session is considered active (user still logged in).
     * 
     * @param login  The login record (cannot be null).
     * @param logout The logout record (can be null, indicating an active session).
     * @throws IllegalArgumentException If the usernames or terminal numbers don't match, 
     *                                  or if the login time is after the logout time.
     */
    public Session(Record login, Record logout) {
        if (login == null) {
            throw new IllegalArgumentException("Login record cannot be null.");
        }
        if (!login.isLogin() ) {
            throw new IllegalArgumentException("Invalid Login status");
        }
        if (logout != null) {
            if (!logout.isLogout()) {
                throw new IllegalArgumentException("Invalid Logout status");
            }
            if (!login.getUsername().equals(logout.getUsername())) {
                throw new IllegalArgumentException("Usernames must match for a valid session.");
            }
            
            if (login.getTerminal() != logout.getTerminal()) {
                throw new IllegalArgumentException("Terminal numbers must match for a valid session.");
            }

            if (login.getTime().after(logout.getTime())) {
                throw new IllegalArgumentException("Login time cannot be after logout time.");
            }
        }
        
        this.login = login;
        this.logout = logout;
    }

    // Getter methods (Accessors)
    /**
     * Returns the terminal.
     * @return The terminal number.
     */
    public int getTerminal() {
        return login.getTerminal();
    }
    /**
     * Returns the login time.
     * @return the login time.
     */
    public Date getLoginTime() {
        return login.getTime();
    }
    /**
     * Returns the logout time.
     * @return the logout time.
     */
    public Date getLogoutTime() {
        return (logout != null) ? logout.getTime() : null;
    }
    /**
     * Returns the username time.
     * @return the username time.
     */
    public String getUsername() {
        return login.getUsername();
    }

    /**
     * Gets the duration of the session in milliseconds.
     * If the session is still active (i.e., no logout), returns -1.
     * 
     * @return The duration in milliseconds, or -1 if the session is still active.
     */
    public long getDuration() {
        if (logout == null) {
            return -1; // Active session
        }
        return logout.getTime().getTime() - login.getTime().getTime();
    }

   /**
     * Utility method to format the session duration into a human-readable string.
     * The format includes days, hours, minutes, and seconds.
     * 
     * @param durationInMillis The duration in milliseconds.
     * @return A formatted string representing the duration in "days, hours, minutes, seconds".
     */
    private String formatDuration(long durationInMillis) {
        long days = TimeUnit.MILLISECONDS.toDays(durationInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis) % 60;
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    
   /**
     * Overrides the toString() method to return session details in the required format.
     * If the session is still active, "active session" is displayed as the duration
     * and "still logged in" is displayed as the logout time.
     * 
     * @return A formatted string representing the session details.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(login.getUsername()).append(", terminal ").append(login.getTerminal()).append(", ");
        
        // If the session is active, display "active session" and no logout time.
        if (logout == null) {
            sb.append("duration active session\n");
            sb.append("  logged in: ").append(login.getTime().toString()).append("\n");
            sb.append("  logged out: still logged in");
        } else {
            // Otherwise, display the formatted duration and logout time.
            sb.append("duration ").append(formatDuration(getDuration())).append("\n");
            sb.append("  logged in: ").append(login.getTime().toString()).append("\n");
            sb.append("  logged out: ").append(logout.getTime().toString());
        }
        return sb.toString();
    }
}

