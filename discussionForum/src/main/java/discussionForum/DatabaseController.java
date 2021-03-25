package discussionForum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface DatabaseController {

    DatabaseController db = new SQLController();

    /**
     * Sjekker om brukeren kan bli logget inn. Bekrefter med databasen om bruker faktisk eksisterer.
     * @param email email eller brukernavnet til bruker-objektet som blir gitt av tekstfeltet i grensesnittet.
     * @param password passordet som blir gitt av tekstfeltet i grensesnittet.
     * @return Et bruker-objekt som finnes i databasen eller null hvis denne brukeren ikke er registrert.
     */
    User signIn(String email, String password);

    /**
     * Finner emner som en bruker er meldt opp i diskusjonsforumet.
     * @param user brukeren
     * @return En "Collection" av emner som bruker er meldt opp i.
     */
    Collection<Course> coursesToUser(User user);

    /**
     * En getter-metode som henter ut alle Folders i et gitt emne.
     * @param course emnet med Folder-objektene
     * @return En "Collection" av Folder-objekter som tilhører course
     */

    Collection<Folder> getFolders(Course course);

    /**
     * Oppretter en Thread-instans som skal legges inn i databasen. Denne skal bli opprettet i et bestemt kurs, i databasen.
     * Legges til i databasen etter opprettelse.
     * @param title Tittelen til Thread-en som er skrevet inn i tekst-feltet på brukergrensesnittet.
     * @param content Innholdet i Thread-en
     * @param author Brukeren som opprettet Thread-en
     * @param postedTime Tidspunktet Thread-en ble opprettet.
     * @param folder Mappen som Thread-en skal bli plassert i.
     * @param tags En eller flere tag-objekter som tilhører en Thread.
     */

    void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder, Collection<Tag> tags);

    /**
     * Oppretter en DiscussionPost-instans i tilhørende Thread som igjen blir opprettet i et bestemt kurs, i databasen.
     * Legges til i databasen etter opprettelse.
     * @param content Innholdet i DiscussionPost
     * @param author Brukeren som opprettet DiscussionPost.
     * @param postedTime Tidspunktet DiscussionPost ble opprettet
     * @param thread Tilhørende Thread
     */
    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    /**
     * Oppretter en Comment-instans i en DiscussionPost. Denne kommer igjen til å tilhøre en Thread i et bestemt kurs, i databasen.
     * Legges til i databasen etter opprettelse.
     * @param content Innholdet i Comment
     * @param author Brukeren som opprettet Comment
     * @param postedTime Tidspunktet Comment ble opprettet.
     * @param discussionPost Tilhørende DiscussionPost
     */

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPost);

    /**
     * En getter-metode som skal hente alle forhåndsdefinerte tag-objekter som allerede ligger i databasen.
     * @return En "Collection" av alle disse tag-objektene som er definert i databasen
     */

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