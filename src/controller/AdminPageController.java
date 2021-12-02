package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AdminPageController {

	@FXML
	public Hyperlink orders, customers, menu;

	private Stage stage;
	private Scene scene;

	String userName;
	int userId;
	int user_id;

	@FXML
	public void manageOrders(ActionEvent event) throws IOException, SQLException {

		System.out.println("Clicked on ManageOrders");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminOrdersView.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	public void manageCustomers(ActionEvent event) throws IOException, SQLException {

		System.out.println("Clicked on manageCustomers");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminCustomersView.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	public void manageMenu(ActionEvent event) throws IOException, SQLException {

		System.out.println("Clicked on manageMenu");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminMenuView.fxml"));
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	public void logoutAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
