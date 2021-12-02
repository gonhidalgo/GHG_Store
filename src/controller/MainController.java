package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Hyperlink maincatalog;
	@FXML
	private Hyperlink maincontact;
	@FXML
	public Hyperlink mainadmin;
	@FXML
	private Hyperlink mainloginbutton;
	@FXML
	private Label maintitle;
	@FXML
	private ImageView iphone13, iphone12;

	@FXML
	private Button buybutton13, buybutton12, buybuttonz;

	private Stage stage;
	private Scene scene;

	@FXML
	private void aboutusAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on AboutUs");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AboutUsView.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	private void contactAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Contact");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/ContactView.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	public void UserLogin(ActionEvent event) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	public void AdminLogin(ActionEvent event) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

}
