package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderDAO {
	// Declare DB objects
	DBConnect dbconn = null;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	// constructor
	public OrderDAO() { // create db object instance
		dbconn = new DBConnect();
	}

	// Create table for storing Customer's Order Details
	public void createOrderDBTable() {
		try {

			// Open a connection
			System.out.println("Connecting to database to create OrderDB Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating OrderDB table in given database...");

			stmt = dbconn.connect().createStatement();

			String sql = "CREATE TABLE GHG_OrderTable " + "(order_id INTEGER NOT NULL AUTO_INCREMENT, "
					+ "user_id INTEGER, " + "total_price float, " + "order_status," + "order_date_time"
					+ "FOREIGN KEY (user_id) REFERENCES  (user_id)," + " PRIMARY KEY(order_id))";

			stmt.executeUpdate(sql);
			System.out.println("Created OrderDB table in given database...");
			dbconn.connect().close(); // close db connection
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	// Method to insert values to the table created previously
	public void insertOrderRecords(float totalPrice, int userid, String username) {
		try {
			stmt = dbconn.connect().createStatement();
			String sql = null;
			String orderStatus = "ORDERED";
			String currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			System.out.println("The records are now going to be inserted...");
			// loop to look for the values we wanted to insert in the db 2021/11/12 13:14:05

			sql = "INSERT INTO GHG_OrderTable" + "(user_id, total_price, order_status, order_date_time)"
					+ " VALUES ( (SELECT user_id  FROM GHG_CustomerTable WHERE username = '" + username + "'),'"
					+ totalPrice + "', '" + orderStatus + "', '" + currentDate + "')";
			// Example of working query
			// INSERT INTO GHG_OrderTable(user_id, total_price, order_status,
			// order_date_time) VALUES ( (SELECT user_id FROM GHG_CustomerTable WHERE
			// username = 'gon '),'250',' IN PROGRESS ',' 2021/11/12 23:24:04')

			stmt.executeUpdate(sql);

			dbconn.connect().close();
			System.out.println("The records are succesfully inserted in OrderTable");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet fetchOrderFromDB() throws SQLException {
		dbconn = new DBConnect();
		stmt = dbconn.connect().createStatement();

		String query = "SELECT * FROM GHG_OrderTable";

		ResultSet rs = stmt.executeQuery(query);
		dbconn.connect().close();

		return rs;
	}

	public void deleteOrder(int id) {
		dbconn = new DBConnect();

		try {
			stmt = dbconn.connect().createStatement();
			String sql = "DELETE FROM GHG_OrderTable where order_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbconn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void updateOrder(int order, int user, String totalprice, String status, String date) throws SQLException {
		dbconn = new DBConnect();

		stmt = dbconn.connect().createStatement();
		String query = "Update GHG_OrderTable SET order_id = ' " + order + "' , user_id = ' " + user
				+ " ' , total_price = ' " + totalprice + " ' , order_status = ' " + status + " ' , order_date_time = '"
				+ date + "' WHERE order_id = ' " + order + "'";
		stmt.executeUpdate(query);
		dbconn.connect().close();
	}

}
