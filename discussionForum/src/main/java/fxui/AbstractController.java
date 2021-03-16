package fxui;

import discussionForum.Forum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {
    private Stage stage;
    private Forum forum;

    protected void setForum(Forum forum) {
        this.forum = forum;
    }

    protected Forum getForum() {
        return forum;
    }

    protected Stage getStage() {
        return stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    protected AbstractController switchScene(AvailableSceneName sceneName) {
        try {
            return migrateToScene(getStage(), sceneName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected AbstractController openScene(AvailableSceneName sceneName) throws IOException {
        Stage newStage = new Stage();
        AbstractController controller = migrateToScene(newStage, sceneName);
        newStage.show();
        return controller;
    }

    private AbstractController migrateToScene(Stage stage, AvailableSceneName sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName.getValue()));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        AbstractController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setForum(getForum());
        controller.onSceneSwitch();
        stage.setScene(scene);
        return controller;
    }

    protected void onSceneSwitch() {
        //EMPTY
    }

}