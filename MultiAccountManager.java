import java.io.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;


// Class to manage multiple user accounts with registration and login
public class MultiAccountManager {
    // Name of the file used to save/load account data
    private static final String FILE_NAME = "accounts.ser";

    // Map to store username-password pairs
    // Uses user class, to allow for further information storage & expansion
    private Map<String, User> accounts;

    // Constructor loads existing accounts from the file if present
    public MultiAccountManager() {
        accounts = loadAccounts();
    }

    // Registers a new user account
    public boolean register(String username, String password) {
        if (accounts.containsKey(username)) {
            return false; // Username already taken
        }
        accounts.put(username, new User (username, password)); // Add to account map
        saveAccounts(); // Persist to disk
        return true;
    }

    // Authenticates login credentials
    public boolean login(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).checkPassword(password);
    }

    // Saves the current account map to disk using serialization
    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Loads account map from the serialized file if it exists
    @SuppressWarnings("unchecked")
    private Map<String, User> loadAccounts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>(); // No file yet, return empty map
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            return new HashMap<>(); // Return empty map on failure
        }
    }

    // Lists all registered usernames (for testing/demo purposes)
    public void listUsers() {
        System.out.println("Registered users: " + accounts.keySet());
    }

    // New added method in order to list users in the GUI
    public String getUsernames(){
        return accounts.keySet().toString();
    }

    
    // Lists all hashed passwords for testing
    public void listPasswords() {
        System.out.println("Passwords:");
        for (Map.Entry<String, User> entry : accounts.entrySet()) {
            System.out.println("Username: " + entry.getKey() + " -> Hashed Password: " + entry.getValue().getPassword());
        }
    }


    // New added method to list Hashed passwords in the GUI
    public String getHashedPasswords(){
        StringBuilder pass = new StringBuilder();
        for (Map.Entry<String, User> entry : accounts.entrySet()) {
            pass.append("Username: " + entry.getKey() + " -> Hashed Password: " + entry.getValue().getPassword()).append("\n");
        }
        return pass.toString();

    }

}
