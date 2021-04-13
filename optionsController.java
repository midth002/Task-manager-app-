package application_group;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class optionsController extends Options {

	@FXML
	private ImageView menu;
	@FXML
	private VBox vb;
	@FXML
	private Text userText;
	@FXML
	private TextField categoryField;
	@FXML
	private Text confirmation;

	public void myUserName(String text) {

		userText.setText(text);
	}

	@FXML
	public void signOut(ActionEvent evt) throws IOException {

		Parent signInRoot = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		Scene signInScene = new Scene(signInRoot, 400, 600);

		Stage window = (Stage) ((Node) evt.getSource()).getScene().getWindow();

		window.setScene(signInScene);
		window.show();
	}

	public void mainMenu() {

		menu.setOnMouseClicked((MouseEvent evt) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("taskMangerMain.fxml"));
				Parent mainPageRoot = loader.load();

				Scene signUpScene = new Scene(mainPageRoot, 400, 600);
				signUpScene.getStylesheets().add("application.css");

				tmController tm = loader.getController();
				tm.myUserName(userText.getText());

				Stage window = (Stage) ((Node) evt.getSource()).getScene().getWindow();

				window.setScene(signUpScene);
				window.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	@FXML
	private void category(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

		String us = userText.getText();
		try {
			userDataAccess.insertTaskCategory(categoryField.getText(), us);

			confirmation.setText("Your task category has been created");

		} catch (SQLException e) {
			confirmation.setText("Something went wrong");

			throw e;
		}

	}
}
