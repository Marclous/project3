 /**
 * 
 * The LoginStats class is the main program that processes the log data
 * and allows users to query login sessions.
 *
 * @author Puyuan Song
 */
package project3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class LoginStats {
    private RecordList records = new RecordList();
    /**
     * Main method that drives the program. It handles opening the log file,
     * reading the data, and interacting with the user.
     * 
     * @param args Command-line arguments. 
     */
    public static void main(String[] args) {
        LoginStats loginStats = new LoginStats();

        // Ensure the program is run with a valid command-line argument
        if (args.length != 1) {
            System.err.println("Usage Error: the program expects a file name as an argument.");
            return;
        }

        String fileName = args[0];

        // Try to read the log file and process it
        try {
            loginStats.readLogFile(fileName);
            loginStats.handleUserQueries();
        } catch (IOException e) {
            System.err.println("Error: Unable to open or read the file: " + fileName);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid input data in file: " + fileName);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
        /**
     * Reads the log file, parses each line into Record objects, and stores them
     * in the RecordList.
     * 
     * @param fileName The name of the log file to read.
     * @throws IOException If an error occurs while reading the file.
     */
    public void readLogFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                // Split the line into its components
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid record format: " + line);
                }

                int terminal = Integer.parseInt(parts[0].replace("-", ""));
                boolean isLogin = !parts[0].startsWith("-");
                String username = parts[2];
                Date time = new Date(Long.parseLong(parts[1]));

                // Create a Record object and add it to the list
                Record record = new Record(terminal, isLogin, username, time);
                records.add(record);

            } catch (IllegalArgumentException e) {
                System.err.println("Error: Failed to parse record: " + line + " (" + e.getMessage() + ")");
            }
        }
        reader.close();
    
    }
    /**
     * Handles user input in a loop, allowing the user to query the first or last
     * session of a user, or quit the program.
     */
    public void handleUserQueries() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Login Stats!\n");
        System.out.println("Available commands:");
        System.out.println("  first USERNAME   - retrieves first login session for the USER");
        System.out.println("  last USERNAME    - retrieves last login session for the USER");
        System.out.println("  quit             - terminates this program\n");
        

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Exiting the program.");
                break;
            }

            // Process the user query
            try {
                processQuery(input);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
    /**
     * Processes the user query by extracting the command and username.
     * Valid commands are "first USERNAME" and "last USERNAME".
     * 
     * @param input The user input string.
     * @throws IllegalArgumentException If the command or input is invalid.
     * @throws NoSuchElementException If no session is found for the specified user.
     */
    private void processQuery(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("This is not a valid query. Try again.");
        }

        String command = parts[0];
        String username = parts[1];

        // Process "first" or "last" queries
        if (command.equalsIgnoreCase("first")) {
            Session firstSession = records.getFirstSession(username);
            System.out.println(firstSession);
            System.out.println("");
        } else if (command.equalsIgnoreCase("last")) {
            Session lastSession = records.getLastSession(username);
            System.out.println(lastSession);
            System.out.println("");
        } else {
            throw new IllegalArgumentException("This is not a valid query. Try again.");
        }
    }
}
