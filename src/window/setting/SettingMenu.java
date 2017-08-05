package window.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static window.main.Main.APPLICATION_NAME;

public class SettingMenu implements Initializable {
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    public TextField frequency;
    public TextField seed;
    public ToggleButton dimension;
    public TextField nObject;
    public TextField gravity;
    @FXML
    private TextField checkpointSavePathTextArea;
    @FXML
    private TextField mapSavePathTextArea;

    public void goToMainMenu(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("../main/main_menu.fxml"));
        primaryStage.setTitle(APPLICATION_NAME + "- Settings");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Setting setting = Setting.getInstance();

        frequency.setText(String.valueOf(setting.getCheckpointSaveFrequency()));
        seed.setText(String.valueOf(setting.getRandomSeed()));
        dimension.setSelected(setting.isGameDimension());
        nObject.setText(String.valueOf(setting.getNumberOfGenerationsAtOnce()));
        gravity.setText(String.valueOf(setting.getGravity()));
        checkpointSavePathTextArea.setText(setting.getCheckpointSavePath());
        mapSavePathTextArea.setText(setting.getMapSaveLocation());
    }

    private void openDirectoryBrowser(ActionEvent actionEvent, TextField area) {
        File directory = directoryChooser.showDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (directory != null)
            area.setText(directory.getAbsolutePath());
    }

    public void openMapDirectoryBrowser(ActionEvent actionEvent) {
        openDirectoryBrowser(actionEvent, mapSavePathTextArea);
    }

    public void openCheckpointDirectoryBrowser(ActionEvent actionEvent) {
        openDirectoryBrowser(actionEvent, checkpointSavePathTextArea);
    }

    public void saveSettings(ActionEvent actionEvent) {
        //TODO save the settings
    }
}
