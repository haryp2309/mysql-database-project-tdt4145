package fxui;

import discussionForum.Folder;
import discussionForum.Tag;
import discussionForum.Thread;
import discussionForum.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class MakeThreadController extends AbstractController{

    @FXML
    TextField content;

    @FXML
    Button post;

    @FXML
    Button back;

    @FXML
    TextField title;

    @FXML
    ListView<Tag> tagListView;

    @FXML
    ChoiceBox<Tag> tagChoiceBox;

    @FXML Button addTag;


    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.THREADS_IN_FOLDER);
        });
        post.setOnAction(event -> {
            String content1 = content.getText();
            String title1 = title.getText();
            Folder folder = getForum().getCurrentFolder();
            User user = getForum().getCurrentUser();
            folder.postThread(title1, content1, user, tagListView.getItems());
            switchScene(AvailableSceneName.THREADS_IN_FOLDER);
        });
        Tag.getAllTags().forEach(tag -> tagChoiceBox.getItems().add(tag));
        addTag.setOnAction(this::addTag);
    }

    void addTag(ActionEvent event) {
        Tag tag = tagChoiceBox.getSelectionModel().getSelectedItem();
        tagListView.getItems().add(tag);
        tagChoiceBox.getItems().remove(tag);
        tagChoiceBox.getSelectionModel().selectFirst();
    }


}
