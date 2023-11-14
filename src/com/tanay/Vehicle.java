package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vehicle {
	
	private String license_number;
	private String color;
	private String model;
	private String manufacture_year;
	private int is_handicapped;
	private String manufacturer;
	private String univ_id;
	private String phone_number;
	
	protected Vehicle() {}
	
	protected Vehicle(String license_number, String color, String model, String manufacture_year, int is_handicapped,
			String manufacturer, String univ_id, String phone_number) {
		this.license_number = license_number;
		this.color = color;
		this.model = model;
		this.manufacture_year = manufacture_year;
		this.is_handicapped = is_handicapped;
		this.manufacturer = manufacturer;
		this.univ_id = univ_id;
		this.phone_number = phone_number;
	}
	
	// Create Query for Driver
	protected static void create(Statement statement) {
        String query = "CREATE TABLE vehicle("
        		+ "license_number VARCHAR(8),"
        		+ "color VARCHAR(30) NOT NULL,"
        		+ "model VARCHAR(255) NOT NULL,"
        		+ "manufacture_year DATE NOT NULL,"
        		+ "is_handicapped BOOL NOT NULL DEFAULT false,"
        		+ "manufacturer VARCHAR(255) NOT NULL,"
        		+ "univ_id CHAR(9),"
        		+ "phone_number CHAR(10),"
           		+ "PRIMARY KEY (license_number)"
        		+ "FOREIGN KEY(univ_id, phone_number) REFERENCES driver(univ_id, phone_number)"
        		+ " ON UPDATE CASCADE ON DELETE SET NULL,"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Vehicle Query Create");
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
	
	protected boolean containsVehicle(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + this.license_number +  "';";
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
	
	protected static boolean containsVehicle(Statement statement, String license_number) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + license_number +  "';";
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
		String query = "INSERT INTO vehicle VALUES ('" + this.license_number + "','" + this.color + "','" + this.model + "','" + this.manufacture_year + "','"
				+ this.is_handicapped + "','" + this.manufacturer +"','" + this.univ_id + "','" + this.phone_number +"');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Vehicle Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
	}
	
	// Returns all the rows in the Table
	protected static ResultSet view(Statement statement) {
        String query = "SELECT * FROM vehicle;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Vehicle Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
        return result;
    }
	
	
	//	Returns Rows Based on Filters
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM vehicle WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Vehicle Query Select with Where");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.close();
        }
        return result;
    }
	
	// Updates Query
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE vehicle SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Vehicle Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	// Delete Query
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM vehicle WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Vehicle Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
