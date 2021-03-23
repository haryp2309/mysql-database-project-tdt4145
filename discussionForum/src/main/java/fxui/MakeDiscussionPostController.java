package fxui;

import discussionForum.Folder;
import discussionForum.Thread;
import discussionForum.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class MakeDiscussionPostController extends AbstractController{

    @FXML
    TextField content;

    @FXML
    Button post;

    @FXML
    Button back;



    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.THREAD_VIEW);
        });
        post.setOnAction(event -> {
            String content = this.content.getText();
            Thread thread = getForum().getCurrentThread();
            User user = getForum().getCurrentUser();
            thread.postDiscussionPost(content, user);
            switchScene(AvailableSceneName.THREAD_VIEW);
        });
    }


}
