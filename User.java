import java.io.Serializable;
import org.mindrot.jbcrypt.BCrypt;


// Class for information storage on Users, used in AccountCLI class
public class User implements Serializable{
    private String username;
    private String password;
    private double balance = 0.0;

    // Constructor for username and passwword
    public User(String username, String password){
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12)); 
    }




    // Check password function
    public boolean checkPassword(String inputPassword) {
        return BCrypt.checkpw(inputPassword, this.password); // Bcrypt function checks the password then removes the salt in the password
    }

    // Getters for future use
    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}