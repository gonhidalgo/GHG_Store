package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminLoginController {

	@FXML
	private Label lbltitle;
	@FXML
	private TextField lblusername;
	@FXML
	private TextField lblpassword;
	@FXML
	private Label lbldesc;

	@FXML
	public void AdminLogin(ActionEvent event) {
		try {
			if (lblusername.getText().equals("admin") && lblpassword.getText().equals("admin")) {
				Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminView.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);

				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

			} else {
				lbldesc.setText("Login Failed. Please, try again");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
