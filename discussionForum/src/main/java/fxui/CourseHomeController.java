package fxui;

import discussionForum.Folder;
import discussionForum.Thread;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Collection;
import java.util.stream.Collectors;


public class CourseHomeController extends AbstractController {
    @FXML
    TreeView<Folder> threadTreeView;

    @FXML
    Button searchButton;

    @FXML
    TextField searchField;

    @FXML Button backButton;

    private TreeItem<Folder> addSubFolders(Folder folder) {
        TreeItem<Folder> treeItem = new TreeItem<>(folder);
        folder.getSubfolders().forEach(subFolder -> {
            TreeItem<Folder> childItem = addSubFolders(subFolder);
            treeItem.getChildren().add(childItem);
        });
        return treeItem;
    }

    @Override
    protected void onSceneSwitch() {
        super.onSceneSwitch();
        Collection<TreeItem<Folder>> rootfolders = getForum().getCurrentCourse().getFolders().stream()
                .map(this::addSubFolders).collect(Collectors.toList());
        TreeItem<Folder> root = new TreeItem<>();
        rootfolders.forEach(rootFolder -> {
            root.getChildren().add(rootFolder);
        });
        rootfolders.forEach(folderTreeItem -> threadTreeView.setRoot(root));
        root.setExpanded(true);
        searchButton.setOnAction(event -> {
            getForum().setSearchQuery(searchField.getText());
            switchScene(AvailableSceneName.SEARCH_RESULT);
        });
        backButton.setOnAction(event -> {
            switchScene(AvailableSceneName.USER_HOME);
        });
    }
}
