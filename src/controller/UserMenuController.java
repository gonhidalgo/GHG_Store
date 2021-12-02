package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomerDAO;

public class UserMenuController {
	@FXML
	private TextField name, email, address, phone;

	@FXML
	private PasswordField password, cpassword;

	@FXML
	private Button order;

	private Stage stage;
	private Scene scene;

	@FXML
	private Label username, userid;
	String userName;
	int userId;
	int user_id;

	@FXML
	public void setUserName(String uname) {
		username.setText(uname);
		userName = username.getText();
		System.out.println("username in cust func page: " + username);
	}

	@FXML
	public void setUserId(int id) {
		userid.setText(id + "");
		userId = Integer.parseInt(userid.getText());
		System.out.println("userid in cust func page: " + userid);
		System.out.println("uid in cust func page: " + userId);
	}

	public void orderAction(ActionEvent event) throws IOException, SQLException {

		CustomerDAO customer = new CustomerDAO();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddToCartView.fxml"));
		Parent parent = (Parent) loader.load();
		AddToCartController cfunc = loader.getController();
		cfunc.setUserName(userName);
		System.out.println("user_id in customer login page: " + user_id);
		cfunc.setUserId(user_id);
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	public void checkOrderAction(ActionEvent event) throws IOException, SQLException {

		CustomerDAO customer = new CustomerDAO();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderStatusView.fxml"));
		Parent parent = (Parent) loader.load();
		OrderStatusController cfunc = loader.getController();
		cfunc.setUserName(userName);
		System.out.println("user_id in customer login page: " + user_id);
		cfunc.setUserId(user_id);
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		// Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		// stage.setScene(new Scene(parent));
		stage.setScene(scene);
		stage.show();

	}

}
