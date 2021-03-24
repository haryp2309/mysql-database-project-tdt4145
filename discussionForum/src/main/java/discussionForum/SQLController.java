package discussionForum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SQLController extends MySQLConn implements DatabaseController {

    //Navn til tabellene i databasen i form av konstanter
    public static final String TABLE_USER = "User";
    public static final String TABLE_POST = "Post";
    public static final String TABLE_THREAD = "Thread";
    public static final String TABLE_DISCUSSION = "Discussion";
    public static final String TABLE_COMMENT = "Comment";
    public static final String TABLE_TAG_ON_THREAD = "TagOnThread";
    public static final String TABLE_LIKEDBY = "LikedBy";
    public static final String TABLE_COURSE = "Course";
    public static final String TABLE_USERINCOURSE = "UserInCourse";
    public static final String TABLE_VIEWEDBY = "ViewedBy";
    public static final String TABLE_FOLDER = "Folder";
    public static final String TABLE_ROOT_FOLDER = "rootfolder";
    public static final String TABLE_SUB_FOLDER = "subfolder";
    public static final String TABLE_ROOTFOLDER = "RootFolder";
    public static final String TABLE_TAG = "Tag";


    public SQLController() {
        this.connect();
    }

    private Collection<Map<String, String>> select(Collection<String> attributes, String table, String additionalSQLStatements) {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
            String query = "SELECT ";
            query += attributes.stream()
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("*");
            query += " ";
            query += "FROM ";
            query += table;
            query += " ";
            query += additionalSQLStatements;
        return customSelect(query, attributes);

    }

    private String insert(Map<String, String> values, String table) {
        if (values.isEmpty()) throw new IllegalArgumentException("Attributes can't be empty");
        String columns = values.keySet().stream()
                .map(column -> "`" + column + "`")
                .reduce((a, b) -> a + ", " + b)
                .get();
        String valuesString = values.keySet().stream()
                .map(values::get)
                .map(value -> {
                    try {
                        Integer.parseInt(value);
                        return value;
                    } catch (NumberFormatException e) {
                        return "\"" + value + "\"";
                    }
                })
                .reduce((a, b) -> a + ", " + b)
                .get();
        String query = "INSERT INTO " + table + " (" + columns + ") VALUES " + " (" + valuesString + ") ";
        System.out.println(query);

        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getString("GENERATED_KEY");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Collection<String> getFolderIdsInCourse(Course course) {
        Collection<Folder> folders = getFolders(course);
        Collection<String> folderIds = new ArrayList<>();
        Stack<Folder> stack = new Stack<>();
        stack.addAll(folders);
        while (!stack.isEmpty()) {
            Folder currentFolder = stack.pop();
            folderIds.add(Integer.toString(currentFolder.getFolderID()));
            stack.addAll(currentFolder.getSubfolders());
        }
        return folderIds;
    }

    //search søker etter et søkeord blant tråder (Thread-objekter) i et bestemt kurs, i databasen.
    //Metoden søker etter ordet i både tittelen (Title) og selve teksten til tråden (Content)
    //Metoden returnerer en liste med tråder som inneholder søkeordet.
    @Override
    public Collection<Thread> search(String searchWord, Course course) {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("PostID");
        attributes.add("Title");
        attributes.add("Content");
        attributes.add("UserID");
        attributes.add("FirstName");
        attributes.add("LastName");
        attributes.add("Email");

        Collection<String> folderIds = getFolderIdsInCourse(course);

        String seperatedFolderIDs = folderIds.stream()
                .reduce((id1, id2) -> id1+", "+id2)
                .orElse(null);
        if (seperatedFolderIDs == null) {
            return new ArrayList<>();
        }
        String query = "NATURAL JOIN ";
        query += TABLE_THREAD;
        query += " INNER JOIN ";
        query += TABLE_USER;
        query += " ON UserID = AuthorID WHERE FolderID IN (";
        query += seperatedFolderIDs;
        query += ") AND PostType = \"Thread\" AND (Title LIKE \"%";
        query += searchWord;
        query += "%\" OR Content LIKE \"%";
        query += searchWord;
        query += "%\")";
        Collection<Map<String, String>> result = select(attributes, TABLE_POST, query);
        return result.stream().map(row -> {
            int postId = Integer.parseInt(row.get("PostID"));
            String title = row.get("Title");
            String content = row.get("Content");

            String firstName = row.get("FirstName");
            String lastName = row.get("LastName");
            String email = row.get("Email");
            int userId = Integer.parseInt(row.get("UserID"));
            User user = new User(userId, firstName, lastName, email);

            return new Thread(postId, title, content, user);
        }).collect(Collectors.toList());
    }

    //Metoden getStatistics finner frem statistikk om alle brukere.
    //Statistikken består av tall på antall poster en bruker har lagt ut,
    //og tall på antall poster en bruker har sett.
    //
    //HVA RETURNERER METODEN HJELP
    //
    @Override
    public Collection<Map<String, String>> getStatistics(User user, Course course) {
        String seperatedFolderIDs = getFolderIdsInCourse(course).stream()
                .reduce((id1, id2) -> id1+", "+id2)
                .orElse(null);

        String query1 = "(SELECT ";
        query1 += "UserID, Email, COUNT(PostedTime) AS NoOfPostCreated";
        query1 += " FROM ";
        query1 += TABLE_POST;
        query1 += " NATURAL JOIN ";
        query1 += TABLE_THREAD;
        query1 += " RIGHT OUTER JOIN ";
        query1 += TABLE_USER;
        query1 += " ON ";
        query1 += TABLE_USER + "." + "UserID";
        query1 += " = ";
        query1 += TABLE_POST + "." + "AuthorID";
        query1 += " NATURAL JOIN ";
        query1 += TABLE_USERINCOURSE;
        query1 += " WHERE (FolderID IN (";
        query1 += seperatedFolderIDs;
        query1 += ") OR FolderID IS Null) AND CourseID = ";
        query1 += course.getCourseID();
        query1 += " GROUP BY ";
        query1 += "UserID";
        query1 += " ) AS PostedUser";

        String query2 = "(SELECT ";
        query2 += TABLE_USER+".UserID, COUNT(ViewedTime) AS NoOfPostViewed";
        query2 += " FROM ";
        query2 += TABLE_VIEWEDBY;
        query2 += " NATURAL JOIN ";
        query2 += TABLE_THREAD;
        query2 += " RIGHT OUTER JOIN ";
        query2 += TABLE_USER;
        query2 += " ON ";
        query2 += TABLE_USER + "." + "UserID";
        query2 += " = ";
        query2 += TABLE_VIEWEDBY + "." + "UserID";
        query2 += " INNER JOIN ";
        query2 += TABLE_USERINCOURSE;
        query2 += " ON ";
        query2 += TABLE_USER;
        query2 += ".UserID = ";
        query2 += TABLE_USERINCOURSE;
        query2 += ".UserID";
        query2 += " WHERE (FolderID IN (";
        query2 += seperatedFolderIDs;
        query2 += ") OR FolderID IS Null) AND CourseID = ";
        query2 += course.getCourseID();
        query2 += " GROUP BY ";
        query2 += "UserID";
        query2 += " ) AS ViewedUser";

        String query = "SELECT ";
        query += "Email, NoOfPostCreated, NoOfPostViewed";
        query += " FROM ";
        query += query1;
        query += " INNER JOIN ";
        query += query2;
        query += " ON ";
        query += "PostedUser.UserID = ViewedUser.UserID ";
        query += "ORDER BY NoOfPostViewed DESC";


        Collection<String> attributes = new ArrayList(Arrays.asList("Email", "NoOfPostCreated", "NoOfPostViewed"));

        return customSelect(query, attributes);


    }

    private Collection<Map<String, String>> customSelect(String query, Collection<String> attributes) {
        System.out.println(query);
        Collection<Map<String, String>> result = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            while (rset.next()) {
                if (attributes.isEmpty()) {
                    int length = rset.getMetaData().getColumnCount();
                    for (int i = 1; i <= length; i++) {
                        attributes.add(rset.getMetaData().getColumnName(i));
                    }
                }
                Map<String, String> map = new HashMap<>();
                attributes.forEach(attribute -> {
                    try {
                        String cell = rset.getString(attribute);
                        map.put(attribute, cell);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                result.add(map);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    @Override
    public User signIn(String email, String password) {
        Collection<Map<String, String>> result = select(null, TABLE_USER, "WHERE " + "Email" + " = \"" + email + "\" AND " + "Password" + " = \"" + password + "\"");
        if (result.size() > 1) {
            throw new IllegalStateException("Duplicate users in databse");
        } else if (result.size() < 1) {
            return null;
        } else {
            Map<String, String> userMap = result.iterator().next();
            int id = Integer.parseInt(userMap.get("UserID"));
            String firstName = userMap.get("FirstName");
            String lastName = userMap.get("LastName");
            email = userMap.get("Email");
            return new User(id, firstName, lastName, email);
        }
    }

    @Override
    public boolean isUserInstructor(User user, Course course){
        Collection<String> attributes = new ArrayList<>();
        attributes.add("IsInstructor");
        Collection<Map<String, String>> result = select(attributes, TABLE_USERINCOURSE, "WHERE("+"UserID"+" = "+user.getUserID()+" AND "+ "CourseID" +" = "+course.getCourseID()+")");
        return result.stream()
                .map(row -> Integer.parseInt(row.get("IsInstructor")) == 1)
                .findFirst()
                .orElse(false);
    }

    private int post(String content, User author, LocalDateTime postedTime) {
        HashMap<String, String> values = new HashMap<>();
        values.put("Content", content);
        values.put("AuthorID", Integer.toString(author.getUserID()));
        values.put("PostedTime", localDateTimeConverter(postedTime));
        values.put("PostType", "Thread");
        return Integer.parseInt(Objects.requireNonNull(this.insert(values, TABLE_POST)));
    }

    @Override
    public void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder, Collection<Tag> tags) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put("PostID", Integer.toString(postId));
        values.put("Title", title);
        values.put("FolderID", Integer.toString(folder.getFolderID()));
        insert(values, TABLE_THREAD);
        tags.forEach(specifiedTag -> {
            tag(postId, specifiedTag);
        });

    }

    @Override
    public void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put("PostID", Integer.toString(postId));
        values.put("ThreadID", Integer.toString(thread.getPostID()));
        insert(values, TABLE_DISCUSSION);
    }

    @Override
    public void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPost) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put("PostID", Integer.toString(postId));
        values.put("DiscussionID", Integer.toString(discussionPost.getPostID()));
        insert(values, TABLE_COMMENT);
    }


    private void tag(int threadId, Tag tag) {
        Map<String, String> values = new HashMap<>();
        values.put("TagName", tag.getValue());
        values.put("ThreadID", Integer.toString(threadId));
        insert(values, TABLE_TAG_ON_THREAD);
    }

    public Collection<Thread> getThreads(Folder folder) {

        Collection<String> attributes = new ArrayList<>();
        attributes.add("PostID");
        attributes.add("Title");
        attributes.add("Content");
        attributes.add("UserID");
        attributes.add("FirstName");
        attributes.add("LastName");
        attributes.add("Email");
        String additional = "NATURAL JOIN "
                + TABLE_THREAD
                + " INNER JOIN "
                + TABLE_USER
                + " ON UserID = AuthorID "
                + " WHERE "
                + "FolderID"
                + " = "
                + folder.getFolderID();
        Collection<Map<String, String>> result = select(attributes, TABLE_POST, additional);
        return result.stream().map(row -> {
            int postId = Integer.parseInt(row.get("PostID"));
            String title = row.get("Title");
            String content = row.get("Content");

            int userId = Integer.parseInt(row.get("UserID"));
            String firstName = row.get("FirstName");
            String lastName = row.get("LastName");
            String email = row.get("Email");
            User author = new User(userId, firstName, lastName, email);
            return new Thread(postId, title, content, author);
        }).collect(Collectors.toList());

    }

    public Collection<Comment> getComments(DiscussionPost discussionPost){
        Collection<String> attributes = new ArrayList<>();
        attributes.add("PostID");
        attributes.add("Content");
        attributes.add("AuthorID");
        attributes.add("FirstName");
        attributes.add("LastName");
        String additional = "NATURAL JOIN ";
        additional += TABLE_COMMENT;
        additional += " INNER JOIN ";
        additional += TABLE_USER;
        additional += " ON AuthorID = UserID WHERE DiscussionID = \"";
        additional += discussionPost.getPostID();
        additional += "\"";
        Collection<Map<String, String>> result = select(attributes, TABLE_POST, additional);
        return result.stream().map(row -> {
            int postId = Integer.parseInt(row.get("PostID"));
            User author = new User(
                    Integer.parseInt(row.get("AuthorID")),
                    row.get("FirstName"),
                    row.get("LastName"),
                    null
            );
            String content = row.get("Content");
            return new Comment(postId, content, author);

        }).collect(Collectors.toList());
    }

    private String localDateTimeConverter(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public Collection<Course> coursesToUser(User user) {
        Collection<String> courseAttributes = new ArrayList<>(Arrays.asList("CourseID", "Name", "Term", "TermYear", "AnonymousAllowance"));
        Collection<Map<String, String>> courseRows = select(courseAttributes, TABLE_COURSE, "NATURAL JOIN userInCourse WHERE " + user.getUserID() + " = UserID");
        return courseRows
                .stream()
                .map(row -> new Course(Integer.parseInt(row.get("CourseID")), row.get("Name"), row.get("Term"), LocalDate.parse(row.get("TermYear")).getYear(), Boolean.parseBoolean(row.get("AnonymousAllowance"))))
                .collect(Collectors.toList());
    }

    @Override
    public void viewPost(User user, Post post, LocalDateTime viewedTimed) {
        String additional = "WHERE UserID="+user.getUserID()+" AND PostID="+post.getPostID();
        boolean alreadyExist = select(null, TABLE_VIEWEDBY,additional).size() > 0;
        if (!alreadyExist){
            Map<String, String> values = new HashMap<>();
            values.put("UserID", Integer.toString(user.getUserID()));
            values.put("PostID", Integer.toString(post.getPostID()));
            values.put("ViewedTime", localDateTimeConverter(viewedTimed));
            insert(values, TABLE_VIEWEDBY);
        }
    }


    public Collection<Folder> getFolders(Course course) {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("FolderID");
        attributes.add("Title");
        String additional = "NATURAL JOIN " + TABLE_ROOT_FOLDER + " WHERE CourseID = \"" + course.getCourseID() + "\"";
        Collection<Map<String, String>> result = select(attributes, TABLE_FOLDER, additional);

        return result.stream()
                .map(row -> {
                    Collection<Folder> subFolders = getSubFolders(Integer.parseInt(row.get("FolderID")));
                    return new Folder(Integer.parseInt(row.get("FolderID")), row.get("Title"), subFolders);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<DiscussionPost> getDiscussionPosts(Thread thread) {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("PostID");
        attributes.add("Content");
        attributes.add("PostedTime");
        attributes.add("AuthorID");
        attributes.add("FirstName");
        attributes.add("LastName");
        String additional = "NATURAL JOIN ";
        additional += TABLE_DISCUSSION;
        additional += " INNER JOIN ";
        additional += TABLE_USER;
        additional += " ON AuthorID = UserID WHERE ThreadID = \"";
        additional += thread.getPostID();
        additional += "\"";
        return select(attributes, TABLE_POST,additional).stream()
                .map(row -> {
                    int postID = Integer.parseInt(row.get("PostID"));
                    String content = row.get("Content");
                    User author = new User(
                            Integer.parseInt(row.get("AuthorID")),
                            row.get("FirstName"),
                            row.get("LastName"),
                            null
                    );
                    return new DiscussionPost(postID, content, author, null);
                })
                .collect(Collectors.toList());
    }

    public Collection<Tag> getTags() {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("TagName");
        return select(attributes, TABLE_TAG, "").stream()
                .map(row -> new Tag(row.get("TagName")))
                .collect(Collectors.toList());
    }

    public Collection<Tag> getTags(Thread thread) {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("TagName");
        String additional = "WHERE ThreadID = "+thread.getPostID();
        return select(attributes, TABLE_TAG_ON_THREAD, additional ).stream()
                .map(row -> new Tag(row.get("TagName")))
                .collect(Collectors.toList());
    }

    private Collection<Folder> getSubFolders(int parentID) {
        Collection<String> attributes = new ArrayList<>();
        attributes.add("FolderID");
        attributes.add("Title");
        String additional = "NATURAL JOIN " + TABLE_SUB_FOLDER + " WHERE ParentFolderID = \"" + parentID + "\"";
        Collection<Map<String, String>> result = select(attributes, TABLE_FOLDER, additional);
        return result.stream()
                .map(row -> {
                    Collection<Folder> subFolders = getSubFolders(Integer.parseInt(row.get("FolderID")));
                    return new Folder(Integer.parseInt(row.get("FolderID")), row.get("Title"), subFolders);
                })
                .collect(Collectors.toList());
    }



    public static void main(String[] args) {
        SQLController db = new SQLController();

        // Hary sin personlige test kode...
        //User user = db.createUser("Olav", "Nordmann", "ssdadss@dasddjacskkljl.com", "dsajlksjadlaksj");
        User user = User.signIn("a@a", "a");
        Course course = db.coursesToUser(user).iterator().next();
        //Thread thread = db.search("ER", course).iterator().next();
        //DiscussionPost discussionPost = db.getDiscussionPosts(thread).iterator().next();
        //System.out.println(db.getComments(discussionPost));
        /*Folder folder = course.getFolders().stream()
                .filter(randomFolder -> randomFolder.getFolderID() == 1)
                .findFirst()
                .get().getSubfolders().stream()
                .filter(randomFolder -> randomFolder.getFolderID() == 2)
                .findFirst()
                .get();*/
        //System.out.println(folder.getThreads());


        //db.postThread("Tittel", "grov content", user, LocalDateTime.now(), 2);
        //Thread thread = new Thread(1, "sjd", 3, LocalDateTime.now(), true, new ArrayList<>());
        //DiscussionPost discussion = new DiscussionPost(1, "sjd", 3, LocalDateTime.now(), true, new ArrayList<>());
        //db.postComment("grov content", user, LocalDateTime.now(), discussion);
        //System.out.println(db.isEmailUsed("ssdadss@dasddjacskkljl.com"));
        //User user = User.signIn("kake@kake.com","123eple321");
        //System.out.println(db.getStatistics(user));

    }
}


