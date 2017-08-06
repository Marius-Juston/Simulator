package window.misc;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static window.main.Main.APPLICATION_NAME;

public class ConfirmBox {
	private static final Stage stage;
	private static byte choice = 2;

	static {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root;
		try {
			root = FXMLLoader.load(ConfirmBox.class.getResource("../misc/confirm_box.fxml"));

			stage.setTitle(APPLICATION_NAME + "- Confirm Box");
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte confirm(String message) {
		((Label) stage.getScene().getRoot().getChildrenUnmodifiable().get(0)).setText(message);
		stage.showAndWait();
		return choice;
	}

	public void choose(ActionEvent actionEvent) {
		switch (((Button) actionEvent.getSource()).getText()) {
			case "Yes":
				choice = 0;
				break;
			case "No":
				choice = 1;
				break;
			case "Cancel":
				choice = 2;
				break;
		}

		stage.close();
	}
}
