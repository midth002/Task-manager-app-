package application_group;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class taskPageController implements Initializable {

	@FXML
	private ImageView igOptions;
	@FXML
	private AnchorPane a;
	@FXML
	private VBox taskVbox;
	@FXML
	private ImageView time;
	@FXML
	private ImageView back;
	@FXML
	private HBox hboxTime;
	@FXML
	private TextField nameTxt;
	@FXML
	private TextField descriptionTxt;
	@FXML
	private Text userText;
	@FXML
	private Button openCategories;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Text CText;
	@FXML
	private HBox timeBox;
	@FXML
	private HBox hCat;

	ComboBox<String> hour = new ComboBox<>();
	ComboBox<String> minute = new ComboBox<>();
	ComboBox<String> dayTime = new ComboBox<>();

	ComboBox<String> cb = new ComboBox<>();
	TextField sub = new TextField();

	ObservableList<String> hourList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12");
	ObservableList<String> minuteList = FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30",
			"35", "40", "45", "50", "55");
	ObservableList<String> daytimeList = FXCollections.observableArrayList("AM", "PM");

	public void myUserName(String text) {
		userText.setText(text);
	}

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rSet = null;

	public taskPageController() {
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

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		datePicker.setValue(LocalDate.now());
		cb.setValue("Default");

		hour.getItems().addAll(hourList);
		minute.getItems().addAll(minuteList);
		dayTime.getItems().addAll(daytimeList);

		hour.setValue("12");
		minute.setValue("00");
		dayTime.setValue("AM");

		hour.setStyle("-fx-background-color: #ADFEB4;");
		minute.setStyle("-fx-background-color: #ADFEB4;");
		dayTime.setStyle("-fx-background-color: #ADFEB4;");

		timeBox.getChildren().addAll(hour, minute, dayTime);

		HBox.setMargin(hour, new Insets(5, 5, 5, 5));
		HBox.setMargin(minute, new Insets(5, 5, 5, 5));
		HBox.setMargin(dayTime, new Insets(5, 5, 5, 5));

	}

	@FXML
	private void submitTask(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException {

		String us = userText.getText();

		String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		String timeValue = hour.getValue() + ":" + minute.getValue() + " " + dayTime.getValue();

		String input = date + " " + timeValue;

		String category = cb.getValue();

		try {
			userDataAccess.insertTask(us, nameTxt.getText(), descriptionTxt.getText(), input, category);
			CText.setText(nameTxt.getText() + " Task has been created!");
			CText.setFill(Color.MEDIUMSEAGREEN);

		} catch (SQLException ex) {
			CText.setText(nameTxt.getText() + " Task couldn't be created!");
			CText.setFill(Color.RED);
			throw ex;
		}

	}

	public void backToMainPage() {

		back.setOnMouseClicked((MouseEvent ev) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("taskMangerMain.fxml"));
				Parent mainPageRoot = loader.load();

				Scene signUpScene = new Scene(mainPageRoot, 400, 600);
				signUpScene.getStylesheets().add("application.css");

				tmController tm = loader.getController();
				tm.myUserName(userText.getText());

				Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();

				window.setScene(signUpScene);
				window.show();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		});

	}

	@FXML
	protected void putTaskIntoCategory(ActionEvent event) {
		String showTaskSQL = "SELECT category_name FROM TaskCategory WHERE userID = ?";
		String user = userText.getText();

		hCat.getChildren().remove(openCategories);
		hCat.getChildren().add(cb);

		cb.setStyle("-fx-background-color: white;");

		try {

			pst = con.prepareStatement(showTaskSQL);
			pst.setString(1, user);
			rSet = pst.executeQuery();

			while (rSet.next()) {
				cb.getItems().addAll(rSet.getString("category_name"));
			}
		} catch (SQLException e) {

		}
	}

}
