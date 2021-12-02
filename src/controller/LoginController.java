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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomerDAO;

public class LoginController {
	@FXML
	private Label lbltitle;
	@FXML
	private TextField lblusername;
	@FXML
	private TextField lblpassword;
	@FXML
	private Label loginStatus;
	@FXML
	private Button loginbutton, signupbutton;

	int user_id;
	private Stage stage;
	private Scene scene;

	public String getusername() {
		return lblusername.getText();
	}

	@FXML
	public void loginButtonAction(ActionEvent event) throws Exception {

		System.out.println("Clicked on Customer Login button");
		CustomerDAO customer = new CustomerDAO();

		boolean flag = customer.searchUserDetailDB(lblusername.getText(), lblpassword.getText());
		System.out.println("flag = " + flag);

		if (flag) {
			user_id = customer.getIdFromUserDB(lblusername.getText(), lblpassword.getText());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UsermenuView.fxml"));
			Parent parent = (Parent) loader.load();
			UserMenuController cfunc = loader.getController();
			cfunc.setUserName(lblusername.getText());
			System.out.println("user_id in customer login page: " + user_id);
			cfunc.setUserId(user_id);
			scene = new Scene(parent);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();

		} else {
			loginStatus.setText("Invalid credentials! Please try again!");
		}
	}

	public void signUp(ActionEvent event) throws IOException, SQLException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/UserSignUp.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

}
