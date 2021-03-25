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
     * @param thread Thread-en discussionPost tilhører
     */
    void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread);

    /**
     * Oppretter en Comment-instans i en DiscussionPost. Denne kommer igjen til å tilhøre en Thread i et bestemt kurs, i databasen.
     * Legges til i databasen etter opprettelse.
     * @param content Innholdet i Comment
     * @param author Brukeren som opprettet Comment
     * @param postedTime Tidspunktet Comment ble opprettet.
     * @param discussionPost DiscussionPost-en comment tilhører
     */

    void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPost);

    /**
     * En getter-metode som skal hente alle forhåndsdefinerte tag-objekter som allerede ligger i databasen.
     * @return En "Collection" av alle disse tag-objektene som er definert i databasen
     */

    /**
     * Henter ut alle tags som eksisterer (i databasen).
     * @return en liste med alle Tag som eksisterer.
     */
     Collection<Tag> getAllTags();

    /**
     * Henter ut alle tags en Thread har.
     * @param thread Threaden man ønsker å hente tags fra.
     * @return en liste med alle Tag Thread-en har.
     */
    Collection<Tag> getAllTags(Thread thread);

    /**
     * Registrerer når en bruker har sett en post.
     * @param user brukeren som ser posten.
     * @param post posten som blir sett.
     * @param viewedTime tidspunktet som brukeren så posten.
     */
    void viewPost(User user, Post post, LocalDateTime viewedTime);

    /**
     * Finner frem alle Threads som tilhører en bestemt Folder.
     * @param folder Folder som man skal finne alle Threads til.
     * @return en liste med alle Threads som tilhører Folder som ble tatt inn som parameter.
     */
    Collection<Thread> getThreads(Folder folder);

    /**
     * Finner frem alle DiscussionPost som tilhører en bestemt Thread.
     * @param thread Thread som man skal finne alle DiscussionPost til.
     * @return en liste med alle DiscussionPost som tilhører Thread som ble tatt inn som parameter.
     */
    Collection<DiscussionPost> getDiscussionPosts (Thread thread);

    /**
     * Finner frem alle Comments som tilhører en bestemt DiscussionPost.
     * @param discussionPost DiscussionPosten som man skal finne alle comments til.
     * @return en liste med alle Comments som tilhører DiscussionPost som ble tatt inn som parameter.
     */
    Collection<Comment> getComments (DiscussionPost discussionPost);

    /**
     * Søker etter et søkeord blant Threads i et bestemt Course, i databasen.
     * Søker i både Title og selve teksten til tråden (Content).
     * @param searchWord søkeordet.
     * @param course kurset man ønsker å søke i.
     * @return en liste med tråder som inneholder søkeordet.
     */
    Collection<Thread> search(String searchWord, Course course);

    /**
     * Henter ut statistikk om alle brukere.
     * Statistikken består av antall poster en bruker har lagt ut,
     * og antall poster en bruker har sett.
     * @param course kurset man ønsker statistikk om.
     * @return en collection med rader som inneholder statistikk om hver person.
     */
    Collection<Map<String, String>> getStatistics(Course course);

    /**
     * Sjekker om en bruker er instruktør eller student. Brukes for å gi riktig rettigheter.
     * @param user brukeren man skal sjekke.
     * @param course kurset men sjekker rollen i.
     * @return True hvis brukeren er instruktør i kurset, False hvis ikke.
     */
    boolean isUserInstructor(User user, Course course);

}