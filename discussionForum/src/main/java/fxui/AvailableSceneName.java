package fxui;

public enum AvailableSceneName {
    APP("Login.fxml"),
    USER_HOME("UserHome.fxml"),
    COURSE_HOME("CourseHome.fxml");

    private final String value;

    AvailableSceneName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
