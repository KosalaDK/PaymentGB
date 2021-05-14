package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class Payment {
	// A common method to connect to the DB
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_paf2021", "root", "kosala");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
public String insertPayment(String orderid, String amount) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into payment(`payID`,`orderID`,`totalAmount`)"+ "values (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setInt(2, Integer.parseInt(orderid));
				preparedStmt.setFloat(3, Float.parseFloat(amount));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String send = readPayments(); 
				 output = "{\"status\":\"success\", \"data\": \"" + send + "\"}";
				
			} catch (Exception e) {
				output = "Error while inserting the payment.";
				System.err.println(e.getMessage());
			}
			return output;
		}

public String readPayments() {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for reading.";
		}
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>PaymentID</th><th>OrderID</th>" 
				+ "<th>Payment Date and Time</th>" + "<th>Total Amount</th>" +"<th>Status</th></tr>";

		String query = "select * from payment";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		// iterate through the rows in the result set
		while (rs.next()) {
			String payID = Integer.toString(rs.getInt("payID"));
			String orderID = Integer.toString(rs.getInt("orderID"));
			Timestamp datets = rs.getTimestamp("payDateTime");
			String payDate = datets.toString();
			String amount = Float.toString(rs.getFloat("totalAmount"));
			String status = rs.getString("payStatus");
			


			// Add into the html table
			output += "<tr><td>" + payID + "</td>";
			output += "<td>" + orderID + "</td>";
			output += "<td>" + payDate + "</td>";
			output += "<td>" + amount + "</td>";
			output += "<td>" + status + "</td>";

			output += "<td><input name='btnUpdate' type='button' value='Update' "
					+ "class='btnUpdate btn btn-secondary' data-userid='" + payID + "'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' "
					+ "class='btnRemove btn btn-danger' data-userid='" + payID + "'></td></tr>"; 


		}
		con.close();

		// Complete the html table
		output += "</table>";
		
	} catch (Exception e) {
		output = "Error while reading the payment.";
		System.err.println(e.getMessage());
	}
	return output;
}

public String getPayByOID(String OID) {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for reading.";
		}
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>PaymentID</th><th>OrderID</th>" 
				+ "<th>Payment Date and Time</th>" + "<th>Total Amount</th>" +"<th>Status</th></tr>";
		
		
		String query = "select * from payment where orderID =?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, Integer.parseInt(OID));
		ResultSet rs = preparedStmt.executeQuery();
		


		// iterate through the rows in the result set
		while (rs.next()) {
			String payID = Integer.toString(rs.getInt("payID"));
			String orderID = Integer.toString(rs.getInt("orderID"));
			Timestamp datets = rs.getTimestamp("payDateTime");
			String payDate = datets.toString();
			String amount = Float.toString(rs.getFloat("totalAmount"));
			String status = rs.getString("payStatus");


			// Add into the html table
			output += "<tr><td>" + payID + "</td>";
			output += "<td>" + orderID + "</td>";
			output += "<td>" + payDate + "</td>";
			output += "<td>" + amount + "</td>";
			output += "<td>" + status + "</td>";
			


		}
		con.close();

		// Complete the html table
		output += "</table>";
	} catch (Exception e) {
		output = "Error while reading the payment.";
		System.err.println(e.getMessage());
	}
	return output;
}

public String setPayStatus(String status, String pID) {
	
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE payment SET payStatus=? WHERE payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		
		preparedStmt.setString(1, status);
		preparedStmt.setInt(2, Integer.parseInt(pID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
	} catch (Exception e) {
		output = "Error while updating the payment status.";
		System.err.println(e.getMessage());
		}
	return output;
	
}

public String updatePayment(String oID, String total, String pID) {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE payment SET orderID=?,totalAmount=? WHERE payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		
		preparedStmt.setInt(1, Integer.parseInt(oID));
		preparedStmt.setFloat(2,Float.parseFloat(total));
		preparedStmt.setInt(3, Integer.parseInt(pID));
		// execute the statement
		preparedStmt.execute();
		
		System.out.println(oID+total+pID);
		con.close();
		String send = readPayments(); 
		 output = "{\"status\":\"success\", \"data\": \"" + send + "\"}";
	} catch (Exception e) {
		output = "Error while updating the payment.";
		System.err.println(e.getMessage());
		}
	return output;
	}

public String deletePayment(String payID) {
	String output = "";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for deleting.";
		}
		// create a prepared statement
		String query = "delete from payment where payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(payID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		String send = readPayments(); 
		 output = "{\"status\":\"success\", \"data\": \"" + send + "\"}";
	} catch (Exception e) {
		output = "Error while deleting the payment.";
		System.err.println(e.getMessage());
	}
	return output;
}
}
