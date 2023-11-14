package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class PermitDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuPermit(Statement statement, Connection connection) {
		System.out.print("\nPermit Sub-Menu\n"
				+ "a. Create Permit\n"
				+ "b. Insert Permit\n"
				+ "c. View All Permit\n"
				+ "d. View Specific Permit\n"
				+ "e. Update Permit\n"
				+ "f. Delete Permit\n"
				+ "Select one option: ");
		
		PermitDAO permitDAO = new PermitDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					permitDAO.createPermit(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				permitDAO.insertPermit(statement);
				break;
			case "c":
				permitDAO.viewAllPermit(statement);
				break;
			case "d":
				permitDAO.viewPermitByFilters(statement);
				break;
			case "e":
				permitDAO.updatePermit(statement);
				break;
			case "f":
				permitDAO.deletePermitByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}
	
	public void createPermit(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "permit")) {
			System.out.println("Table Already Exists");
		} else {
			Permit.create(statement);
		}
	}
	
	public void insertPermit(Statement statement) {
		
		System.out.print("Enter Permit Id (Integer): ");
		String permit_id = sc.nextLine();
		
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
        String start_date = sc.nextLine();
        
        System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
        String expiration_date = sc.nextLine();
        
        System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
        String expiration_time = sc.nextLine();
        
        System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();
		
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();
		
		Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);
		
		if (permit.containsPermit(statement)) {
        	System.out.println("Permit already present!");
        	return;
        }
		
		if (!permit.containsVehicle(statement)) {
        	System.out.println("Vehicle license number is incorrect, Vehicle is not registered!");
        	return;
        }
		
		permit.insert(statement);
	}
	
	public void viewAllPermit(Statement statement) {
		ResultSet result = Permit.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("permit_id") + "), (" + result.getString("start_date") + "), (" 
				+ result.getString("expiration_date") + "), (" + result.getString("expiration_time") + "), (" + result.getString("vehicle_id") + "), (" 
				+ result.getString("type") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewPermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Integer): ");
		String permit_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
        String start_date = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
        String expiration_date = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
        String expiration_time = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();
		
		SQLHelper sqlHelper = new SQLHelper();
		
		if(permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length() + vehicle_id.length()+ type.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
	        if(start_date.length() > 0) {
	        	whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
	        }
			if(expiration_date.length() > 0) {
	        	whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
	        }
			if(expiration_time.length() > 0) {
	        	whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
	        }
	        if(vehicle_id.length() > 0) {
	        	whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
	        }
	        if(type.length() > 0) {
	        	whereMap.put("type", sqlHelper.singleQuotes(type));
	        }
	        
	        Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);
	        String query = "";
	        
	        query = sqlHelper.merger(whereMap);
	        
	        ResultSet result = permit.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("permit_id") + "), (" + result.getString("start_date") + "), (" 
	        					+ result.getString("expiration_date") + "), (" + result.getString("expiration_time") + "), (" + result.getString("vehicle_id") + "), (" 
	        					+ result.getString("type") + ")]");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void updatePermit(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Integer): ");
		String permit_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter NEW Permit Id (Integer): ");
		String permit_id_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
        String start_date = sc.nextLine();
        
        SQLHelper.skipper();
		System.out.print("Enter NEW Permit Start Date (Date in yyyy-mm-dd format): ");
        String start_date_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
        String expiration_date = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Permit expiration Date (Date in yyyy-mm-dd format): ");
        String expiration_date_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
        String expiration_time = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Permit Expiration time (Time in hh:mm:ss format): ");
        String expiration_time_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();
		
		SQLHelper.skipper();
        System.out.print("Enter your NEW Vehicle license number (String): ");
		String vehicle_id_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your NEW Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type_new = sc.nextLine();
		
		Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);
        String queryWhere = "";
        String querySet = "";
        
        
        SQLHelper sqlHelper = new SQLHelper();
		
		if(permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length() + vehicle_id.length()+ type.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
	        if(start_date.length() > 0) {
	        	whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
	        }
			if(expiration_date.length() > 0) {
	        	whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
	        }
			if(expiration_time.length() > 0) {
	        	whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
	        }
	        if(vehicle_id.length() > 0) {
	        	whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
	        }
	        if(type.length() > 0) {
	        	whereMap.put("type", sqlHelper.singleQuotes(type));
	        }
	        
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(permit_id_new.length() > 0) {
	        	setMap.put("permit_id", sqlHelper.singleQuotes(permit_id_new));
	        	if (Permit.containsPermit(statement, permit_id_new)) {
	            	System.out.println("Permit already present!");
	            	return;
	            }
	        }
	        if(start_date_new.length() > 0) {
	        	setMap.put("start_date", sqlHelper.singleQuotes(start_date_new));
	        }
			if(expiration_date_new.length() > 0) {
	        	setMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date_new));
	        }
			if(expiration_time_new.length() > 0) {
	        	setMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time_new));
	        }
	        if(vehicle_id_new.length() > 0) {
	        	setMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id_new));
	        	if (!Permit.containsVehicle(statement, vehicle_id_new)) {
	            	System.out.println("Vehicle license number is incorrect, Vehicle is not registered!");
	            	return;
	            }
	        }
	        if(type_new.length() > 0) {
	        	setMap.put("type", sqlHelper.singleQuotes(type_new));
	        }
	        
	        querySet = sqlHelper.merger(setMap, ", ");
	        
	        permit.updateFiltered(statement, queryWhere, querySet);
	        
        }
        
	}
	
	public void deletePermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Integer): ");
		String permit_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
        String start_date = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
        String expiration_date = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
        String expiration_time = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();
		
		SQLHelper sqlHelper = new SQLHelper();
		
		if(permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length() + vehicle_id.length()+ type.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
	        if(start_date.length() > 0) {
	        	whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
	        }
			if(expiration_date.length() > 0) {
	        	whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
	        }
			if(expiration_time.length() > 0) {
	        	whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
	        }
	        if(vehicle_id.length() > 0) {
	        	whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
	        }
	        if(type.length() > 0) {
	        	whereMap.put("type", sqlHelper.singleQuotes(type));
	        }
	        
	        String query = "";
	        
	        query = sqlHelper.merger(whereMap);
	        
	        Permit.deleteFiltered(statement, query);
        	}
	}
}
