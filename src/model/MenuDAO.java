package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuDAO {
	DBConnect dbconn;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public MenuDAO() {
		dbconn = new DBConnect();
	}

	// Create table for storing Menu Details
	public void createMenuTable() {
		try {

			// Open a connection
			System.out.println("Connecting to database to create MenuDB Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating MenuDB table in given database...");

			stmt = dbconn.connect().createStatement();

			String sql = "CREATE TABLE GHG_PhoneMenuTable" + "(phone_id INTEGER NOT NULL AUTO_INCREMENT, "
					+ " phone_name VARCHAR(20), " + " phone_price numeric(8,2), " + " PRIMARY KEY(phone_id))";

			stmt.executeUpdate(sql);
			System.out.println("Created MenuDB table in given database...");
			dbconn.connect().close(); // close db connection
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public void insertPhone(int phoneid, String phonename, String phoneprice) throws SQLException {
		dbconn = new DBConnect();
		stmt = dbconn.connect().createStatement();

		String sql = "INSERT INTO GHG_PhoneMenuTable" + "(phone_id, phone_name ,phone_price) " + "VALUES (' " + phoneid
				+ "','" + phonename + " ', ' " + phoneprice + " ')";
		stmt.execute(sql);
		dbconn.connect().close();
	}

	public void updatePhone(int phoneid, String phonename, String phoneprice) throws SQLException {
		dbconn = new DBConnect();

		stmt = dbconn.connect().createStatement();
		String query = "Update GHG_PhoneMenuTable" + " SET phone_name = ' " + phonename + "' , 	phone_price = ' "
				+ phoneprice + " ' WHERE phone_id = ' " + phoneid + "'";
		stmt.executeUpdate(query);
		dbconn.connect().close();
	}

	public void deletePhone(int id) {
		dbconn = new DBConnect();

		try {
			stmt = dbconn.connect().createStatement();
			String sql = "DELETE FROM GHG_PhoneMenuTable" + " where phone_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbconn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ResultSet fetchMenuItems() throws SQLException {
		dbconn = new DBConnect();
		stmt = dbconn.connect().createStatement();

		String query = "SELECT * FROM GHG_PhoneMenuTable";

		ResultSet rs = stmt.executeQuery(query);
		dbconn.connect().close();

		return rs;
	}

}
