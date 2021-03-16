package discussionForum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SQLController extends MySQLConn implements DatabaseController {

    private static String TABLE_USER = "User";
    private static String TABLE_POST = "Post";
    private static String TABLE_THREAD = "Thread";

    private static String USER_ID = "UserID";
    private static String USER_FIRST_NAME = "FirstName";
    private static String USER_LAST_NAME = "LastName";
    private static String USER_EMAIL = "Email";
    private static String USER_PASSWORD = "Password";

    private static String POST_ID = "PostID";
    private static String POST_CONTENT = "Content";
    private static String POST_AUTHOR_ID = "AuthorID";
    private static String POST_POSTED_TIME = "PostedTime";
    private static String POST_TYPE = "PostType";
    private static String POST_TYPES_THREAD = "Thread";
    private static String POST_THREAD_TITLE = "Title";
    private static String POST_THREAD_FOLDER_ID = "FolderID";


    public SQLController() {
        this.connect();
    }

    private Collection<Map<String, String>> select(Collection<String> attributes, String table, String additionalSQLStatements) {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        Collection<Map<String, String>> result = new ArrayList<>();
        try {
            String query = "SELECT ";
            query += attributes.stream()
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("*");
            query += " ";
            query += "FROM ";
            query += table;
            query += " ";
            query += additionalSQLStatements;

            System.out.println(query);

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

    private String insert(HashMap<String, String> values, String table) {
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
        values.put(POST_POSTED_TIME, postedTime.format(DateTimeFormatter.ISO_DATE_TIME));
        values.put(POST_TYPE, POST_TYPES_THREAD);
        return Integer.parseInt(Objects.requireNonNull(this.insert(values, TABLE_POST)));
    }

    @Override
    public void postThread(String title, String content, User author, LocalDateTime postedTime, int folderId) {
        int postId = post(content, author, postedTime);
        HashMap<String, String> values = new HashMap<>();
        values.put(POST_ID, Integer.toString(postId));
        values.put(POST_THREAD_TITLE, title);
        values.put(POST_THREAD_FOLDER_ID, Integer.toString(folderId));
        insert(values, TABLE_THREAD);
    }

    public static void main(String[] args) {
        SQLController db = new SQLController();
        //User user = db.createUser("Olav", "Nordmann", "ssdadss@dasddjacskkljl.com", "dsajlksjadlaksj");
        User user = User.signIn("ssdadss@dasddjacskkljl.com", "dsajlksjadlaksj");
        //db.postThread("Tittel", "grov content", user, LocalDateTime.now(), 2);
        System.out.println(db.isEmailUsed("ssdadss@dasddjacskkljl.com"));
    }
}


