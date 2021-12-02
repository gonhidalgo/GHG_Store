package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public final class UserFunctions {

	@FXML
	private Label username, userid;
	String userName;
	int userId;

	@FXML
	private ImageView customerfuncimg;

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

}
