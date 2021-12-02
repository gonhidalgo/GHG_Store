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
import model.OrderDAO;

public class AdminOrdersController {

	@FXML
	private TableView ordertable;

	@FXML
	private TableColumn orderid, totalprice, orderdate, orderstatus, userid;

	@FXML
	private TextField orderidtf, totalpricetf, orderdatetf, orderstatustf, useridtf;
	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@FXML
	public void loadData(ActionEvent event) throws IOException {
		OrderDAO OrderItem = new OrderDAO();
		setCellValueFromTableToTextField();

		try {
			refreshOrderFxTable(OrderItem.fetchOrderFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fillTextfields();
	}

	public void fillTextfields() {

		ordertable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				System.out.println("clicked menu table");
				setCellValueFromTableToTextField();
			}
		});
	}

	public void refreshOrderFxTable(ResultSet rs) {
		System.out.println("entered refreshOrderFxTable method");
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("order_id", rs.getInt(1) + "");
				dataRow.put("user_id", rs.getInt(2) + "");
				dataRow.put("total_price", rs.getString(3));
				dataRow.put("order_status", rs.getString(4));
				dataRow.put("order_date_time", rs.getString(5));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		orderid.setCellValueFactory(new MapValueFactory("order_id"));
		userid.setCellValueFactory(new MapValueFactory("user_id"));
		totalprice.setCellValueFactory(new MapValueFactory("total_price"));
		orderstatus.setCellValueFactory(new MapValueFactory("order_status"));
		orderdate.setCellValueFactory(new MapValueFactory("order_date_time"));

		ordertable.setItems(allData);
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
		if (ordertable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = ordertable.getSelectionModel().getSelectedItem();

			orderidtf.setText((selectedItems.toString().split(",")[4].substring(
					selectedItems.toString().split(",")[4].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[4].indexOf("}"))).replaceFirst("\\s+", ""));
			useridtf.setText((selectedItems.toString().split(",")[3]
					.substring(selectedItems.toString().split(",")[3].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			totalpricetf.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			orderstatustf.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			orderdatetf.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));

		}
	}

	private void refreshTextFields() {
		orderidtf.setText("");
		useridtf.setText("");
		totalpricetf.setText("");
		orderstatustf.setText("");
		orderdatetf.setText("");
	}

	@FXML
	public void deleteOrder(ActionEvent event) {
		OrderDAO OrderItem;

		try {
			// deleting from database
			if (!(orderidtf.getText().equalsIgnoreCase(""))) {
				OrderItem = new OrderDAO();
				OrderItem.deleteOrder(Integer.parseInt(orderidtf.getText()));

				// deleting from FX tableview
				refreshOrderFxTable(OrderItem.fetchOrderFromDB());
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
	public void updateOrder(ActionEvent event) {
		String price = totalpricetf.getText();
		String status = orderstatustf.getText();
		String date = orderdatetf.getText();

		try {
			// updating in database
			if (!(status.equalsIgnoreCase("") && date.equalsIgnoreCase(""))) {
				int order = Integer.parseInt(orderidtf.getText());
				int user = Integer.parseInt(useridtf.getText());
				// float price = Float.parseFloat(totalpricetf.getText());
				OrderDAO fItemDBObj = new OrderDAO();
				fItemDBObj.updateOrder(order, user, price, status, date);

				// refresh FX tableview
				refreshOrderFxTable(fItemDBObj.fetchOrderFromDB());
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
