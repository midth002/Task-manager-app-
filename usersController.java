package application_group;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class usersController extends Users {

	@FXML
	private TextField fnText;
	@FXML
	private TextField lnText;
	@FXML
	private TextField uText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField pwText;
	@FXML
	private Button btn1;
	@FXML
	private Text msg;

	@FXML
	private void insertUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

		// create a pattern and match with the provided text fields
		Pattern pattern1 = Pattern.compile("^.{8,}$");
		java.util.regex.Matcher match1 = pattern1.matcher(pwText.getText());

		Pattern pattern2 = Pattern.compile("[a-zA-Z-Z0-9][a-zA-Z0-9.]+");
		java.util.regex.Matcher match2 = pattern2.matcher(uText.getText());

		Pattern pattern3 = Pattern.compile("[a-zA-Z-Z0-9][a-zA-Z0-9.]+");
		java.util.regex.Matcher match3 = pattern3.matcher(fnText.getText());

		Pattern pattern4 = Pattern.compile("[a-zA-Z-Z0-9][a-zA-Z0-9.]+");
		java.util.regex.Matcher match4 = pattern4.matcher(lnText.getText());

		// if the text fields match, indicate that they are, or else show what was wrong
		// creating the account
		if (match3.find() && match3.group().contentEquals(fnText.getText())) {
			fnText.setStyle("-fx-border-color: green");
			emailText.setStyle("-fx-border-color: green");

		} else {
			Alert a1 = new Alert(Alert.AlertType.WARNING);
			a1.setTitle("Alert");
			a1.setHeaderText("There was a problem creating your account");
			a1.setContentText("Please enter your first name.");
			a1.showAndWait();
			fnText.setStyle("-fx-border-color: red; -fx-focus-color: red");
		}

		if (match4.find() && match4.group().contentEquals(lnText.getText())) {
			lnText.setStyle("-fx-border-color: default");
			lnText.setStyle("-fx-border-color: green");
		} else {
			Alert a2 = new Alert(Alert.AlertType.WARNING);
			a2.setTitle("Alert");
			a2.setHeaderText("There was a problem creating your account");
			a2.setContentText("Please enter your last name.");
			a2.showAndWait();
			lnText.setStyle("-fx-border-color: red; -fx-focus-color: red");
		}

		if (match2.find() && match2.group().contentEquals(uText.getText())) {
			uText.setStyle("-fx-border-color: green");
		} else {
			Alert a3 = new Alert(AlertType.WARNING);
			a3.setTitle("Alert");
			a3.setHeaderText("Problem creating your account");
			a3.setContentText("Your username must" + "\n\n  Not be blank" + "\n 2. Be unique in our system"
					+ "\n\nPlease try again.");
			a3.showAndWait();
			uText.setStyle("-fx-border-color: red; -fx-focus-color: red");
		}

		if (match1.find() && match1.group().contentEquals(pwText.getText())) {
			pwText.setStyle("-fx-border-color: green");

			try {
				userDataAccess.insertUser(uText.getText(), fnText.getText(), lnText.getText(), emailText.getText(),
						pwText.getText());

				FXMLLoader loader = new FXMLLoader(getClass().getResource("taskMangerMain.fxml"));
				Parent mainPageRoot = loader.load();

				Scene signUpScene = new Scene(mainPageRoot, 400, 600);
				signUpScene.getStylesheets().add("application.css");

				tmController tm = loader.getController();
				tm.myUserName(uText.getText());

				Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

				window.setScene(signUpScene);
				window.show();

			} catch (SQLException ex) {
				msg.setText("Error when creating an account: Already used username in the database");
				throw ex;
			}

		} else {
			Alert a4 = new Alert(Alert.AlertType.WARNING);
			a4.setTitle("Alert");
			a4.setHeaderText("Problem creating your account");
			a4.setContentText("Your password needs to be eight (8) characters in length." + "\n\nPlease try again.");
			a4.showAndWait();
			pwText.setStyle("-fx-border-color: red; -fx-focus-color: red");

		}

	}

	@FXML
	private void loginInstead(ActionEvent actionEvent) throws IOException {
		Parent signInRoot = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		Scene signInScene = new Scene(signInRoot, 400, 600);

		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

		window.setScene(signInScene);
		window.show();
	}

}
