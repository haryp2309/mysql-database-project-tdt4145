package discussionForum;

import static discussionForum.DatabaseController.db;

public class Forum {
    User currentUser;
    Course currentCourse;

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

    public String getStatistics(User user) {
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
