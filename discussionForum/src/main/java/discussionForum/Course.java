package discussionForum;

import java.util.ArrayList;
import java.util.Collection;

public class Course {
    private int courseID;
    private String courseName;
    private String term;
    private int termYear;
    private boolean anonymousAllowance;
    private Collection<Folder> folders = new ArrayList<Folder>();

    public Course(String courseName, String term, int termYear, boolean anonymousAllowance, Collection<Folder> folders) {
        this.courseName = courseName;
        this.term = term;
        this.termYear = termYear;
        this.anonymousAllowance = anonymousAllowance;
        this.folders = folders;
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



}
