package discussionForum;

import java.util.Collection;

import static discussionForum.DatabaseController.db;

/***
 * The User-class represents a user-account in the application.
 * Every instance/objects of this class will be saved in the database with userID, firstName, lastName and email.
 */

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;

    /***
     * Constructor of the User-class
     * @param userID Unique ID of an User, mostly used to differentiate the user-objects in the database.
     * @param firstName Firstname of the user.
     * @param lastName  Lastname of the user.
     * @param email Email/username of the user
     */
    public User(int userID, String firstName, String lastName, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    /***
     * Checks if current user can be signed in. Confirms with the database if the user exists.
     * @param Email Email/username in the displayed field.
     * @param Password Password in the displayed field.
     * @return User-object or null if User does not exist in the database
     */
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

    /***
     * Getter-method to Email-attribute
     * @return String-instance: email of an user.
     */

    public String getEmail() {
        return email;
    }

    /***
     * Getter-method to firstName-attribute
     * @return String-instance: firstName of an user.
     */

    public String getFirstName() {
        return firstName;
    }

    /***
     * Getter-method to lastName-attribute
     * @return String-instance: lastName of an user.
     */

    public String getLastName() {
        return lastName;
    }

    /***
     * Getter-method that use imported attribute db from DatabaseController. Refers the logic to SQLController.
     * @return Courses-objects to current user.
     */

    public Collection<Course> getCourses() {
        return db.coursesToUser(this);
    }

    /***
     * Getter-method to userID-attribute
     * @return an integer that represents the userID of an User
     */

    public int getUserID() {
        return userID;
    }

    /***
     * Checks whether an User-object is a student account or instructor account. Using imported attribute db from DatabaseController. Refers the logic to SQLController.
     * @param course Associated course to User.
     * @return Boolean: True if user is instructor, false if user is a student-account.
     */

    public boolean isInstructor(Course course) {
        return db.isUserInstructor(this, course);
    }
}