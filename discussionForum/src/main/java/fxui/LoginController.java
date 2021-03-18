package fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import discussionForum.User;

public class LoginController extends AbstractController {
    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    Button signInButton;

    @FXML
    Button registerButton;

    @Override
    protected void onSceneSwitch() {
        signInButton.onActionProperty().setValue((event) -> {
            signIn();
        });
        registerButton.onActionProperty().setValue((event) -> {
            register();
        });
    }

    @FXML
    void signIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = User.signIn(username, password);
        if (user != null) {
            getForum().setCurrentUser(user);
            switchScene(AvailableSceneName.USER_HOME);
        }
    }

    @FXML
    void register() {
    }

}
