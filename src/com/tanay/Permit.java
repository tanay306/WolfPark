package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Permit {
	
	private String permit_id;
	private String start_date;
	private String expiration_date;
	private String expiration_time;
	private String vehicle_id;
	private String type;
	
	
	protected Permit(String permit_id, String start_date, String expiration_date, String expiration_time, String vehicle_id, String type) {
		this.permit_id = permit_id;
		this.start_date = start_date;
		this.expiration_date = expiration_date;
		this.expiration_time = expiration_time;
		this.vehicle_id = vehicle_id;
		this.type = type;
	}
	
	protected static void create(Statement statement) {
        String query = "CREATE TABLE permit"
        		+ "("
        		+ "permit_id VARCHAR(10),"
        		+ "start_date DATE NOT NULL,"
        		+ "expiration_date DATE NOT NULL,"
        		+ "expiration_time TIME NOT NULL,"
        		+ "vehicle_id VARCHAR(8),"
        		+ "type VARCHAR(14) NOT NULL,"
        		+ "FOREIGN KEY(vehicle_id) REFERENCES vehicle(license_number),"
        		+ "PRIMARY KEY (permit_id)"
        		+ ");";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Permit Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	
	protected String getUnivId(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + this.vehicle_id +  "';";
    	try {
    		result = statement.executeQuery(query);
    		if (result.next()) {
    			return result.getString("univ_id");
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return "0";
    }
	
	protected String getPhoneNumber(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + this.vehicle_id +  "';";
    	try {
    		result = statement.executeQuery(query);
    		if (result.next()) {
    			return result.getString("phone_number");
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return "0";
    }
	
	
	protected boolean containsPermit(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM permit WHERE permit_id = '" + this.permit_id +  "';";
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
	
	protected static boolean containsPermit(Statement statement, String permit_id) {
    	ResultSet result = null;
    	String query = "SELECT * FROM permit WHERE permit_id = '" + permit_id +  "';";
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
    	String query = "SELECT * FROM permit WHERE vehicle_id = '" + this.vehicle_id +  "';";
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
	
	protected static boolean containsVehicle(Statement statement, String vehicle_id) {
    	ResultSet result = null;
    	String query = "SELECT * FROM permit WHERE vehicle_id = '" + vehicle_id +  "';";
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
	
	protected boolean containsVehicleForeign(Statement statement) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + this.vehicle_id +  "';";
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
	
	protected static boolean containsVehicleForeign(Statement statement, String vehicle_id) {
    	ResultSet result = null;
    	String query = "SELECT * FROM vehicle WHERE license_number = '" + vehicle_id +  "';";
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
	
	protected void insert(Statement statement) {
        String query = "INSERT INTO permit VALUES ('" + this.permit_id + "','" + this.start_date + "','" + this.expiration_date + "','" 
        		+ this.expiration_time + "','" + this.vehicle_id + "','" + this.type + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Permit Query Insert");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static ResultSet view(Statement statement) {
        String query = "SELECT * FROM permit;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Permit Query Select");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM permit WHERE " + queryParams + ";";
        ResultSet result = null;
        System.out.println(query);
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Permit Query Select with Where");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
        return result;
    }
	
	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE permit SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Permit Query Update");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
	
	protected static void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM permit WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Permit Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
