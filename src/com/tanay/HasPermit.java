package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HasPermit {

	private String univ_id;
	private String phone_number;
	private String permit_id;
	private String special_event;
	
	
	protected HasPermit(String univ_id, String phone_number, String permit_id, String special_event) {
		this.univ_id = univ_id;
		this.phone_number = phone_number;
		this.permit_id = permit_id;
		this.special_event = special_event;
	}
	
	protected static void create(Statement statement) {
        String query = "CREATE TABLE haspermit"
        		+ "("
        		+ "univ_id CHAR(9),"
        		+ "phone_number CHAR(10),"
        		+ "permit_id INT,"
        		+ "special_event BOOL NOT NULL DEFAULT false,"
        		+ "PRIMARY KEY (univ_id, phone_number, permit_id),"
        		+ "FOREIGN KEY(univ_id, phone_number) REFERENCES driver(univ_id, phone_number) ON UPDATE CASCADE ON DELETE CASCADE,"
        		+ "FOREIGN KEY(permit_id) REFERENCES permit(permit_id) ON UPDATE CASCADE ON DELETE CASCADE"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: HasPermit Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected void insert(Statement statement) {
        String query = "INSERT INTO haspermit VALUES ('" + this.univ_id + "','" + this.phone_number + "','" + this.permit_id + "','" 
        		+ this.special_event + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: HasPermit Query Insert");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static ResultSet view(Statement statement) {
        String query = "SELECT * FROM haspermit;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: HasPermit Query Select");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM haspermit WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: HasPermit Query Select with Where");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE haspermit SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: HasPermit Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM haspermit WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: HasPermit Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
