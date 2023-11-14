package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {
	
	private String univ_id;
	private String phone_number;
	private String name;
	private String status;
	private int no_of_permits;
	
	protected Driver() {}
	
	protected Driver(String univ_id, String phone_number, String name, String status, int no_of_permits) {
		this.univ_id = univ_id;
		this.phone_number = phone_number;
		this.name = name;
		this.status = status;
		this.no_of_permits = no_of_permits;
	}
	
	// Create Query for Driver
	protected void create(Statement statement) {
        String query = "CREATE TABLE driver("
        		+ "univ_id CHAR(9),"
        		+ "phone_number CHAR(10),"
        		+ "name VARCHAR(255) NOT NULL,"
        		+ "status VARCHAR(255) NOT NULL,"
        		+ "no_of_permits INT NOT NULL DEFAULT 0,"
        		+ "PRIMARY KEY (univ_id, phone_number)"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Driver Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected boolean containsDriver(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM driver WHERE univ_id = '" + this.univ_id+ "' AND phone_number = '" + this.phone_number + "';";
    	try {
    		result = statement.executeQuery(query);
    		if (result.next()) {
    			return true;
    		} else {
    			return false;
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return true;
    }
	
	protected static boolean containsDriver(Statement statement, String univ_id, String phone_number) {
    	ResultSet result = null;
    	String query = "SELECT * FROM driver WHERE univ_id = '" + univ_id+ "' AND phone_number = '" + phone_number + "';";
    	try {
    		result = statement.executeQuery(query);
    		if (result.next()) {
    			return true;
    		} else {
    			return false;
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return true;
    }
	
	// Insert one row in the Drivers Table
	protected void insert(Statement statement) {
		String query = "INSERT INTO driver VALUES ('" + this.univ_id + "','" + this.phone_number + "','" 
				+ this.name + "','" + this.status + "','" + this.no_of_permits +"');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Driver Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
	}
	
	// Returns all the rows in the Table
	protected ResultSet view(Statement statement) {
        String query = "SELECT * FROM driver;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Driver Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
        return result;
    }
	
	
	//	Returns Rows Based on Filters
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM driver WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Driver Query Select with Where");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
        return result;
    }
	
	// Updates Query
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE driver SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Driver Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	// Delete Query
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM driver WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Driver Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
}
