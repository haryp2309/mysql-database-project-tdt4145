package discussionForum;

public class Forum {
    User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getStatistics(User user){
        return db.getStatistics;
    }

}