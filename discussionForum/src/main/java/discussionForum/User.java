import static discussionForum.DatabaseController.db;

public class User{
    private int userID;
    private String firstNam;
    private String lastName;
    private String email;
    private String password;

    public User(String firstName, String lastName, String email, String password){
        this.firstNam=FirstName;
        this.lastName=LastName;
        this.email=Email;
        this.password=Password;
    }

    public static String logIn(String Email, String Password){
        if (db.UserExist(Email, Password)){
            System.out.println("Du har logget inn");
        }
        else{
            System.out.println("Brukeren finnes ikke, eller email/passord er feil");
        }
    }
}