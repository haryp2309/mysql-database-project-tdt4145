package discussionForum;

import java.sql.*;

public abstract class MySQLConn {
    protected Connection conn;

    public MySQLConn() {

    }

    public void connect() {
        try {
            // Class.forName("com.mysql.jdbc.Driver").newInstance(); when you are using MySQL 5.7
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "test123");

        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}