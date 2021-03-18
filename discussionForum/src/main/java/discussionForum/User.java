package discussionForum;

import java.util.Collection;
import java.util.Map;

import static discussionForum.DatabaseController.db;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;

    public User(int userID, String firstName, String lastName, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public Collection<Course> getCourses() {
        return db.coursesToUser(this);
    }

    public int getUserID() {
        return userID;
    }


}