package discussionForum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static discussionForum.DatabaseController.db;


public class Course {
    private int courseID;
    private String courseName;
    private String term;
    private int termYear;
    private boolean anonymousAllowance;
    private Collection<Folder> folders = new ArrayList<Folder>();

    public Course(int courseID, String courseName, String term, int termYear, boolean anonymousAllowance, Collection<Folder> folders) {
        this.courseName = courseName;
        this.term = term;
        this.termYear = termYear;
        this.anonymousAllowance = anonymousAllowance;
        this.folders = folders;
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


    public void addFolders(Folder folder) {
        if(!this.folders.contains(folder)) {
            this.folders.add(folder);
        }
    }

    public Collection<Folder> getFolders() {
        return db.getFolders(this);
    }



    public Collection<Thread> search(String searchWord) {
        return db.search(searchWord, this);
    }

    public Collection<Map<String, String>> getStatistics(User user) {
        return db.getStatistics(user, this);
    }

    @Override
    public String toString() {
        return courseName;
    }
}
