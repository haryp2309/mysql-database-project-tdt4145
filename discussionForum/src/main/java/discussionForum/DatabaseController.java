package discussionForum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    User signIn(String email, String password);

    Collection<Course> coursesToUser(User user);

    Collection<Folder> getFolders(Course course);

    void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder, Collection<Tag> tags);

    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPostID);

    Collection<Tag> getAllTags();

    Collection<Tag> getAllTags(Thread thread);

    void viewPost(User user, Post post, LocalDateTime postedTimed);

    Collection<Thread> getThreads(Folder folder);

    Collection<DiscussionPost> getDiscussionPosts (Thread thread);

    /**
     * Finner frem alle comments som tilhører en bestemt discussionPost
     * @param discussionPost discussionPosten som man skal finne alle comments til
     * @return en liste med alle comments som tilhører discussionPost som ble tatt inn som parameter.
     */
    Collection<Comment> getComments (DiscussionPost discussionPost);

    /**
     * Søker etter et søkeord blant tråder (Thread-objekter) i et bestemt kurs, i databasen.
     * Søker i både tittelen (Title) og selve teksten til tråden (Content)
     * @param searchWord søkeordet
     * @param course kurset
     * @return en liste med tråder som inneholder søkeordet
     */
    Collection<Thread> search(String searchWord, Course course);

    /**
     * Henter ut statistikk om alle brukere.
     * Statistikken består av antall poster en bruker har lagt ut,
     * og antall poster en bruker har sett.
     * @param course kurset man ønsker statistikk om
     * @return en collection med rader som inneholder statistikk om hver person
     */
    Collection<Map<String, String>> getStatistics(Course course);

    /**
     * 
     * @param user
     * @param course
     * @return
     */
    boolean isUserInstructor(User user, Course course);

}