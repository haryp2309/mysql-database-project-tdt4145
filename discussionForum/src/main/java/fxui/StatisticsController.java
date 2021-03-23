package fxui;

import discussionForum.Folder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsController extends AbstractController{

    @FXML
    ListView<String> statisticsTreeView;

    @FXML
    Button back;

    protected void onSceneSwitch() {
        super.onSceneSwitch();
        back.setOnAction(event -> {
            switchScene(AvailableSceneName.USER_HOME);
        });
        Collection<Map<String, String>> stats = getForum().getCurrentUser().getStatistics();

        Collection<String> newStats = stats.stream().map(row -> {
            String email = row.get("Email");
            String postCount = row.get("NoOfPostCreated");
            String postViewed = row.get("NoOfPostViewed");
            return email + " has " + postCount + " posts created and " + postViewed + " posts viewed.";
        }).collect(Collectors.toList());

        for(String row : newStats){
            statisticsTreeView.getItems().add(row);
        }
                ;
    }

}
