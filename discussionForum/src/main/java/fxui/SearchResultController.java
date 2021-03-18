package fxui;

import discussionForum.Thread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SearchResultController extends AbstractController {
    @FXML
    ListView<Thread> threadListView;

    @FXML
    Button back;

    @Override
    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.COURSE_HOME);
        });
        String searchQuery = getForum().getSearchQuery();
        threadListView.getItems().addAll(getForum().getCurrentCourse().search(searchQuery));
    }
}
