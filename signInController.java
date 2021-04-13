package application_group;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class signInController {

	@FXML
	private Hyperlink signUpLink;

	@FXML
	private TextField uText;
	@FXML
	private TextField pwText;
	@FXML
	private HBox hSign;
	@FXML
	private Text errorTxt;
	@FXML
	private Button signInBtn;

	@FXML
	private void signUpInstead(ActionEvent actionEvent) throws IOException {
		Parent signUpRoot = FXMLLoader.load(getClass().getResource("signUp.fxml"));
		Scene signUpScene = new Scene(signUpRoot, 400, 600);

		Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

		window.setScene(signUpScene);
		window.show();
	}

	public String userActive() {
		String userAct = new String(uText.getText());
		return userAct;
	}

	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@FXML
	private Label e;

	@FXML
	void handleButtonAction(ActionEvent event) {

		if (event.getSource() == signInBtn) {
			// login here
			if (logIn().equals("Success")) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("taskMangerMain.fxml"));
					Parent mainPageRoot = loader.load();

					Scene signUpScene = new Scene(mainPageRoot, 400, 600);
					signUpScene.getStylesheets().add("application.css");

					tmController tm = loader.getController();
					tm.myUserName(uText.getText());

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

					window.setScene(signUpScene);
					window.show();
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				}
			}
		}
	}

	public signInController() {
		try {
			con = DatabaseUtility.dbConnect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// use string to check for status
	public String logIn() {
		String status = "Success";
		String username = uText.getText();
		String password = pwText.getText();

		if (uText.getText().toString().isEmpty() && pwText.getText().toString().isEmpty() == false) {

			uText.setStyle("-fx-border-color: red; -fx-background-color: white");
			pwText.setStyle("-fx-border-color: green; -fx-background-color: white");

			Alert alert1 = new Alert(Alert.AlertType.WARNING);
			alert1.setTitle("Warning");
			alert1.setHeaderText("There was a problem signing in to your account");
			alert1.setContentText("Please enter your username.");
			alert1.showAndWait();

		} else if (uText.getText().toString().isEmpty() == false && pwText.getText().toString().isEmpty()) {

			pwText.setStyle("-fx-border-color: red; -fx-background-color: white");
			uText.setStyle("-fx-border-color: green; -fx-background-color: white");

			Alert alert1 = new Alert(Alert.AlertType.WARNING);
			alert1.setTitle("Warning");
			alert1.setHeaderText("There was a problem signing in to your account");
			alert1.setContentText("Please enter your password.");
			alert1.showAndWait();

		} else if (uText.getText().toString().isEmpty() && pwText.getText().toString().isEmpty()) {

			Alert alert1 = new Alert(Alert.AlertType.WARNING);

			uText.setStyle("-fx-border-color: red; -fx-background-color: white");
			pwText.setStyle("-fx-border-color: red; -fx-background-color: white");

			alert1.setTitle("Warning");
			alert1.setHeaderText("There was a problem signing in to your account");
			alert1.setContentText("Please enter in your username and password.");
			alert1.showAndWait();

			setLblError(Color.TOMATO, "Empty credentials");
			status = "Error";
		}

		if (pwText.getText().toString() != null && uText.getText().toString() != null) {
			// query
			String sql = "SELECT * FROM Users Where userID = ? and PASSWORD = ?";
			try {
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				resultSet = preparedStatement.executeQuery();
				if (!resultSet.next()) {
					setLblError(Color.TOMATO, "");
					PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
					pauseTransition.setOnFinished(
							e -> setLblError(Color.TOMATO, "Invalid username and/or password. Please try again"));
					pauseTransition.play();

					setLblError(Color.TOMATO, "Invalid username and/or password. Please try again.");
					status = "Error";
				} else {
					setLblError(Color.GREEN, "Login was approved");
				}
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
				status = "Exception";
			}
		}
		return status;
	}

	private void setLblError(Color color, String text) {
		e.setTextFill(color);
		e.setText(text);
		System.out.println(text);
	}

	@FXML
	private void validateUserName() {
		if (uText.getText().toString().isEmpty() == false) {
			uText.setStyle("-fx-border-color: green; -fx-background-color: white");
		} else {
			uText.setStyle("-fx-border-color: red; -fx-background-color: white");
		}
	}

	@FXML
	private void validatePassword() {
		if (pwText.getText().toString().isEmpty() == false) {
			pwText.setStyle("-fx-border-color: green; -fx-background-color: white");
		} else {
			pwText.setStyle("-fx-border-color: red; -fx-background-color: white");
		}
	}

}
