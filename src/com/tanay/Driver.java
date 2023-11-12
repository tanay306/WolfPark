package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {
	
	String univ_id;
	String phone_number;
	String name;
	String status;
	int no_of_permits;
	
	public Driver() {}
	
	public Driver(String univ_id, String phone_number, String name, String status, int no_of_permits) {
		this.univ_id = univ_id;
		this.phone_number = phone_number;
		this.name = name;
		this.status = status;
		this.no_of_permits = no_of_permits;
	}
	
	// Get Method for univ_id
	public String getUniv_id() {
		return this.univ_id;
	}
	
	// Set Method for univ_id
	public void setUnivId(String univ_id) {
		this.univ_id = univ_id;
	}
	
	// Get Method for phone_number
	public String getPhone_number() {
		return this.phone_number;
	}
	
	// Set Method for phone_number
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	// Get Method for name
	public String getName() {
		return this.name;
	}
	
	// Set Method for name
	public void setName(String name) {
		this.name = name;
	}
	
	// Get Method for status
	public String getStatus() {
		return this.status;
	}
	
	// Set Method for status
	public void setStatus(String status) {
		this.status = status;
	}
	
	// Get Method for no_of_permits
	public int getNo_of_Permits() {
		return this.no_of_permits;
	}
	
	// Set Method for no_of_permits
	public void setNo_of_Permits(int no_of_permits) {
		this.no_of_permits = no_of_permits;
	}
	
	// Insert one row in the Drivers Table
	public void insert(Statement statement) {
		String query = "INSERT INTO driver VALUES ('" + this.univ_id + "','" + this.phone_number + "','" 
				+ this.name + "','" + this.status + "','" + this.no_of_permits +"');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Driver Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ResultSet view(Statement statement) {
        String query = "SELECT * FROM driver;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Driver Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
	
}
