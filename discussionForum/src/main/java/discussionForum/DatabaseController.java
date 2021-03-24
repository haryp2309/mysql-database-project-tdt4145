package discussionForum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    User createUser(String firstName, String lastName, String email, String password);

    User signIn(String email, String password);

    boolean isEmailUsed(String email);

    Collection<Course> coursesToUser(User user);

    void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads, Course course);

    Collection<Folder> getFolders(Course course);

    void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder, Collection<Tag> tags);

    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPostID);

    Collection<Tag> getTags();

    Collection<Tag> getTags(Thread thread);

    void likePost(User user, Post post, LocalDateTime postedTime);

    void viewPost(User user, Post post, LocalDateTime postedTimed);

    Collection<Thread> getThreads(Folder folder);

    Collection<DiscussionPost> getDiscussionPosts (Thread thread);

    Collection<Comment> getComments (DiscussionPost discussionPost);

    Collection<Thread> search(String searchWord, Course course);

    Collection<Map<String, String>> getStatistics(User user);



}