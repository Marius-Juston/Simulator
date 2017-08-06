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
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import window.misc.ConfirmBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static window.main.Main.APPLICATION_NAME;

public class SettingMenu implements Initializable {
	private final DirectoryChooser directoryChooser = new DirectoryChooser();
	@FXML
	private TextField frequency;
	@FXML
	private TextField seed;
	@FXML
	private ToggleButton dimension;
	@FXML
	private TextField nObject;
	@FXML
	private TextField gravity;
	@FXML
	private boolean needSave = false;
	@FXML
	private TextField checkpointSavePathTextArea;
	@FXML
	private TextField mapSavePathTextArea;

	private void goToMainMenu(ActionEvent actionEvent) throws IOException {

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
		setDimension(setting.isGameDimension());
		nObject.setText(String.valueOf(setting.getNumberOfGenerationsAtOnce()));
		gravity.setText(String.valueOf(setting.getGravity()));
		checkpointSavePathTextArea.setText(new File(setting.getCheckpointSavePath()).getAbsolutePath());
		mapSavePathTextArea.setText(new File(setting.getMapSaveLocation()).getAbsolutePath());
	}

	public void dimensionButtonPress() {
		setDimension(dimension.isSelected());
		needSave = true;
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

	private int getFrequency() {
		return Integer.valueOf(frequency.getText());
	}

	private int getSeed() {
		return Integer.valueOf(seed.getText());
	}

	private boolean getDimension() {
		return dimension.isSelected();
	}

	private void setDimension(boolean d) {
		dimension.setSelected(d);
		dimension.setText(d ? "3D" : "2D");
	}

	private int getNObject() {
		return Integer.valueOf(nObject.getText());
	}


	private double getGravity() {
		return Double.valueOf(gravity.getText());
	}

	private String getCheckpointSavePath() {
		return checkpointSavePathTextArea.getText();
	}

	private String getMapSavePath() {
		return mapSavePathTextArea.getText();
	}

	@FXML
	private void saveSettings(ActionEvent actionEvent) {
		//TODO save the settings
		Setting settings = Setting.getInstance();

		settings.setCheckpointSavePath(getCheckpointSavePath());
		settings.setMapSaveLocation(getMapSavePath());
		settings.setCheckpointSaveFrequency(getFrequency());
		settings.setRandomSeed(getSeed());
		settings.setGameDimension(getDimension());
		settings.setNumberOfGenerationsAtOnce(getNObject());
		settings.setGravity(getGravity());

		settings.saveSettings();
		needSave = false;
	}


	public void verify(ActionEvent actionEvent) throws IOException {
		byte choice = 0;

		if (needSave)
			if ((choice = ConfirmBox.confirm("Do you want to save your settings?")) == 0)
				saveSettings(actionEvent);

		if (choice != 2)
			goToMainMenu(actionEvent);
	}

	private void check(KeyEvent keyEvent, String word) {
		TextField field = (TextField) keyEvent.getSource();

		int caretPosition = field.getCaretPosition();

//        System.out.println(keyEvent.getCharacter());

		if (!keyEvent.getCharacter().matches(word)) {
			keyEvent.consume();
		}

		if (!field.getText().matches(word)) {
			field.setText(field.getText().replaceAll("\\D", ""));
			keyEvent.consume();
		}

		try {
			long number = Long.valueOf(field.getText());
			long newNumber;

			if ((newNumber = Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, number))) != number)
				keyEvent.consume();

			field.setText(String.valueOf(newNumber));
		} catch (NumberFormatException e) {
			try {
				float number = Float.valueOf(field.getText());

				double newNumber;

				if ((newNumber = Math.max(Double.MIN_VALUE, Math.min(Double.MAX_VALUE, number))) != number)
					keyEvent.consume();

				field.setText(String.valueOf(newNumber));
			} catch (NumberFormatException ei) {
				keyEvent.consume();
				setDefaultValue(field);
			}
		}

		field.positionCaret(caretPosition);
		needSave = true;
	}

	private void setDefaultValue(TextField field) {
		Setting settings = Setting.getInstance();
		switch (field.getId()) {
			case "frequency":
				frequency.setText(String.valueOf(settings.getCheckpointSaveFrequency()));
				break;
			case "seed":
				seed.setText(String.valueOf(settings.getRandomSeed()));

				break;
			case "nObject":
				nObject.setText(String.valueOf(settings.getNumberOfGenerationsAtOnce()));
				break;
			case "gravity":
				gravity.setText(String.valueOf(settings.getGravity()));
				break;
		}
	}

	public void checkFloat(KeyEvent keyEvent) {
		check(keyEvent, "[0-9]+\\.?[0-9]+");
	}

	public void checkInt(KeyEvent keyEvent) {
		check(keyEvent, "[0-9]+");
	}
}
