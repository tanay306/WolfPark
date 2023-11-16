package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Space {
	private String lot_name;
	private String zone_id;
	private String space_number;
	private String type;
	private String availability_slot;

    Space(){}
	
    Space (String lot_name, String zone_id, String space_number, String type, String availability_slot) {
		this.lot_name = lot_name;
		this.zone_id = zone_id;
		this.space_number = space_number;
		this.type = type;
		this.availability_slot = availability_slot;
	}
    
    protected boolean containsZone(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM zone WHERE lot_name = '" + this.lot_name + "' AND zone_id = '" + this.zone_id +"';";
    	System.out.println(query);
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
        String query = "CREATE TABLE space\r\n"
        		+ "("
        		+ " lot_name VARCHAR (50),"
        		+ " zone_id VARCHAR (2) NOT NULL,"
        		+ " space_number INT,"
        		+ " type VARCHAR (20) DEFAULT 'regular' NOT NULL,"
        		+ " availability_slot BOOL DEFAULT false NOT NULL,"
        		+ " PRIMARY KEY (lot_name, zone_id, space_number),"
        		+ " FOREIGN KEY(lot_name, zone_id) REFERENCES zone(lot_name, zone_id)"
        		+ " ON UPDATE CASCADE ON DELETE CASCADE"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Space Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected void insert(Statement statement) {
        String query = "INSERT INTO space VALUES ('" + this.lot_name + "','" + this.zone_id + "','" + this.space_number + "','" + this.type + "','" + this.availability_slot + "');";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Space Query Insert");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static ResultSet view(Statement statement) {
        String query = "SELECT * FROM space;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Space Query Select");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM space WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Space Query Select with Where");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE space SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Space Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage() + "\n\nChanges trashed, try again!");
            Main.close();
        }
    }
	
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM space WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Space Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
