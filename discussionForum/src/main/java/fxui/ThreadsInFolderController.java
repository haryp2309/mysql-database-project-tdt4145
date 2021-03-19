package fxui;

import discussionForum.Thread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ThreadsInFolderController extends AbstractController {
    @FXML
    ListView<Thread> threadListView;

    @FXML Button back;

    @Override
    protected void onSceneSwitch() {
        super.onSceneSwitch();
        threadListView.getItems().addAll(getForum().getCurrentFolder().getThreads());
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.COURSE_HOME);
        });
    }

    @FXML
    protected  void onClicked() {
        Thread thread = threadListView.getSelectionModel().getSelectedItem();
        if (thread != null) {
            getForum().setCurrentThread(thread);
            switchScene(AvailableSceneName.THREAD_VIEW);
        }
    }
}
