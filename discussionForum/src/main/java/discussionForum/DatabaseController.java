import java.time.LocalDate;

public interface DatabaseController{

public static DatabaseController db = null;

public boolean isEmailUsed(String Email);
public boolean userExist(String Email, String Password);
public void postThread(String Content, String Author, LocalDate PostedTime);

}