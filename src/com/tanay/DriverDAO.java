package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverDAO {
	Scanner sc = new Scanner(System.in);
	
	// Insert a Driver in the Table
	public void createDriver(Statement statement) {
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();
		System.out.print("Enter Phone Number (String): ");
        String phone_number = sc.nextLine();
        System.out.print("Enter your Name (String): ");
        String name = sc.nextLine();
        System.out.print("Enter Status (String): ");
        String status = sc.nextLine().toUpperCase();
        System.out.print("Enter Number of Permits (Integer): ");
        int no_of_permits = Integer.parseInt(sc.nextLine());
        
        Driver driver= new Driver(univ_id, phone_number, name, status, no_of_permits);
        driver.insert(statement);
	}
	
	
	// View All Drivers in the Table
	public void viewAllDriver(Statement statement) {
		Driver driver = new Driver();
		ResultSet result = driver.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), "
						+ "(" + result.getString("name") + "), (" + result.getString("status") + "), "
								+ "(" + result.getString("no_of_permits") + ")]\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewDriverByFilters(Statement statement) {
		System.out.print("Click enter to skip this filter\nEnter University Id(String): ");
		String univ_id = sc.nextLine();
		System.out.print("Click enter to skip this filter\nEnter Phone Number (String): ");
        String phone_number = sc.nextLine();
        System.out.print("Click enter to skip this filter\nEnter your Name (String): ");
        String name = sc.nextLine();
        System.out.print("Click enter to skip this filter\nEnter Status (String): ");
        String status = sc.nextLine().toUpperCase();
        System.out.print("Click enter to skip this filter\nEnter Number of Permits (Integer): ");
        int no_of_permits;
        String int_input = sc.nextLine();
        if(int_input.isEmpty()) {
        	no_of_permits = 0;
        } else {
        	no_of_permits = Integer.parseInt(int_input);      	
        }
        
        Driver driver= new Driver(univ_id, phone_number, name, status, no_of_permits);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(univ_id.length() + phone_number.length() + name.length() + status.length() + no_of_permits == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllDriver(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
	        if(name.length() > 0) {
	        	whereMap.put("name", sqlHelper.singleQuotes(name));
	        }
	        if(status.length() > 0) {
	        	whereMap.put("status", sqlHelper.singleQuotes(status));
	        }
	        
	        query = sqlHelper.merger(whereMap);
	        
	        // Handling integer case for no_of_permits.
	        if(no_of_permits > 0 && query.length() > 0) {
	        	query += "AND no_of_permits=" + no_of_permits;
	        } else { query += "no_of_permits=" + no_of_permits; }
	        ResultSet result = driver.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), "
	    						+ "(" + result.getString("name") + "), (" + result.getString("status") + "), "
	    								+ "(" + result.getString("no_of_permits") + ")]\n");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}
