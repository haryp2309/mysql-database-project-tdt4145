package discussionForum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class SQLController extends MySQLConn implements DatabaseController {

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
                    .reduce((a,b) -> a+", "+b)
                    .orElse("*");
            query += " ";
            query += "FROM ";
            query += table;
            query += " ";
            query += additionalSQLStatements;

            System.out.println(query);

            Statement stmt = this.conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);


            while(rset.next()) {
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

    private void insert(HashMap<String, String> values, String table) {
        if (values.isEmpty()) throw new  IllegalArgumentException("Attributes can't be empty");
        String columns = values.keySet().stream()
                .map(column -> "`"+column+"`")
                .reduce((a,b) -> a+", "+b)
                .get();
        String valuesString = values.keySet().stream()
                .map(values::get)
                .map(value -> "\""+value+"\"")
                .reduce((a, b) -> a+", "+b)
                .get();
        String query = "INSERT INTO " + table + " ("+columns+") VALUES "+ " ("+valuesString+") ";
        System.out.println(query);

        try {
            Statement stmt = this.conn.createStatement();
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createUser(String firstName, String lastName, String email, String password) {
        HashMap<String, String> values = new HashMap<>();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("Email", email);
        values.put("Password", password);
        this.insert(values, "user");
    }

    public static void main(String[] args)  {
        SQLController db = new SQLController();
        db.createUser("Olav", "Nordmann", "olav@dasljl.com", "dsajlksjadlaksj");
    }
}
