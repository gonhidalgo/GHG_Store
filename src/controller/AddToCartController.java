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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import model.MenuDAO;
import model.OrderDAO;

public class AddToCartController {

	@FXML
	private TableView phonetable;
	@FXML
	private TableColumn phoneid, phonename, phoneprice; // menu table
	@FXML
	private TableView cartTable;

	@FXML
	private TableColumn cartid, cartitem, cartquantity; // cart table
	@FXML
	private ChoiceBox<Integer> quantity;
	@FXML
	private TextField totalprice;
	@FXML
	private Label errorMsg, orderConfirmMsg, userid, username;
	Double p = 0.0, tot = 0.0;
	int userId;
	String userName;

	int user_id;

	ObservableList<Map> allData = null;
	ObservableList<Map> cartData = FXCollections.observableArrayList();
	ObservableList<Map> cartDataQty = FXCollections.observableArrayList();
	ObservableList list = FXCollections.observableArrayList();

	private Stage stage;
	private Scene scene;

	public void setUserName(String uname) {
		username.setText(uname);
		userName = username.getText();
		System.out.println("username in cust func page: " + username);
	}

	public void setUserId(int id) {
		userid.setText(id + "");
		userId = Integer.parseInt(userid.getText());
		System.out.println("userid in cust func page: " + userid);
		System.out.println("uid in cust func page: " + userId);
	}

	@FXML
	private void loadData(ActionEvent event) throws IOException {
		quantity.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
		MenuDAO fItemDBObj = new MenuDAO();

		try {
			refreshMenuFxTable(fItemDBObj.fetchMenuItems());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void refreshMenuFxTable(ResultSet rs) {
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
	private void backAction(ActionEvent event) throws IOException {
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

	@FXML
	private void addToCart(ActionEvent event) throws IOException {
		Object selectedItems = phonetable.getSelectionModel().getSelectedItem();
		if (quantity.getValue() != null && selectedItems != null) {
			Map<String, String> cartdatarow = new HashMap<>();
			cartdatarow.put("phonename", (selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)));
			cartdatarow.put("phoneprice",
					((selectedItems.toString().split(",")[1]
							.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+",
									"")));
			cartdatarow.put("phoneid",
					(selectedItems.toString().split(",")[2].substring(
							selectedItems.toString().split(",")[2].lastIndexOf("=") + 1,
							selectedItems.toString().split(",")[2].indexOf("}"))));
			cartdatarow.put("phonequantity", quantity.getValue().toString());
			cartData.add(cartdatarow);

			cartid.setCellValueFactory(new MapValueFactory("phoneid"));
			cartitem.setCellValueFactory(new MapValueFactory("phonename"));
			cartquantity.setCellValueFactory(new MapValueFactory("phonequantity"));
			cartTable.setItems(cartData);

			// Calculate the total price

			tot += (Double.parseDouble(cartdatarow.get("phoneprice"))
					* Integer.parseInt(cartdatarow.get("phonequantity")));
			Double pr = Math.floor(tot * 100) / 100;
			totalprice.setText(Double.toString(pr));

		} else if (quantity.getValue() == null) {
			errorMsg.setText("Please select a quantity");
		} else if (selectedItems == null) {
			errorMsg.setText("Please select a phone");
		}
	}

	@FXML
	private void orderAction(ActionEvent event) throws IOException {

		if (!((totalprice.getText().equalsIgnoreCase(""))) && Double.parseDouble(totalprice.getText()) > 0.0) {
			OrderDAO od = new OrderDAO();
			od.insertOrderRecords(Float.parseFloat(totalprice.getText()), userId, userName);
			orderConfirmMsg.setText("Order placed successfully!");

		} else {
			errorMsg.setText("Please add a menu item to cart.");
		}

	}

}
