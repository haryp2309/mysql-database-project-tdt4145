package fxui;

import discussionForum.DiscussionPost;
import discussionForum.Thread;
import discussionForum.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MakeCommentController extends AbstractController{

    @FXML
    TextField content;

    @FXML
    Button post;

    @FXML
    Button back;



    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.DISCUSSION_POST_VIEW);
        });
        post.setOnAction(event -> {
            String content = this.content.getText();
            DiscussionPost discussionPost = getForum().getCurrentDiscussionPost();
            User user = getForum().getCurrentUser();
            discussionPost.postComment(content, user);
            switchScene(AvailableSceneName.DISCUSSION_POST_VIEW);
        });
    }


}
