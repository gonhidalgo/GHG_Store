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
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import model.OrderDAO;

public class OrderStatusController {

	@FXML
	private TableView ordertable;

	@FXML
	private TableColumn orderid, totalprice, orderdate, orderstatus;

	@FXML
	private Label username, userid;
	int userId;
	String userName;
	ObservableList<Map> allData = null;
	int user_id;

	private Stage stage;
	private Scene scene;

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

	public void fetchOrderDetails(String name, int id) {
		System.out.println("userid in order page (fetchOrderDetails method): " + id);
		try {
			OrderDAO oDbObj = new OrderDAO();
			refreshOrderFxTable(oDbObj.fetchOrderFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadData(ActionEvent event) throws IOException {
		OrderDAO fItemDBObj = new OrderDAO();

		try {
			refreshOrderFxTable(fItemDBObj.fetchOrderFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void refreshOrderFxTable(ResultSet rs) {
		System.out.println("entered refreshOrderFxTable method");
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("order_id", rs.getInt(1) + "");
				dataRow.put("total_price", rs.getString(3));
				dataRow.put("order_status", rs.getString(4));
				dataRow.put("order_date_time", rs.getString(5));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		orderid.setCellValueFactory(new MapValueFactory("order_id"));
		totalprice.setCellValueFactory(new MapValueFactory("total_price"));
		orderstatus.setCellValueFactory(new MapValueFactory("order_status"));
		orderdate.setCellValueFactory(new MapValueFactory("order_date_time"));

		ordertable.setItems(allData);
	}

	@FXML
	public void backAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Home link");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UsermenuView.fxml"));
		Parent parent = (Parent) loader.load();
		UserMenuController cfunc = loader.getController();
		cfunc.setUserName(username.getText());
		cfunc.setUserId(user_id);
		scene = new Scene(parent);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

}
