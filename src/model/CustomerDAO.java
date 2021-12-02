package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {
	DBConnect dbcon;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public CustomerDAO() {
		dbcon = new DBConnect();
	}

	// Create table for storing User Details
	public void createUserDetailDBTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create UserDetailDB Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating UserDetailDB table in given database...");

			stmt = dbcon.connect().createStatement();

			String sql = "CREATE TABLE GHG_CustomerTable" + "(user_id INTEGER NOT NULL AUTO_INCREMENT, "
					+ " username VARCHAR(20), " + " email VARCHAR(30), " + " address VARCHAR(50), "
					+ " phone VARCHAR(10), " + " password VARCHAR(15), " + " PRIMARY KEY(user_id))";
			stmt.executeUpdate(sql);
			System.out.println("Created UserDetailDB table in given database...");
			dbcon.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public boolean insertUserDetailRecords(String username, String email, String address, String phone,
			String password) {
		Boolean flag = false;
		try {
			stmt = dbcon.connect().createStatement();
			String sql = null;
			sql = "INSERT INTO GHG_CustomerTable" + "(username,email,address,phone,password) " + "VALUES ('" + username
					+ "', '" + email + "', '" + address + "', '" + phone + "', '" + password + "' )";
			stmt.executeUpdate(sql);
			System.out.println("Records Inserted into GHG_CustomerTable.");
			flag = true;
			dbcon.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println("flag value after insert: " + flag);
		return flag;
	}

	public boolean searchUserDetailDB(String username, String password) {
		boolean flag = false;
		try {
			stmt = dbcon.connect().createStatement();
			String sql = null;
			sql = "SELECT username, password FROM GHG_CustomerTable" + " WHERE username = '" + username
					+ "' AND password = '" + password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("sql: " + sql);
			System.out.println("rs: " + rs);
			if (rs.next())
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	public int getIdFromUserDB(String username, String password) {
		boolean flag = false;
		int userId = 0;
		try {
			stmt = dbcon.connect().createStatement();
			String sql = null;
			sql = "SELECT user_id FROM GHG_CustomerTable" + " WHERE username = '" + username + "' AND password = '"
					+ password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				userId = rs.getInt(1);
				System.out.println("userId: " + userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userId;
	}

	public ResultSet fetchCustomer() throws SQLException {
		dbcon = new DBConnect();
		stmt = dbcon.connect().createStatement();

		String query = "SELECT * FROM GHG_CustomerTable";

		ResultSet rs = stmt.executeQuery(query);
		dbcon.connect().close();

		return rs;
	}

	public void updateCustomer(int custid, String custname, String custemail, String custaddr, String custphone,
			String custpassword) throws SQLException {
		dbcon = new DBConnect();
		System.out.println("Entered update method");
		System.out.println("custid: " + custid + "custname: " + custname + "custemail: " + custemail + "custaddr: "
				+ custaddr + "custphone: " + custphone);

		stmt = dbcon.connect().createStatement();
		String query = "Update GHG_CustomerTable" + " SET username = '" + custname + "' , email = '" + custemail
				+ "' , address = '" + custaddr + "' , phone = '" + custphone + "' , password ='" + custpassword
				+ "' WHERE user_id = '" + custid + "'";
		System.out.println("update query: " + query);
		stmt.executeUpdate(query);
		dbcon.connect().close();
	}

	public void deleteCustomer(int id) {
		dbcon = new DBConnect();

		try {
			stmt = dbcon.connect().createStatement();
			String sql = "DELETE FROM GHG_CustomerTable" + " where user_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbcon.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ResultSet retrieveValues(int id) throws SQLException {
		System.out.println("Retrieve Values in Customer Detail DB!!!" + id);
		dbcon = new DBConnect();

		stmt = dbcon.connect().createStatement();
		String sql = "Select * from GHG_CustomerTable" + " where user_id = '" + id + "'";
		result = stmt.executeQuery(sql);
		dbcon.connect().close();
		return result;
	}

}
