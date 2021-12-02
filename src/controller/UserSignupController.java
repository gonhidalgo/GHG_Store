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

public class UserSignupController {
	@FXML
	private TextField name, email, address, phone;

	@FXML
	private PasswordField password, cpassword;

	@FXML
	private Button signup;

	@FXML
	private Label nameerror, emailerror, addresserror, phoneerror, passworderror, cpassworderror;

	boolean nameIsNull = false;
	boolean emailisNull = false;
	boolean isemail = false;
	boolean addressIsNull = false;
	boolean isphone = true;
	boolean passwordIsNull = false;
	boolean confirmPassword = true;

	public void checkName(TextField name, Label nameerror, String message) {
		if (name.getText().isEmpty()) {
			nameIsNull = true;
			nameerror.setText(message);
		} else {
			nameIsNull = false;
			nameerror.setText("");
		}

	}

	// Email Validation
	public void checkEmail(TextField email, Label emailerror, String message) {

		if (email.getText().isEmpty()) {
			emailisNull = true;
			emailerror.setText(message);
		} else {
			emailisNull = false;
			checkEmailFormat(email, emailerror);
		}
	}

	public void checkEmailFormat(TextField email, Label emailerror) {
		if (email.getText().endsWith("@gmail.com") || email.getText().endsWith("@hawk.iit.edu")) {
			emailerror.setText("");
			isemail = true;
		} else {
			isemail = false;
			emailerror.setText("Please enter a valid e-mail address");
		}
	}

	// Address Validation
	public void checkAddress(TextField address2, Label addresserror2, String string) {
		// TODO Auto-generated method stub
		if (address2.getText().isEmpty()) {
			addressIsNull = true;
			addresserror.setText(string);
		} else {
			addressIsNull = false;
			addresserror.setText("");
		}
	}

	// Phone Validation
	boolean phoneIsNull = false;

	public void checkPhone(TextField phone, Label phoneerror, String message) throws SQLException {
		if (phone.getText().isEmpty() && !phone.getText().matches("^[0-9]{10}$")) {
			phoneIsNull = true;
			phoneerror.setText(message);
		} else {
			phoneIsNull = false;
		}
	}

	public void checkPassword(PasswordField password, Label passworderror, String string) {
		if (password.getText().isEmpty()) {
			passwordIsNull = true;
			passworderror.setText(string);
		} else {
			passwordIsNull = false;
			checkConfirmPassword(cpassword, cpassworderror);
			passworderror.setText("");
		}
	}

	public void checkConfirmPassword(PasswordField password2, Label cpassworderror) {
		if (!(password2.getText().equals(password.getText()))) {
			confirmPassword = false;
			cpassworderror.setText("Please re-enter your password");
		} else {
			confirmPassword = true;
			cpassworderror.setText("");
		}
	}

	public void signUp(ActionEvent event) throws IOException, SQLException {

		CustomerDAO custDBObj = new CustomerDAO();

		checkName(name, nameerror, "Please enter a name");
		checkEmail(email, emailerror, "Please enter an e-email address");
		checkAddress(address, addresserror, "Please enter a billing address");
		checkPhone(phone, phoneerror, "Please enter a contact number");
		checkPassword(password, passworderror, "Please enter a password");

		boolean flag2;
		if (nameIsNull == false && emailisNull == false && addressIsNull == false && phoneIsNull == false
				&& passwordIsNull == false && isphone == true && isemail == true && confirmPassword == true) {

			flag2 = custDBObj.insertUserDetailRecords(name.getText(), email.getText(), address.getText(),
					phone.getText(), password.getText());

			if (flag2) {
				Parent parent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
				Scene scene = new Scene(parent, 600, 500);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			}
		}

	}

	@FXML
	private void mainAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Home link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

}
