package window.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import window.misc.ConfirmBox;

public class Main extends Application {
	public static final String APPLICATION_NAME = "Simulation";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
		primaryStage.setTitle(APPLICATION_NAME + "- Main Menu");
		primaryStage.setScene(new Scene(root));
		primaryStage.setOnCloseRequest((e) -> {
			if (ConfirmBox.confirm("Are you sure you want to exit the program?") != 0) {
				e.consume();
			}
		});

		primaryStage.show();
	}
}
