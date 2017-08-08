package window.misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmBox {
	private static final Alert confirmBox;

	private static final ButtonType yesButton = new ButtonType("Yes");
	private static final ButtonType noButton = new ButtonType("No");
	private static final ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

	static {
		confirmBox = new Alert(Alert.AlertType.CONFIRMATION);
		confirmBox.setTitle("Confirmation Alert");
		confirmBox.setHeaderText(null);
		confirmBox.getButtonTypes().setAll(yesButton, noButton, cancelButton);
	}

	public static byte confirm(String message) {
		confirmBox.setContentText(message);

		Optional<ButtonType> result = confirmBox.showAndWait();

		if (result.isPresent())
			if (result.get() == yesButton)
				return 0;
			else if (result.get() == noButton)
				return 1;
		return 2;
	}
}
