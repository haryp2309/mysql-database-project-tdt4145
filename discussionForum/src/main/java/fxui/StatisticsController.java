package fxui;

import discussionForum.Folder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Collection;
import java.util.Map;

public class StatisticsController extends AbstractController{

    @FXML
    ListView<Collection<Map<String, String>>> statisticsTreeView;

    @FXML
    Button back;

    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.USER_HOME);
        });

        statisticsTreeView.getItems().addAll(getForum().getCurrentUser().getStatistics());
    }

}
