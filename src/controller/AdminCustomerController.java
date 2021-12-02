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
import model.CustomerDAO;

public class AdminCustomerController {

	@FXML
	private TableView customertable;

	@FXML
	private TableColumn custid, custname, custmail, custadd, custphone, custpass;

	@FXML
	private TextField custidtf, custnametf, custmailtf, custaddtf, custphonetf, custpasstf;
	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@FXML
	public void loadData(ActionEvent event) throws IOException {
		CustomerDAO CustomerItem = new CustomerDAO();
		setCellValueFromTableToTextField();

		try {
			refreshCustomerTable(CustomerItem.fetchCustomer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fillTextfields();
	}

	public void fillTextfields() {

		customertable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				System.out.println("clicked menu table");
				setCellValueFromTableToTextField();
			}
		});
	}

	public void refreshCustomerTable(ResultSet rs) {
		System.out.println("entered refreshCustomerTable method");
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("cust_id", rs.getInt(1) + "");
				dataRow.put("cust_name", rs.getString(2));
				dataRow.put("cust_email", rs.getString(3));
				dataRow.put("cust_address", rs.getString(4));
				dataRow.put("cust_phone", rs.getString(5));
				dataRow.put("cust_passwd", rs.getString(6));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		custid.setCellValueFactory(new MapValueFactory("cust_id"));
		custname.setCellValueFactory(new MapValueFactory("cust_name"));
		custmail.setCellValueFactory(new MapValueFactory("cust_email"));
		custadd.setCellValueFactory(new MapValueFactory("cust_address"));
		custphone.setCellValueFactory(new MapValueFactory("cust_phone"));
		custpass.setCellValueFactory(new MapValueFactory("cust_passwd"));

		customertable.setItems(allData);
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
		if (customertable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = customertable.getSelectionModel().getSelectedItem();

			custidtf.setText((selectedItems.toString().split(",")[4]
					.substring(selectedItems.toString().split(",")[4].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			custnametf.setText((selectedItems.toString().split(",")[3]
					.substring(selectedItems.toString().split(",")[3].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			custmailtf.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			custaddtf.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			custphonetf.setText((selectedItems.toString().split(",")[5].substring(
					selectedItems.toString().split(",")[5].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[5].indexOf("}"))).replaceFirst("\\s+", ""));
			custpasstf.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));

		}
	}

	private void refreshTextFields() {
		custidtf.setText("");
		custnametf.setText("");
		custmailtf.setText("");
		custaddtf.setText("");
		custphonetf.setText("");
		custpasstf.setText("");
	}

	@FXML
	public void deleteCustomer(ActionEvent event) {
		CustomerDAO CustomerItem;

		try {
			// deleting from database
			if (!(custidtf.getText().equalsIgnoreCase(""))) {
				CustomerItem = new CustomerDAO();
				CustomerItem.deleteCustomer(Integer.parseInt(custidtf.getText()));

				// deleting from FX tableview
				refreshCustomerTable(CustomerItem.fetchCustomer());
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
	public void updateCustomer(ActionEvent event) {
		int id = Integer.parseInt(custidtf.getText());
		String name = custnametf.getText();
		String mail = custmailtf.getText();
		String address = custaddtf.getText();
		String phone = custphonetf.getText();
		String password = custpasstf.getText();

		try {
			// updating in database
			if (!(mail.equalsIgnoreCase("") && name.equalsIgnoreCase(""))) {
				CustomerDAO CuetomerItem = new CustomerDAO();
				CuetomerItem.updateCustomer(id, name, mail, address, phone, password);
				// refresh FX tableview
				refreshCustomerTable(CuetomerItem.fetchCustomer());
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
