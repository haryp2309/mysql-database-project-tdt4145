package discussionForum;

import java.util.Collection;
import static discussionForum.DatabaseController.db;


public class Course {
    private int courseID;
    private String courseName;
    private String term;
    private int termYear;
    private boolean anonymousAllowance;

    public Course(int courseID, String courseName, String term, int termYear, boolean anonymousAllowance) {
        this.courseName = courseName;
        this.term = term;
        this.termYear = termYear;
        this.anonymousAllowance = anonymousAllowance;
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }


    public String getTerm() {
        return term;
    }


    public int getTermYear() {
        return termYear;
    }


    public boolean isAnonymousAllowance() {
        return anonymousAllowance;
    }



    public Collection<Folder> getFolders() {
        return db.getFolders(this);
    }



    public Collection<Thread> search(String searchWord) {
        return db.search(searchWord, this);
    }

    @Override
    public String toString() {
        return courseName;
    }
}
