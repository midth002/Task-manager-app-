package application_group;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class tmController {

	@FXML
	private Button addTaskBtn;
	@FXML
	private Button optionsBtn;
	@FXML
	private VBox vb;
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane a;
	@FXML
	private AnchorPane anc;
	@FXML
	private ScrollPane scroll;
	@FXML
	private VBox vTask;
	@FXML
	private ImageView plus;
	@FXML
	private ImageView menu;
	@FXML
	private ComboBox combo;
	@FXML
	private ListView<String> lv;
	@FXML
	private VBox showTaskBox;
	@FXML
	private Text userID;
	@FXML
	private HBox taskBox;
	@FXML
	private Button categoryBtn;
	@FXML
	private HBox categoryBox;
	@FXML
	private Text t1;
	@FXML
	private Text showTime;

	Button showTasks = new Button("Show Tasks");

	SimpleDateFormat code12Hours = new SimpleDateFormat("HH:mm aa"); // 12 hour format

	ComboBox<String> taskCategory = new ComboBox<>();

	public static final ObservableList<String> taskList = FXCollections.observableArrayList();

	public static final ObservableList<String> subTaskList = FXCollections.observableArrayList();

	public static final ObservableList<String> completion = FXCollections.observableArrayList();

	String taskListItem;

	ListView<String> slv = new ListView<>();

	ListView<String> completeList = new ListView<>();

	public void myUserName(String text) {

		userID.setText(text);
	}

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rSet = null;

	public tmController() {
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

	@FXML
	public void showAllTask(ActionEvent evt) throws SQLException, ClassNotFoundException {

		String user = userID.getText();
		lv.getItems().clear();
		lv.setItems(taskList);

		lv.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(String taskListItem) {
				BooleanProperty observable = new SimpleBooleanProperty();
				observable.addListener((obs, wasSeleced, isNowSelected) -> {
					if (isNowSelected) {

						try {

							userDataAccess.deleteTask(taskListItem, user);
							String str1 = "Task has been checked!";
							t1.setText(str1);
							t1.setFill(Color.MEDIUMSEAGREEN);
							taskList.remove(taskListItem);

							userDataAccess.insertCompletedTask(taskListItem, user);

						} catch (SQLException ex) {

						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});

				return observable;
			}
		}));

		String showTaskSQL = "SELECT name FROM Task WHERE userID = ?";
		lv.getItems().clear();

		try {

			pst = con.prepareStatement(showTaskSQL);
			pst.setString(1, user);
			rSet = pst.executeQuery();

			while (rSet.next()) {

				taskList.addAll(rSet.getString(1));

			}
		} catch (SQLException e) {

		}

		lv.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					taskDetailsPage();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			public void taskDetailsPage() throws SQLException, ClassNotFoundException {
				String selectedItem = lv.getSelectionModel().getSelectedItem();
				slv.setItems(subTaskList);

				Button addSubTask = new Button("Add Sub Task");

				Text date_time = new Text("Due Date");

				Text t1 = new Text("Task Name");
				Text t2 = new Text("Details");
				Text t3 = new Text("Subtasks");
				Button deleteTask = new Button("Delete Task");
				deleteTask.setStyle("-fx-background-color: #ff4d4d;");
				deleteTask.setFont(Font.font("Cambria", 13));
				deleteTask.setTextFill(Color.WHITE);

				t1.setFont(Font.font("Cambria", 13));
				t2.setFont(Font.font("Cambria", 13));
				t3.setFont(Font.font("Cambria", 13));

				Text tf1 = new Text();
				tf1.setText(selectedItem);

				Text tf2 = new Text();
				Text tf3 = new Text();
				Text text1 = new Text();

				addSubTask.setStyle("-fx-background-color:  #00b300;");
				addSubTask.setTextFill(Color.WHITE);
				addSubTask.setFont(Font.font("Cambria", 13));
				tf1.setStyle("-fx-border-color: white");
				tf2.setStyle("-fx-border-color: white");

				Button clBtn = new Button("Close");

				HBox hb = new HBox(deleteTask, text1);

				clBtn.setStyle("-fx-background-color: #3366cc; -fx-border-color: #3366cc;");
				clBtn.setFont(Font.font("Cambria", 13));
				VBox subV = new VBox(addSubTask);
				VBox tv = new VBox(clBtn, t1, tf1, date_time, tf3, t2, tf2, subV, slv, hb);
				tv.setStyle("-fx-background-color: white; -fx-border-color: #cce6ff");

				VBox.setMargin(t1, new Insets(5, 5, 5, 5));
				VBox.setMargin(t2, new Insets(5, 5, 5, 5));
				VBox.setMargin(t3, new Insets(5, 5, 5, 5));
				VBox.setMargin(tf1, new Insets(5, 5, 5, 5));
				VBox.setMargin(tf2, new Insets(5, 5, 5, 5));
				VBox.setMargin(clBtn, new Insets(5, 5, 5, 310));
				VBox.setMargin(tf3, new Insets(5, 5, 5, 5));
				VBox.setMargin(date_time, new Insets(5, 5, 5, 5));
				VBox.setMargin(subV, new Insets(5, 5, 5, 5));

				HBox.setMargin(deleteTask, new Insets(5, 5, 5, 5));
				HBox.setMargin(text1, new Insets(5, 5, 5, 5));

				tv.setPrefSize(382, 400);
				anc.getChildren().add(tv);
				AnchorPane.setLeftAnchor(tv, 11.0);
				AnchorPane.setTopAnchor(tv, 10.0);

				String selectDescription = "SELECT * FROM Task WHERE userID = ? AND name = ?";
				String user = userID.getText();

				try {
					pst = con.prepareStatement(selectDescription);
					pst.setString(1, user);
					pst.setString(2, selectedItem);
					rSet = pst.executeQuery();

					while (rSet.next()) {
						tf2.setText(rSet.getString("description"));
					}

				} catch (SQLException ex) {

				}

				String showDate_Time = "SELECT dueDate FROM Task WHERE name = ?";

				try {

					pst = con.prepareStatement(showDate_Time);
					pst.setString(1, selectedItem);
					rSet = pst.executeQuery();

					while (rSet.next()) {
						tf3.setText(rSet.getString(1));
					}

				} catch (SQLException e) {

				}

				String showTaskSQL = "SELECT name FROM subTask WHERE taskName = ?";

				try {

					pst = con.prepareStatement(showTaskSQL);
					pst.setString(1, selectedItem);
					rSet = pst.executeQuery();

					while (rSet.next()) {
						subTaskList.add(rSet.getString(1));

					}
				} catch (SQLException e) {

				}

				slv.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(String subtaskListItem) {
						BooleanProperty observable = new SimpleBooleanProperty();
						observable.addListener((obs, wasSeleced, isNowSelected) -> {
							if (isNowSelected) {

								try {

									userDataAccess.deleteSubTask(subtaskListItem, selectedItem, userID.getText());

									t1.setText(subtaskListItem + " task has been checked!");
									t1.setFill(Color.MEDIUMSEAGREEN);
									taskList.remove(taskListItem);

								} catch (SQLException ex) {

								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						});

						return observable;
					}
				}));

				addSubTask.setOnAction(eerr -> {

					String us = userID.getText();

					subV.getChildren().remove(addSubTask);
					TextField sub = new TextField();
					sub.setPromptText("Add subtask name");
					sub.setPrefWidth(150);

					Button removeSubTask = new Button("X");
					Button addBtn = new Button("+");
					removeSubTask.setStyle("-fx-background-color: white;");
					sub.setStyle("-fx-background-color: white;");

					HBox h = new HBox(sub, removeSubTask, addBtn);
					subV.getChildren().add(h);

					addBtn.setStyle("-fx-background-color: #00b300;");
					addBtn.setFont(Font.font("Cambria", 12));
					addBtn.setTextFill(Color.WHITE);

					addBtn.setOnAction(ettt -> {

						try {
							userDataAccess.insertSubTask(sub.getText(), selectedItem, us);
							t1.setText(sub.getText() + " sub task was created");
							t1.setFill(Color.MEDIUMSEAGREEN);
							subV.getChildren().remove(h);
							h.getChildren().clear();
							subV.getChildren().add(addSubTask);

						} catch (SQLException | ClassNotFoundException e) {
							t1.setText(sub.getText() + " sub task couldn't be created");
							t1.setFill(Color.RED);

						}
					});

					removeSubTask.setOnAction(ev -> {
						subV.getChildren().remove(h);
						subV.getChildren().add(addSubTask);
					});

				});

				clBtn.setOnAction(even -> {
					tv.getChildren().removeAll(tv.getChildren());
					slv.getItems().clear();
					anc.getChildren().remove(tv);
				});

				deleteTask.setOnAction(eeerrt -> {
					try {

						userDataAccess.deleteTask(selectedItem, user);
						String str1 = "Task has been Deleted";
						text1.setText(str1);
						text1.setFill(Color.MEDIUMSEAGREEN);
						taskList.remove(selectedItem);

					} catch (SQLException ex) {

						String str1 = "Task has been Deleted";
						text1.setText(str1);
						text1.setFill(Color.MEDIUMSEAGREEN);
						taskList.remove(selectedItem);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			}

		});

	}

	public void openTaskPage() {

		plus.setOnMouseClicked((MouseEvent ev) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("taskpage.fxml"));
				Parent taskPageRoot = loader.load();

				Scene taskScene = new Scene(taskPageRoot, 400, 600);
				taskScene.getStylesheets().add("application.css");

				taskPageController tp = loader.getController();
				tp.myUserName(userID.getText());

				Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();

				window.setScene(taskScene);
				window.show();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		});

	}

	public void openOptionsMenu() {

		menu.setOnMouseClicked((MouseEvent e) -> {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("options.fxml"));
				Parent optionPageRoot = loader.load();

				Scene optionScene = new Scene(optionPageRoot, 400, 600);
				optionScene.getStylesheets().add("application.css");

				optionsController oc = loader.getController();
				oc.myUserName(userID.getText());

				Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

				window.setScene(optionScene);
				window.show();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		});

	}

	@FXML
	public void showCompletedTasks(ActionEvent actionEvent) {
		VBox v = new VBox();

		Text completedText = new Text("Completed");
		completedText.setFont(Font.font("Cambria", 15));
		completedText.setFill(Color.WHITE);
		Text text = new Text();

		Button backToTasks = new Button("close");

		backToTasks.setStyle("-fx-background-color: #3366cc;");
		backToTasks.setFont(Font.font("Cambria", 14));

		completeList.setItems(completion);

		v.getChildren().addAll(completedText, completeList, text, backToTasks);
		anc.getChildren().add(v);

		VBox.setMargin(completeList, new Insets(5, 5, 5, 5));
		VBox.setMargin(completedText, new Insets(10, 20, 10, 10));
		VBox.setMargin(backToTasks, new Insets(5, 5, 5, 300));
		VBox.setMargin(text, new Insets(5, 5, 5, 5));

		v.setStyle("-fx-background-color: #4d4d4d;");
		v.setPrefSize(400, 275);
		AnchorPane.setTopAnchor(v, 240.0);

		backToTasks.setOnAction(evt -> {
			anc.getChildren().remove(v);
			completeList.getItems().clear();
		});

		String user = userID.getText();
		String showcompletedtask = "SELECT task_name FROM Completed WHERE user_ID = ?";

		try {

			pst = con.prepareStatement(showcompletedtask);
			pst.setString(1, user);
			rSet = pst.executeQuery();

			while (rSet.next()) {
				completion.add(rSet.getString(1));

			}
		} catch (SQLException e) {

		}

		completeList.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(String completedItem) {
				BooleanProperty observable = new SimpleBooleanProperty();
				observable.addListener((obs, wasSeleced, isNowSelected) -> {
					if (isNowSelected) {

						String user = userID.getText();
						String clearCompleted = "DELETE FROM Completed WHERE task_name = ? AND user_ID = ?";
						try {

							pst = con.prepareStatement(clearCompleted);
							pst.setString(1, completedItem);
							pst.setString(2, user);
							rSet = pst.executeQuery();
							text.setText("The completed Task has been deleted");
							text.setFill(Color.WHITE);
							completion.remove(completedItem);

						} catch (SQLException ex) {

							text.setText("The completed Task has been deleted");
							text.setFill(Color.WHITE);
							completion.remove(completedItem);
						}

					}

				});

				return observable;
			}
		}));

	}

	@FXML
	protected void TaskIntoCategory(ActionEvent event) {
		String showTaskSQL = "SELECT category_name FROM TaskCategory WHERE userID = ?";
		String user = userID.getText();
		Button button = new Button("Filter");
		lv.setItems(taskList);

		categoryBox.getChildren().remove(categoryBtn);
		categoryBox.getChildren().addAll(taskCategory, button);
		taskCategory.setPromptText("filter category");
		button.setFont(Font.font("Cambria", 14));
		button.setTextFill(Color.WHITE);

		button.setPrefWidth(150);
		button.setPrefHeight(28);
		taskCategory.setPrefWidth(175);

		HBox.setMargin(button, new Insets(2, 2, 2, 2));
		HBox.setMargin(taskCategory, new Insets(2, 2, 2, 2));

		taskCategory.setStyle("-fx-background-color: #ADFEB4");
		button.setStyle("-fx-background-color: #3366cc");

		try {
			pst = con.prepareStatement(showTaskSQL);
			pst.setString(1, user);
			rSet = pst.executeQuery();

			while (rSet.next()) {

				taskCategory.getItems().addAll(rSet.getString(1));

			}
		} catch (SQLException e) {

		}

		lv.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(String taskListItem) {
				BooleanProperty observable = new SimpleBooleanProperty();
				observable.addListener((obs, wasSeleced, isNowSelected) -> {
					if (isNowSelected) {

						try {

							userDataAccess.deleteTask(taskListItem, user);
							t1.setText("task has been checked!");
							t1.setFill(Color.MEDIUMSEAGREEN);
							taskList.remove(taskListItem);

							userDataAccess.insertCompletedTask(taskListItem, user);

						} catch (SQLException ex) {

						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});

				return observable;
			}
		}));

		button.setOnAction(err -> {
			String catname = taskCategory.getValue();
			String category = "SELECT name from Task where category_name = ? AND userID = ?";
			lv.getItems().clear();
			try {

				pst = con.prepareStatement(category);
				pst.setString(1, catname);
				pst.setString(2, user);
				rSet = pst.executeQuery();

				while (rSet.next()) {

					taskList.add(rSet.getString(1));

				}
			} catch (SQLException ex) {
			}
		});
	}

}
