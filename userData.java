package application_group;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class userData {

	private StringProperty first_name;
	private StringProperty last_name;
	private StringProperty userID;
	private StringProperty email;
	private StringProperty password;
	private StringProperty task_name;
	private SimpleObjectProperty<Date> task_due;
	private StringProperty description;
	private StringProperty task_category_name;
	private StringProperty sub_task;

	// constructor
	public userData() {
		this.first_name = new SimpleStringProperty();
		this.last_name = new SimpleStringProperty();
		this.userID = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.task_name = new SimpleStringProperty();
		this.task_due = new SimpleObjectProperty<>();
		this.description = new SimpleStringProperty();
		this.task_category_name = new SimpleStringProperty();
		this.sub_task = new SimpleStringProperty();
	}

	// user name getter, setter, and string property
	public String getUserID() {
		return userID.get();
	}

	public void setUserID(String userName) {
		this.userID.set(userName);
	}

	public StringProperty userIDProperty() {
		return userID;
	}

	// description getter, setter, and string property
	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public String getTaskName() {
		return task_name.get();
	}

	public void setTaskName(String taskName) {
		this.task_name.set(taskName);
	}

	public StringProperty taskNameProperty() {
		return task_name;
	}

	// date time getter, setter, and object property
	public Object getDateTime() {
		return task_due.get();
	}

	public void setDateTime(Date dateTime) {
		this.task_due.set(dateTime);
	}

	public SimpleObjectProperty<Date> dateTimeProperty() {
		return task_due;
	}

	// first name getter, setter, and string property
	public String getFirsName() {
		return first_name.get();
	}

	public void setFirstName(String firstName) {
		this.first_name.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return first_name;
	}

	// last name getter, setter, and string property
	public String getLastName() {
		return last_name.get();
	}

	public void setLastName(String lastName) {
		this.last_name.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return last_name;
	}

	// email getter, setter, and string property
	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty emailProperty() {
		return email;
	}

	// password getter, setter, and string property
	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	// task_category name getter, setter, and string property
	public String getTaskCategoryName() {
		return task_category_name.get();
	}

	public void setTaskCategoryName(String name) {
		this.task_category_name.set(name);
	}

	public StringProperty taskCategoryNameProperty() {
		return task_category_name;
	}

	// sub_task getter, setter, and string property
	public String getsubTask() {
		return sub_task.get();
	}

	public void setSubTask(String sub) {
		this.sub_task.set(sub);
	}

	public StringProperty subTaskProperty() {
		return sub_task;
	}

}
