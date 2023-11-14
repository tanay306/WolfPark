package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class HasPermitDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuHasPermit(Statement statement, Connection connection) {
		System.out.print("\nHasPermit Sub-Menu\n"
				+ "a. Create HasPermit\n"
				+ "b. Insert HasPermit\n"
				+ "c. View All HasPermit\n"
				+ "d. View Specific HasPermit\n"
				+ "e. Update HasPermit\n"
				+ "f. Delete HasPermit\n"
				+ "Select one option: ");
		
		HasPermitDAO haspermitDAO = new HasPermitDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					haspermitDAO.createHasPermit(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				haspermitDAO.insertHasPermit(statement);
				break;
			case "c":
				haspermitDAO.viewAllHasPermit(statement);
				break;
			case "d":
				haspermitDAO.viewHasPermitByFilters(statement);
				break;
			case "e":
				haspermitDAO.updateHasPermit(statement);
				break;
			case "f":
				haspermitDAO.deleteHasPermitByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}
	
	public void createHasPermit(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "haspermit")) {
			System.out.println("Table Already Exists");
		} else {
			HasPermit.create(statement);
		}
	}
	
	public void insertHasPermit(Statement statement) {
		System.out.print("Enter your University Id (String): ");
		String univ_id = sc.nextLine();
		
		System.out.print("Enter your Phone number(String): ");
        String phone_number = sc.nextLine();
        
        System.out.print("Enter your Permit Id: ");
        String permit_id = sc.nextLine();
		
		System.out.print("Enter 1 if it's a Special Event (Integer): ");
		String special_event = sc.nextLine();
		
		HasPermit hasPermit = new HasPermit(univ_id, phone_number, permit_id, special_event);
		
		if (!hasPermit.containsPermit(statement)) {
        	System.out.println("Permit is not registered, Incorrect Permit Id!");
        	return;
        }
		
		if (!hasPermit.containsDriver(statement)) {
        	System.out.println("Driver is not registered, Incorrect University Id or Phone Number!");
        	return;
        }
		
		if (hasPermit.containsHasPermitAll(statement)) {
        	System.out.println("Duplicate Primary key, Cannot insert this ROW.");
        	return;
        }
		
		hasPermit.insert(statement);
	}
	
	public void viewAllHasPermit(Statement statement) {
		ResultSet result = HasPermit.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), (" 
				+ result.getString("permit_id") + "), (" + result.getString("special_event") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewHasPermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your University Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Phone number(String): ");
        String phone_number = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Permit Id: ");
        String permit_id = sc.nextLine();
		
        SQLHelper.skipper();
		System.out.print("Enter 1 if it's a Special Event (Integer): ");
		String special_event = sc.nextLine();
		
		SQLHelper sqlHelper = new SQLHelper();
		
		if(univ_id.length() + phone_number.length() + permit_id.length() + special_event.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllHasPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
			if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
			if(special_event.length() > 0) {
	        	whereMap.put("special_event", sqlHelper.singleQuotes(special_event));
	        }
	        
	        HasPermit hasPermit = new HasPermit(univ_id, phone_number, permit_id, special_event);
	        String query = "";
	        
	        query = sqlHelper.merger(whereMap);
	        
	        ResultSet result = hasPermit.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), (" 
	        					+ result.getString("permit_id") + "), (" + result.getString("special_event") + ")]");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void updateHasPermit(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your University Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your NEW University Id (String): ");
		String univ_id_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Phone number(String): ");
        String phone_number = sc.nextLine();
        
        SQLHelper.skipper();
		System.out.print("Enter your NEW Phone number(String): ");
        String phone_number_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Permit Id: ");
        String permit_id= sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW your Permit Id: ");
        String permit_id_new = sc.nextLine();
		
        SQLHelper.skipper();
		System.out.print("Enter 1 if it's a Special Event (Integer): ");
		String special_event = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("(UPDATE) Enter 1 if it's a Special Event (Integer): ");
		String special_event_new = sc.nextLine();
		
		HasPermit hasPermit = new HasPermit(univ_id, phone_number, permit_id, special_event);
        String queryWhere = "";
        String querySet = "";
		
		SQLHelper sqlHelper = new SQLHelper();
		
		if(univ_id.length() + phone_number.length() + permit_id.length() + special_event.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllHasPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
			if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
			if(special_event.length() > 0) {
	        	whereMap.put("special_event", sqlHelper.singleQuotes(special_event));
	        }
	        
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(univ_id_new.length() > 0) {
	        	setMap.put("univ_id", sqlHelper.singleQuotes(univ_id_new));
	        }
	        if(phone_number_new.length() > 0) {
	        	setMap.put("phone_number", sqlHelper.singleQuotes(phone_number_new));
	        }
			if(permit_id_new.length() > 0) {
	        	setMap.put("permit_id", sqlHelper.singleQuotes(permit_id_new));
	        	if (!HasPermit.containsPermit(statement, permit_id_new)) {
	            	System.out.println("Permit is not registered, Incorrect Permit Id!");
	            	return;
	            }
	        }
			if(special_event_new.length() > 0) {
	        	setMap.put("special_event", sqlHelper.singleQuotes(special_event_new));
	        }
	        
			if(univ_id_new.length() > 0 && phone_number_new.length() > 0) {
				if(!HasPermit.containsDriver(statement, univ_id_new, phone_number_new)) {
	        		System.out.println("Driver is not registered, Incorrect University Id or Phone Number!");
	            	return;
	        	}
			}
			
			querySet = sqlHelper.merger(setMap, ", ");
			
	        hasPermit.updateFiltered(statement, queryWhere, querySet);
        }
	}
	
	public void deleteHasPermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your University Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter your Phone number(String): ");
        String phone_number = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Permit Id: ");
        String permit_id = sc.nextLine();
		
        SQLHelper.skipper();
		System.out.print("Enter 1 if it's a Special Event (Integer): ");
		String special_event = sc.nextLine();
		
		SQLHelper sqlHelper = new SQLHelper();
		
		if(univ_id.length() + phone_number.length() + permit_id.length() + special_event.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllHasPermit(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
			if(permit_id.length() > 0) {
	        	whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
	        }
			if(special_event.length() > 0) {
	        	whereMap.put("special_event", sqlHelper.singleQuotes(special_event));
	        }
	        
	        String query = "";
	        
	        query = sqlHelper.merger(whereMap);
	        
	        HasPermit.deleteFiltered(statement, query);
	        
        }
	}
}
