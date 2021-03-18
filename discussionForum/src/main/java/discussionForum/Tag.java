package discussionforum;

public enum Tag {
    QUESTION("Question"),
    ANNOUNCEMENTS("Announcements"),
    HOMEWORK("Homework"),
    HOMEWORK_SOLUTIONS("Homework Solutions"),
    LECTURE_NOTES("Lectures Notes"),
    GENERAL_ANNOUNCEMENTS("General Announcements");

    String value;

    Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
