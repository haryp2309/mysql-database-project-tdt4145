package discussionForum;

import java.sql.DatabaseMetaData;

import static discussionForum.DatabaseController.db;

public class Course {
    private int courseID;
    private String courseName;
    private String term;
    private int termYear;
    private boolean anonymousAllowance;


    public Course(String courseName, String term, int termYear, boolean anonymousAllowance) {
        this.courseName = courseName;
        this.term = term;
        this.termYear = termYear;
        this.anonymousAllowance = anonymousAllowance;
    }
}
