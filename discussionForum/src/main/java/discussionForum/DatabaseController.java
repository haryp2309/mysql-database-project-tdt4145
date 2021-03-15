package discussionForum;

import java.time.LocalDateTime;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    boolean isEmailUsed(String email);

    User signIn(String email, String password);

    void postThread(String title, String content, User author, LocalDateTime postedTime, int folderId);

    User createUser(String firstName, String lastName, String email, String password);

}