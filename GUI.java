import java.awt.*;
import javax.swing.*;

/**
 * Barebones GUI for account management using Swing, mostly JFrame Jpanel JTextField JPasswordField and JTextArea.
 */
public class GUI extends JFrame {
    private MultiAccountManager manager;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;

    public GUI() { // Starts the method GUI 
        manager = new MultiAccountManager();
        createUI();
    }

    private void createUI() {//Visual formatting of the GUI
        setTitle("Banking App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center window

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        add(panel);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Credentials"));

        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        JButton listUsersButton = new JButton("List Users");
        JButton listPassButton = new JButton("List Pass");

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(listUsersButton);
        buttonPanel.add(listPassButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        panel.add(scrollPane, BorderLayout.SOUTH);

        // Action listeners
        registerButton.addActionListener(e -> handleRegister());
        loginButton.addActionListener(e -> handleLogin());
        listUsersButton.addActionListener(e -> handleListUsers());
        listPassButton.addActionListener(e -> handleListPass());

        setVisible(true);
    }

    /**This here is for testing only
     *  probably delete later 
     * Shows hashed passwords
     */
    private void handleListPass(){
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals("123321")) {
            appendOutput(manager.getHashedPasswords());
            return;
        }
        else {
            appendOutput("Wrong code");
        }
    }
    //Handles the registering by comparing username and passwords
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            appendOutput("Please enter both username and password.");
            return;
        }

        if (manager.register(username, password)) {
            appendOutput("Registration successful for: " + username);
        } else {
            appendOutput("Username already exists: " + username);
        }
    }
    //Handles the login by comparing
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (manager.login(username, password)) {
            appendOutput("Login successful! Welcome, " + username);
        } else {
            appendOutput("Login failed. Invalid credentials.");
        }
    }

    //Shows a list of users in the file, simply outputs them
    private void handleListUsers() {
        appendOutput("Registered users: " + manager.getUsernames());
    }
    /**
     * This is pretty helpful
     * Outputs messages in the GUI
     * Example: outputs username lists, or wrong credential messages
     */
    public void appendOutput(String message) {
        outputArea.append(message + "\n");
    }
}
