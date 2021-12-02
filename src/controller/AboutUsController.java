package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AboutUsController {

	@FXML
	private Label aboutustitle;
	@FXML
	private Button backbutton;

	private Stage stage;
	private Scene scene;

	@FXML
	public void MainAction(ActionEvent event) {
		try {
			System.out.println("Clicked on Catalog");
			Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			scene = new Scene(parent);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
