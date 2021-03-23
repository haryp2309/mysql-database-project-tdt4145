package fxui;

import discussionForum.Comment;
import discussionForum.DiscussionPost;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DiscussionPostController extends AbstractController {
    @FXML
    Label title;

    @FXML Label content;

    @FXML
    Button back;

    @FXML
    Button postComment;

    @FXML
    ListView<Comment> commentListView;

    @FXML Label originalThread;

    @Override
    protected void onSceneSwitch() {
        super.onSceneSwitch();
        DiscussionPost currentDiscussionPost = getForum().getCurrentDiscussionPost();
        title.setText("Discussion Post");
        content.setText(currentDiscussionPost.getContent());
        back.setOnAction(event -> {
            getForum().setCurrentDiscussionPost(null);
            switchScene(AvailableSceneName.THREAD_VIEW);
        });
        postComment.setOnAction(event -> {
            switchScene(AvailableSceneName.MAKE_COMMENT);
        });
        commentListView.getItems().addAll(currentDiscussionPost.getComments());
        originalThread.setText("From thread: "+getForum().getCurrentThread().getTitle());
    }
}
