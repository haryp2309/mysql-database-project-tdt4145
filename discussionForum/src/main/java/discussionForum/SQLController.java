package discussionForum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SQLController extends MySQLConn implements DatabaseController {

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

    public static final String USER_ID = "UserID";
    public static final String USER_FIRST_NAME = "FirstName";
    public static final String USER_LAST_NAME = "LastName";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "Password";

    public static final String COURSE_ID = "CourseID";
    public static final String COURSE_NAME = "Name";
    public static final String COURSE_TERMYEAR = "TermYear";
    public static final String COURSE_TERM = "Term";
    public static final String COURSE_ANOALLOWANCE = "AnonymousAllowance";

    public static final String POST_ID = "PostID";
    public static final String POST_CONTENT = "Content";
    public static final String POST_AUTHOR_ID = "AuthorID";
    public static final String POST_POSTED_TIME = "PostedTime";
    public static final String POST_TYPE = "PostType";
    public static final String POST_TYPES_THREAD = "Thread";
    public static final String POST_THREAD_TITLE = "Title";
    public static final String POST_THREAD_POST_ID = "PostID";
    public static final String POST_THREAD_FOLDER_ID = "FolderID";
    public static final String POST_DISCUSSION_POST_ID = "PostID";
    public static final String POST_DISCUSSION_THREAD_ID = "ThreadID";
    public static final String POST_COMMENT_POST_ID = "PostID";
    public static final String POST_COMMENT_DISCUSSION_ID = "DiscussionID";

    public static final String TAG_TAG_ID = "TagID";

    public static final String VIEWED_TIME = "ViewedTime";
    public static final String LIKED_TIME = "LikedTime";
    public static final String VIEWEDBY_USERID = "UserID";

    public static final String FOLDER_ID = "FolderID";
    public static final String FOLDER_NAME = "Name";
    public static final String FOLDER_TYPE = "FolderType";

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
        Collection<Map<String, String>> result = new ArrayList<>();
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

    @Override
    public User createUser(String firstName, String lastName, String email, String password) {
        HashMap<String, String> values = new HashMap<>();
        values.put(USER_FIRST_NAME, firstName);
        values.put(USER_LAST_NAME, lastName);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        int id = Integer.parseInt(Objects.requireNonNull(this.insert(values, TABLE_USER)));
        return new User(id, firstName, lastName, email);
    }



    @Override
    public Collection<Thread> search(String searchWord, Course course) {
        Collection<String> attributes = new ArrayList<String>();
        attributes.add("PostID");
        attributes.add("Title");
        attributes.add("Content");

        Collection<Folder> folders = getFolders(course);
        Collection<String> folderIds = new ArrayList<>();
        Stack<Folder> stack = new Stack<>();
        stack.addAll(folders);
        while (!stack.isEmpty()) {
            Folder currentFolder = stack.pop();
            folderIds.add(Integer.toString(currentFolder.getFolderID()));
            stack.addAll(currentFolder.getSubfolders());
        }

        String seperatedFolderIDs = folderIds.stream().reduce((id1, id2) -> id1+" OR "+id2).orElse(null);
        if (seperatedFolderIDs == null) {
            return new ArrayList<>();
        }
        String query = "INNER JOIN Thread USING (PostID) WHERE (";
        query += seperatedFolderIDs;
        query += ") AND PostType = \"Thread\" AND (Title LIKE \"%";
        query += searchWord;
        query += "%\" OR Content LIKE \"%";
        query += searchWord;
        query += "%\")";
        Collection<Map<String, String>> result = select(attributes, TABLE_POST, query);
        return result.stream().map(row -> new Thread(Integer.parseInt(row.get("PostID")), row.get("Title"), row.get("Content"), null, null)).collect(Collectors.toList());
    }

    @Override
    public Collection<Map<String, String>> getStatistics(User user) {
        String query1 = "(SELECT ";
        query1 += "UserID, firstName, lastName, COUNT(PostedTime) AS NoOfPostCreated";
        query1 += " FROM ";
        query1 += TABLE_USER;
        query1 += " LEFT OUTER JOIN ";
        query1 += TABLE_POST;
        query1 += " ON ";
        query1 += TABLE_USER + "." + USER_ID;
        query1 += " = ";
        query1 += TABLE_POST + "." + USER_ID;
        query1 += " GROUP BY ";
        query1 += USER_ID;
        query1 += " ) AS PostedUser";

        String query2 = "(SELECT ";
        query2 += "UserID, COUNT(ViewedTime) AS NoOfPostViewed";
        query2 += " FROM ";
        query2 += TABLE_USER;
        query2 += " LEFT OUTER JOIN ";
        query2 += TABLE_VIEWEDBY;
        query2 += " ON ";
        query2 += TABLE_USER + "." + USER_ID;
        query2 += " = ";
        query2 += TABLE_VIEWEDBY + "." + USER_ID;
        query2 += " GROUP BY ";
        query2 += USER_ID;
        query2 += " ) AS ViewedUser";

        String query = "SELECT ";
        query += "FirstName, LastName, NoOfPostCreated, NoOfPostViewed";
        query += " FROM ";
        query += query1;
        query += " FULL OUTER JOIN ";
        query += query2;
        query += " ON ";
        query += "PostedUser.UserID = ViewedUser.UserID ";
        query += "ORDER BY NoOfPostViewed DESC";


        Collection<String> attributes = new ArrayList(Arrays.asList("FirstName", "LastName", "NoOfPostCreated", "NoOfPostViewed"));

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
    public boolean isEmailUsed(String email) {
        Collection<Map<String, String>> result = select(null, TABLE_USER, "WHERE " + USER_EMAIL + " = \"" + email + "\"");
        if (result.size() == 1) {
            return true;
        } else if (result.size() == 0) {
            return false;
        } else {
            throw new IllegalStateException("Database is corrupted!!");
        }
    }


    @Override
    public User signIn(String email, String password) {
        Collection<Map<String, String>> result = select(null, TABLE_USER, "WHERE " + USER_EMAIL + " = \"" + email + "\" AND " + USER_PASSWORD + " = \"" + password + "\"");
        if (result.size() > 1) {
            throw new IllegalStateException("Duplicate users in databse");
        } else if (result.size() < 1) {
            return null;
        } else {
            Map<String, String> userMap = result.iterator().next();
            int id = Integer.parseInt(userMap.get(USER_ID));
            String firstName = userMap.get(USER_FIRST_NAME);
            String lastName = userMap.get(USER_LAST_NAME);
            email = userMap.get(USER_EMAIL);
            return new User(id, firstName, lastName, email);
        }
    }

    private int post(String content, User author, LocalDateTime postedTime) {
        HashMap<String, String> values = new HashMap<>();
        values.put(POST_CONTENT, content);
        values.put(POST_AUTHOR_ID, Integer.toString(author.getUserID()));
        values.put(POST_POSTED_TIME, localDateTimeConverter(postedTime));
        values.put(POST_TYPE, POST_TYPES_THREAD);
        return Integer.parseInt(Objects.requireNonNull(this.insert(values, TABLE_POST)));
    }

    @Override
    public void postThread(String title, String content, User author, LocalDateTime postedTime, Folder folder) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put(POST_THREAD_POST_ID, Integer.toString(postId));
        values.put(POST_THREAD_TITLE, title);
        values.put(POST_THREAD_FOLDER_ID, Integer.toString(folder.getFolderID()));
        insert(values, TABLE_THREAD);
    }

    @Override
    public void postDiscussionPost(String content, User author, LocalDateTime postedTime, Thread thread) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put(POST_DISCUSSION_POST_ID, Integer.toString(postId));
        values.put(POST_DISCUSSION_THREAD_ID, Integer.toString(thread.getPostID()));
        insert(values, TABLE_DISCUSSION);
    }

    @Override
    public void postComment(String content, User author, LocalDateTime postedTime, DiscussionPost discussionPost) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put(POST_COMMENT_POST_ID, Integer.toString(postId));
        values.put(POST_COMMENT_DISCUSSION_ID, Integer.toString(discussionPost.getPostID()));
        insert(values, TABLE_COMMENT);
    }

    @Override
    public void tag(Thread thread, Tag tag) {
        Map<String, String> values = new HashMap<>();
        values.put(TAG_TAG_ID, tag.getValue());
        values.put(POST_THREAD_POST_ID, Integer.toString(thread.getPostID()));
        insert(values, TABLE_TAG_ON_THREAD);
    }

    public Collection<Thread> getThreads(Folder folder) {

        Collection<String> attributes = new ArrayList<String>();
        attributes.add("PostID");
        attributes.add("Title");
        Collection<Map<String, String>> result = select(attributes, TABLE_THREAD, "WHERE " + FOLDER_ID + " = " + folder.getFolderID());
        return result.stream().map(row -> new Thread(Integer.parseInt(row.get("PostID")), row.get("Title"), null, null, null)).collect(Collectors.toList());

    }

    private String localDateTimeConverter(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public Collection<Course> coursesToUser(User user) {
        Collection<String> courseAttributes = new ArrayList<>(Arrays.asList(COURSE_ID, COURSE_NAME, COURSE_TERM, COURSE_TERMYEAR, COURSE_ANOALLOWANCE));
        Collection<Map<String, String>> courseRows = select(courseAttributes, TABLE_COURSE, "NATURAL JOIN userInCourse WHERE " + user.getUserID() + " = UserID");
        return courseRows
                .stream()
                .map(row -> new Course(Integer.parseInt(row.get("CourseID")), row.get("Name"), row.get("Term"), LocalDate.parse(row.get("TermYear")).getYear(), Boolean.parseBoolean(row.get("AnonymousAllowance")), new ArrayList<>()))
                .collect(Collectors.toList());
    }

    @Override
    public void likePost(User user, Post post, LocalDateTime likedTime) {
        HashMap<String, String> values = new HashMap<>();
        values.put(USER_ID, Integer.toString(user.getUserID()));
        values.put(POST_ID, Integer.toString(post.getPostID()));
        values.put(LIKED_TIME, localDateTimeConverter(likedTime));
        insert(values, TABLE_LIKEDBY);
    }

    @Override
    public void viewPost(User user, Post post, LocalDateTime viewedTimed) {
        Map<String, String> values = new HashMap<>();
        values.put(USER_ID, Integer.toString(user.getUserID()));
        values.put(POST_ID, Integer.toString(post.getPostID()));
        values.put(VIEWED_TIME, localDateTimeConverter(viewedTimed));
        insert(values, TABLE_VIEWEDBY);
    }

    @Override
    public void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads, Course course) {
        Map<String, String> rootFolders = new HashMap<>();
        rootFolders.put(FOLDER_NAME, name);
        rootFolders.put(FOLDER_TYPE, "Root");
        rootFolders.put(COURSE_ID, Integer.toString(course.getCourseID()));
        insert(rootFolders, TABLE_ROOTFOLDER);
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
                    return new Folder(Integer.parseInt(row.get("FolderID")), row.get("Title"), subFolders, null);
                })
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
                    return new Folder(Integer.parseInt(row.get("FolderID")), row.get("Title"), subFolders, null);
                })
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        SQLController db = new SQLController();
        //User user = db.createUser("Olav", "Nordmann", "ssdadss@dasddjacskkljl.com", "dsajlksjadlaksj");
        User user = User.signIn("a@a", "a");
        Course course = db.coursesToUser(user).iterator().next();
        System.out.println(db.search("grov", course));
        //db.postThread("Tittel", "grov content", user, LocalDateTime.now(), 2);
        //Thread thread = new Thread(1, "sjd", 3, LocalDateTime.now(), true, new ArrayList<>());
        //DiscussionPost discussion = new DiscussionPost(1, "sjd", 3, LocalDateTime.now(), true, new ArrayList<>());
        //db.postComment("grov content", user, LocalDateTime.now(), discussion);
        //System.out.println(db.isEmailUsed("ssdadss@dasddjacskkljl.com"));
    }
}


