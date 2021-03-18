package fxui;

import discussionForum.Course;
import discussionForum.Forum;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Collection;
import java.util.Map;

public class UserHomeController extends AbstractController {
    @FXML
    ListView<Course> courseListView = new ListView<>();

    @Override
    protected void onSceneSwitch() {
        Collection<Course> courses = getForum().getCurrentUser().getCourses();
        courseListView.getItems().addAll(courses);
    }

    @FXML
    protected  void onClicked() {
        Course course = courseListView.getSelectionModel().getSelectedItem();
        getForum().setCurrentCourse(course);
        switchScene(AvailableSceneName.COURSE_HOME);
    }
}
