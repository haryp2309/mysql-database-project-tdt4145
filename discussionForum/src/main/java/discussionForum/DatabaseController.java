package discussionForum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    boolean isEmailUsed(String email);

    User signIn(String email, String password);

    void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder, Collection<Tag> tags);

    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPostID);

    public Collection<Tag> getTags();

    public Collection<Tag> getTags(Thread thread);

    void likePost(User user, Post post, LocalDateTime postedTime);

    void viewPost(User user, Post post, LocalDateTime postedTimed);

    void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads, Course course);

    User createUser(String firstName, String lastName, String email, String password);

    Collection<Thread> getThreads(Folder folder);

    Collection<DiscussionPost> getDiscussionPosts (Thread thread);

    Collection<Comment> getComments (DiscussionPost discussionPost);

    //"A student searches for posts with a specific keyword “WAL”. 
    //The return value of this should be a list of ids of posts matching the keyword."
    Collection<Thread> search(String searchWord, Course course);

    //An instructor views statistics for users and how many post they have read and how many
    //they have created. These should be sorted on highest read posting numbers. The output is
    //“user name, number of posts read, number of posts created”. You don’t need to order by 
    //posts created, but the number should be displayed. The result should also include users
    //which have not read or created posts.
    Collection<Map<String, String>> getStatistics(User user, Course course);

    public boolean isUserInstructor(User user, Course course);

    public Collection<Course> coursesToUser(User user);

    public Collection<Folder> getFolders(Course course);



}