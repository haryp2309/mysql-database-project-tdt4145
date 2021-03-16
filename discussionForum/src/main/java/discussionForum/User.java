package discussionForum;
import static discussionForum.DatabaseController.db;

public class User{
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(int userID, String firstName, String lastName, String email, String password){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static User signIn(String Email, String Password) {
        User user = db.signIn(Email, Password);
        if (user != null) {
            System.out.println("Du har logget inn");
            return user;
        } else {
            System.out.println("Brukeren finnes ikke, eller email/passord er feil");
            return null;
        }
    }

    public static User createUser(String firstName, String lastName, String email, String password) {
        return db.createUser(firstName, lastName, email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID (int userID){
        this.userID = userID;
    }

}