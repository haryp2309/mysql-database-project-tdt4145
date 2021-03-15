package discussionForum;

import java.sql.DatabaseMetaData;

import static discussionForum.DatabaseController.db;

public class Course {
    private int CourseID;
    private String CourseName;
    private String Term;
    private int TermYear;
    private boolean AnonymousAllowance;

    public Course(String CourseName, String Term, int TermYear, boolean AnonymousAllowance) {
        this.CourseName = CourseName;
        this.Term = Term;
        this.TermYear = TermYear;
        this.AnonymousAllowance = AnonymousAllowance;
    }
}
