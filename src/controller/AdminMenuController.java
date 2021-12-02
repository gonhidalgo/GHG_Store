package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MenuDAO;

public class AdminMenuController {

	@FXML
	private TableView phonetable;

	@FXML
	private TableColumn phoneid, phonename, phoneprice;

	@FXML
	private TextField phoneidtf, phonenametf, phonepricetf;
	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	public void fetchMenuDetails(String name, int id) {
		System.out.println("userid in order page (fetchOrderDetails method): " + id);
		try {
			MenuDAO MenuItem = new MenuDAO();
			refreshMenuFxTable(MenuItem.fetchMenuItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadData(ActionEvent event) throws IOException {
		MenuDAO MenuItem = new MenuDAO();
		setCellValueFromTableToTextField();

		try {
			refreshMenuFxTable(MenuItem.fetchMenuItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fillTextfields();
	}

	public void fillTextfields() {

		phonetable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				System.out.println("clicked menu table");
				setCellValueFromTableToTextField();
			}
		});
	}

	public void refreshMenuFxTable(ResultSet rs) {
		System.out.println("entered refreshMenuFxTable method");
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("phone_id", rs.getInt(1) + "");
				dataRow.put("phone_name", rs.getString(2));
				dataRow.put("phone_price", rs.getString(3));
				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		phoneid.setCellValueFactory(new MapValueFactory("phone_id"));
		phonename.setCellValueFactory(new MapValueFactory("phone_name"));
		phoneprice.setCellValueFactory(new MapValueFactory("phone_price"));

		phonetable.setItems(allData);
	}

	@FXML
	public void backAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminView.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	public void logoutAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	private void setCellValueFromTableToTextField() {
		if (phonetable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = phonetable.getSelectionModel().getSelectedItem();

			phoneidtf.setText((selectedItems.toString().split(",")[2].substring(
					selectedItems.toString().split(",")[2].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[2].indexOf("}"))).replaceFirst("\\s+", ""));
			phonenametf.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			phonepricetf.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));

		}
	}

	private void refreshTextFields() {
		phoneidtf.setText("");
		phonenametf.setText("");
		phonepricetf.setText("");
	}

	@FXML
	public void insertPhone(ActionEvent event) {
		MenuDAO MenuItem;
		String name = phonenametf.getText();
		String price = phonepricetf.getText();
		int id = Integer.parseInt(phoneidtf.getText());

		try {
			// deleting from database
			if (!(phoneidtf.getText().equalsIgnoreCase(""))) {
				MenuItem = new MenuDAO();
				MenuItem.insertPhone(id, name, price);

				// deleting from FX tableview
				refreshMenuFxTable(MenuItem.fetchMenuItems());
				dbOperationsMsg.setText("Inserted Successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("Please select a record from the table.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deletePhone(ActionEvent event) {
		MenuDAO MenuItem;

		try {
			// deleting from database
			if (!(phoneidtf.getText().equalsIgnoreCase(""))) {
				MenuItem = new MenuDAO();
				MenuItem.deletePhone(Integer.parseInt(phoneidtf.getText()));

				// deleting from FX tableview
				refreshMenuFxTable(MenuItem.fetchMenuItems());
				dbOperationsMsg.setText("Deleted Successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("Please select a record from the table.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void updatePhone(ActionEvent event) {
		String name = phonenametf.getText();
		String price = phonepricetf.getText();

		try {
			// updating in database
			if (!(name.equalsIgnoreCase("") && price.equalsIgnoreCase(""))) {
				int id = Integer.parseInt(phoneidtf.getText());

				MenuDAO MenuItem = new MenuDAO();
				MenuItem.updatePhone(id, name, price);

				// refresh FX tableview
				refreshMenuFxTable(MenuItem.fetchMenuItems());
				dbOperationsMsg.setText("Order have been updated successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("Please select a record from the table.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
