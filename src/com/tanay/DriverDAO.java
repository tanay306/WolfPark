package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuDriver(Statement statement, Connection connection) {
		System.out.print("\nDriver Sub-Menu\n"
				+ "a. Create Driver\n"
				+ "b. Insert Driver\n"
				+ "c. View All Driver\n"
				+ "d. View Specific Driver\n"
				+ "e. Update Driver\n"
				+ "f. Delete Driver\n"
				+ "Select one option: ");
		
		DriverDAO driverDAO = new DriverDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					driverDAO.createDriver(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				driverDAO.insertDriver(statement);
				break;
			case "c":
				driverDAO.viewAllDriver(statement);
				break;
			case "d":
				driverDAO.viewDriverByFilters(statement);
				break;
			case "e":
				driverDAO.updateDriver(statement);
				break;
			case "f":
				driverDAO.deleteDriverByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
				this.menuDriver(statement, connection);
		}
	}
	
	public void createDriver(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "driver")) {
			System.out.println("Table Already Exists");
		} else {
			Driver driver = new Driver();
			driver.create(statement);
		}
	}
	
	// Insert a Driver in the Table
	public void insertDriver(Statement statement) {
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();
		System.out.print("Enter Phone Number (String): ");
        String phone_number = sc.nextLine();
        System.out.print("Enter your Name (String): ");
        String name = sc.nextLine();
        System.out.print("Enter Status (String): ");
        String status = sc.nextLine().toUpperCase();
        
        Driver driver= new Driver(univ_id, phone_number, name, status, 0);
        if (driver.containsDriver(statement)) {
        	System.out.println("Driver is already registered!");
        	return;
        }
        
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
	
	// View Driver by filter
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
	        if(no_of_permits > 0) {
	        	if(query.length() > 0) {
	        		query += "AND no_of_permits=" + no_of_permits;
	        	} else {
	        		query += "no_of_permits=" + no_of_permits;
	        	}
	        }
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
	
	// update Driver
	public void updateDriver(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter NEW University Id (String): ");
		String univ_id_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
        String phone_number = sc.nextLine();
        
        SQLHelper.skipper();
		System.out.print("Enter NEW Phone Number (String): ");
        String phone_number_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Name (String): ");
        String name = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your NEW Name (String): ");
        String name_new = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Status (String): ");
        String status = sc.nextLine().toUpperCase();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Status (String): ");
        String status_new = sc.nextLine().toUpperCase();
        
        SQLHelper.skipper();
        System.out.print("Enter Number of Permits (Integer): ");
        int no_of_permits;
        String int_input = sc.nextLine();
        if(int_input.isEmpty()) {
        	no_of_permits = 0;
        } else {
        	no_of_permits = Integer.parseInt(int_input);      	
        }
        
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Number of Permits (Integer): ");
        int no_of_permits_new;
        String int_input_new = sc.nextLine();
        if(int_input_new.isEmpty()) {
        	no_of_permits_new = 0;
        } else {
        	no_of_permits_new = Integer.parseInt(int_input);      	
        }
        
        
        Driver driver = new Driver(univ_id, phone_number, name, status, no_of_permits);
        String queryWhere = "";
        String querySet = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(univ_id.length() + phone_number.length() + name.length() + status.length() + no_of_permits == 0) {
        	System.out.println("No Filters provided, cannot update a row");
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
	        
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        // Handling integer case for no_of_permits.
	        if(no_of_permits > 0) {
	        	if(queryWhere.length() > 0) {
	        		queryWhere += "AND no_of_permits=" + no_of_permits;
	        	} else {
	        		queryWhere += "no_of_permits=" + no_of_permits;
	        	}
	        }
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(univ_id_new.length() > 0) {
	        	setMap.put("univ_id", sqlHelper.singleQuotes(univ_id_new));
	        }
	        if(phone_number_new.length() > 0) {
	        	setMap.put("phone_number", sqlHelper.singleQuotes(phone_number_new));
	        }
	        if(name_new.length() > 0) {
	        	setMap.put("name", sqlHelper.singleQuotes(name_new));
	        }
	        if(status_new.length() > 0) {
	        	setMap.put("status", sqlHelper.singleQuotes(status_new));
	        }
	        
	        querySet = sqlHelper.merger(setMap, ", ");
	        
	        // Handling integer case for no_of_permits.
	        if(no_of_permits_new > 0) {
	        	if(querySet.length() > 0) {
	        		querySet += ", no_of_permits=" + no_of_permits_new;
	        	} else {
	        		querySet += "no_of_permits=" + no_of_permits_new;
	        	}
	        }
	        
	        if(univ_id_new.length() > 0 && phone_number_new.length() > 0) {
				if(Driver.containsDriver(statement, univ_id_new, phone_number_new)) {
	        		System.out.println("Driver is already registered, Incorrect University Id or Phone Number!");
	            	return;
	        	}
			}
	        
	        driver.updateFiltered(statement, queryWhere, querySet);
	       
        }
	}
	
	public void deleteDriverByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
        String phone_number = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter your Name (String): ");
        String name = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter Status (String): ");
        String status = sc.nextLine().toUpperCase();
        
        SQLHelper.skipper();
        System.out.print("Enter Number of Permits (Integer): ");
        int no_of_permits;
        String int_input = sc.nextLine();
        if(int_input.isEmpty()) {
        	no_of_permits = 0;
        } else {
        	no_of_permits = Integer.parseInt(int_input);      	
        }
        
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
	        if(no_of_permits > 0) {
	        	if(query.length() > 0) {
	        		query += "AND no_of_permits=" + no_of_permits;
	        	} else {
	        		query += "no_of_permits=" + no_of_permits;
	        	}
	        }
	        
	        Driver.deleteFiltered(statement, query);
        }
	}
}
