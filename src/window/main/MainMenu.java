package window.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static window.main.Main.APPLICATION_NAME;

public class MainMenu implements Initializable {
    @FXML
    private Label welcomeTextLabel;
    @FXML
    private Button openLoadMenuButton;
    @FXML
    private Button openGameMenuButton;
    @FXML
    private Button openSettingsMenuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeTextLabel.setText("Welcome to " + APPLICATION_NAME);
    }

    public void openSettings(ActionEvent event) throws IOException {

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("../setting/setting_menu.fxml"));
        primaryStage.setTitle(APPLICATION_NAME + "- Settings");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
