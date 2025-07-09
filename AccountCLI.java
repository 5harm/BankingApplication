import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

/**
 * AccountCLI handles all user interaction through a simple command-line interface.
 * It delegates registration and login operations to the MultiAccountManager class.
 */
public class AccountCLI {
    private MultiAccountManager manager; // Handles account logic
    private Scanner scanner;             // Reads user input

    // Constructor initializes the manager and scanner
    public AccountCLI() {
        manager = new MultiAccountManager();
        scanner = new Scanner(System.in);
    }

    // Runs the interactive CLI loop
    public void run() {
        while (true) {
            displayMenu();             // Show options
            int option = getUserOption(); // Read user selection

            // Execute appropriate action
            switch (option) {
                case 1 -> handleRegister(); // Register user
                case 2 -> handleLogin();    // Login user
                case 3 -> manager.listUsers(); // List all usernames
                case 4 -> {
                    System.out.println("Goodbye!");
                    return; // Exit the loop/program
                }
                case 5 -> {
                    manager.listPasswords();
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Prints the menu options
    private void displayMenu() {
        System.out.println("\n1. Register\n2. Login\n3. List Users\n4. Exit");
        System.out.print("Choose an option: ");
    }

    // Gets the user's chosen menu option, validating input
    private int getUserOption() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next(); // Discard invalid input
        }
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline
        return option;
    }

    // Handles the user registration process
    private void handleRegister() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine(); // Use the Bcrypt function to hash and then salt the password

        if (manager.register(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists.");
        }
    }

    // Handles the login process
    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (manager.login(username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
        
}