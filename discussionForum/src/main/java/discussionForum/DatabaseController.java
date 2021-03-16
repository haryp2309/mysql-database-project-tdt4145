package discussionForum;

import java.time.LocalDateTime;
import java.util.Collection;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    boolean isEmailUsed(String email);

    User signIn(String email, String password);

    void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder);

    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPostID);

    void tag(Thread thread, Tag tag);

    void likePost(User user, Post post, LocalDateTime postedTime);

    void viewPost(User user, Post post, LocalDateTime postedTimed);

    void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads);

    User createUser(String firstName, String lastName, String email, String password);

    int likedCount(Post post);

    int viewedCount(Post post);

}