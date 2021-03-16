package fxui;


import discussionForum.Forum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(AvailableSceneName.APP.getValue()));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        AbstractController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setForum(new Forum());
        controller.onSceneSwitch();

        stage.setTitle("Discussion Forum");
        stage.show();
    }
}

