package discussionforum;

public class UserInCourse {
    private User user;
    private Course course;
    private boolean isInstructor;
    private static boolean isInvitationAccepted = false;

    public UserInCourse(User user, Course course, boolean isInstructor) {
        this.user = user;
        this.course = course;
        this.isInstructor = isInstructor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setInstructor(boolean isInstructor) {
        this.isInstructor = isInstructor;

    }

    public boolean isInvitationAccepted() {
        return isInvitationAccepted;
    }

    public void setInvitationAccepted(boolean invitationAccepted) {
        this.isInvitationAccepted = invitationAccepted;
    }
}
