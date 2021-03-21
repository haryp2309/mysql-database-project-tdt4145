package fxui;

import discussionForum.Folder;
import discussionForum.Thread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class MakeThreadController extends AbstractController{

    @FXML
    TextField textField;

    @FXML
    Button post;

    @FXML
    Button back;

    @FXML
    TextField title;


    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.COURSE_HOME);
        });
    }

    void postThread(){
        String content = textField.getText();
        String title1 = title.getText();

        Folder folder = getForum().getCurrentFolder();
        LocalDateTime postedTimed = LocalDateTime.now();
        folder.postThread(title1, content, postedTimed, getForum().getCurrentUser());
        switchScene(AvailableSceneName.COURSE_HOME);
    }

}
