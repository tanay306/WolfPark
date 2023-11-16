package com.tanay;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ParkingLot {

    private String lot_name;
    private String address;
    
    protected ParkingLot() {}
    
    protected ParkingLot(String lot_name, String address) {
    	this.lot_name = lot_name;
    	this.address = address;
    }

    protected String getLot_name() {
        return lot_name;
    }

    protected void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }
    
    protected boolean containsParkingLot(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM parkinglot WHERE lot_name = '" + this.lot_name + "';";
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
    
    protected void create(Statement statement) {
        String query = "CREATE TABLE parkinglot("
        		+ "lot_name VARCHAR (50),"
        		+ "address VARCHAR (255) NOT NULL,"
        		+ "PRIMARY KEY (lot_name)"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
    

    protected void insert(Statement statement) {
        String query = "INSERT INTO parkinglot VALUES ('" + this.lot_name + "','" + this.address + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Parking Lot Query Insert");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
    
    protected ResultSet view(Statement statement) {
        String query = "SELECT * FROM parkinglot;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Select");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }

    protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM parkinglot WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Select with Where");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
    
    protected void viewUpdateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE parkinglot SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Parking Lot Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
    
    protected void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM parkinglot WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
