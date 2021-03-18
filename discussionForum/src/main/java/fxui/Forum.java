package fxui;

import discussionForum.Course;
import discussionForum.User;

import java.util.Collection;
import java.util.Map;

import static discussionForum.DatabaseController.db;

public class Forum {
    private User currentUser;
    private Course currentCourse;
    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    public Collection<Map<String, String>> getStatistics(User user) {
        return db.getStatistics(user);
    }

    @Override
    public String toString() {
        return "Forum{" +
                "currentUser=" + currentUser +
                ", currentCourse=" + currentCourse +
                '}';
    }
}
