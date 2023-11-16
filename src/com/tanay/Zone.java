package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Zone {
	private String lot_name;
	private String zone_id;
	
	protected Zone(String lot_name, String zone_id){
		this.lot_name = lot_name;
		this.zone_id = zone_id;
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
	
	protected boolean containsZone(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM zone WHERE lot_name = '" + this.lot_name + "' AND zone_id = '" + this.zone_id + "';";
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
	
	protected static void create(Statement statement) {
        String query = "CREATE TABLE zone"
        		+ "("
        		+ " lot_name VARCHAR (50),"
        		+ " zone_id VARCHAR (2),"
        		+ " PRIMARY KEY (lot_name, zone_id),"
        		+ " FOREIGN KEY(lot_name) REFERENCES parkinglot(lot_name) ON UPDATE"
        		+ " CASCADE ON"
        		+ " DELETE CASCADE"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Zone Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected void insert(Statement statement) {
        String query = "INSERT INTO zone VALUES ('" + this.lot_name + "','" + this.zone_id + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Zone Query Insert");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static ResultSet view(Statement statement) {
        String query = "SELECT * FROM zone;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Zone Query Select");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM zone WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Zone Query Select with Where");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE zone SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Zone Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM zone WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Zone Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
