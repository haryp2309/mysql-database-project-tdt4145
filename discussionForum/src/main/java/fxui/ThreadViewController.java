package fxui;

import discussionForum.DiscussionPost;
import discussionForum.Thread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ThreadViewController extends AbstractController {
    @FXML
    Label title;

    @FXML Label content;

    @FXML
    ListView<DiscussionPost> discussionPostListView;

    @FXML
    Button back;

    @Override
    protected void onSceneSwitch() {
        super.onSceneSwitch();
        Thread currentThread = getForum().getCurrentThread();
        title.setText(currentThread.getTitle());
        content.setText(currentThread.getContent());
        back.setOnAction(event -> {
            getForum().setCurrentThread(null);
            switchScene(AvailableSceneName.COURSE_HOME);
        });
        discussionPostListView.getItems().addAll(currentThread.getDiscussionPosts());
    }

    @FXML
    protected  void onClicked() {
        DiscussionPost thread = discussionPostListView.getSelectionModel().getSelectedItem();
        getForum().setCurrentDiscussionPost(thread);
        switchScene(AvailableSceneName.DISCUSSION_POST_VIEW);
    }
}
