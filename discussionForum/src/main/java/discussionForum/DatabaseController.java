package discussionForum;

import java.time.LocalDateTime;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    boolean isEmailUsed(String email);

    User signIn(String email, String password);

    void postThread(String title, String content, User author, LocalDateTime postedTime, int folderId);

    void postDiscussionPost(String content, User author, LocalDateTime postedTime, int threadID);

    void postComment(String content, User author, LocalDateTime postedTime, int discussionPostID);

    void tag(Thread thread, Tag tag);

    void likePost(Post post, LocalDateTime postedTime);

    void viewPost(Post post, LocalDateTime postedTimed);

    void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads);

    User createUser(String firstName, String lastName, String email, String password);

    int likedCount(Post post);

    int viewedCount(Post post);
}