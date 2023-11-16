package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppealsDAO {

    Scanner sc = new Scanner(System.in);

    public void menuCategory(Statement statement, Connection connection) {
		System.out.print("\nAppeals Sub-Menu\n"
				+ "a. Create Appeals\n"
				+ "b. Insert Appeals\n"
				+ "c. View All Appeals\n"
				+ "d. View Specific Appeals\n"
				+ "e. Update Appeals\n"
				+ "f. Delete Appeals\n"
				+ "g. Pay\n"
				+ "Select one option: ");
		
		AppealsDAO appealsDAO = new AppealsDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					appealsDAO.createAppeals(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				appealsDAO.insertAppeals(statement);
				break;
			case "c":
				appealsDAO.viewAllAppeals(statement);
				break;
			case "d":
				appealsDAO.viewAppealsByFilters(statement);
				break;
			case "e":
				appealsDAO.updateAppeals(statement);
				break;
			case "f":
				appealsDAO.deleteAppealsByFilters(statement);
				break;
			case "g":
				appealsDAO.pay(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}
	
	public void createAppeals(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "appeals")) {
			System.out.println("Table Already Exists");
		} else {
			Appeals appeals = new Appeals();
			appeals.create(statement);
		}
	}

    public void insertAppeals(Statement statement) {
		System.out.print("Enter Univ_Id (String): ");
		String univ_id = sc.nextLine();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();
        System.out.print("Enter Citation Number (String): ");
		String citation_number = sc.nextLine();
        
        Appeals appeals = new Appeals(univ_id, phone_number, citation_number);
        if (appeals.containsAppeals(statement)) {
        	System.out.println("Appeals already present!");
        	return;
        }
        appeals.insert(statement);
	}

    public void viewAllAppeals(Statement statement) {
		Appeals appeals = new Appeals();
		ResultSet result = appeals.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), (" + result.getString("citation_number") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void viewAppealsByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Univ_Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

        SQLHelper.skipper();
		System.out.print("Enter Citation Number (String): ");
		String citation_number = sc.nextLine();
        
        Appeals appeals = new Appeals(univ_id, phone_number, citation_number);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(univ_id.length() + phone_number.length() + citation_number.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllAppeals(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
            if(citation_number.length() > 0) {
	        	whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
	        }
	        query = sqlHelper.merger(whereMap);
	        ResultSet result = appeals.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), (" + result.getString("citation_number") + ")]");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}

    public void updateAppeals(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Univ_Id (String): ");
		String univ_id = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter New Univ_Id (String): ");
		String univ_id_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter New Phone Number (String): ");
		String phone_number_new = sc.nextLine();

        SQLHelper.skipper();
		System.out.print("Enter Citation Number (String): ");
		String citation_number = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter New Citation Number (String): ");
		String citation_number_new = sc.nextLine();

        Appeals appeals = new Appeals(univ_id, phone_number, citation_number);
        if ( (univ_id_new.length() > 0 && phone_number_new.length() == 0) || (univ_id_new.length() == 0 && phone_number_new.length() > 0)) {
            System.out.println("Need to change both univ id and phone number!");
        	return;
        }
		if (univ_id_new.length() > 0 && phone_number_new.length() > 0 && !appeals.containsDriver(statement, univ_id_new, phone_number_new)) {
            System.out.println("Driver not present!");
        	return;
        }
        if (citation_number_new.length() > 0 && !appeals.containsCitation(statement, citation_number_new)) {
            System.out.println("Citation not present!");
        	return;
        }
        String queryWhere = "";
        String querySet = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(univ_id.length() + phone_number.length() + citation_number.length() == 0) {
        	System.out.println("No Filters provided, cannot update a row");
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
            if(citation_number.length() > 0) {
	        	whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
	        }
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(univ_id_new.length() > 0) {
	        	setMap.put("univ_id", sqlHelper.singleQuotes(univ_id_new));
	        }
	        if(phone_number_new.length() > 0) {
	        	setMap.put("phone_number", sqlHelper.singleQuotes(phone_number_new));
	        }
            if(citation_number_new.length() > 0) {
	        	setMap.put("citation_number", sqlHelper.singleQuotes(citation_number_new));
	        }
	        querySet = sqlHelper.mergerUpdate(setMap);
	        
	        appeals.viewUpdateFiltered(statement, queryWhere, querySet);
        }
	}

    public void deleteAppealsByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Univ_Id (String): ");
		String univ_id = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

        SQLHelper.skipper();
		System.out.print("Enter Citation Number (String): ");
		String citation_number = sc.nextLine();
        
        Appeals appeals = new Appeals(univ_id, phone_number, citation_number);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(univ_id.length() + phone_number.length() + citation_number.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllAppeals(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(univ_id.length() > 0) {
	        	whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
	        }
	        if(phone_number.length() > 0) {
	        	whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
	        }
            if(citation_number.length() > 0) {
	        	whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
	        }
	        query = sqlHelper.merger(whereMap);
	        appeals.deleteFiltered(statement, query);
        }
	}
    
    public void pay(Statement statement) {
		System.out.print("Enter Citation Number (String): ");
		String citation_number = sc.nextLine();
		Citation c = new Citation();
		if (citation_number.length() > 0 && !c.containsCitation(statement, citation_number)) {
			System.out.println("Citation not present!");
        	return;
		}
		String query1 = "Select * from citation where citation_number = '" + citation_number + "';";
		String query2 = "select * from checks where citation_number =  '" + citation_number + "';";
		String query = "Update citation set payment_status = 1 where citation_number = '" + citation_number + "';";
		ResultSet result = null;
		ResultSet result2 = null;
		String category = "";
		String vehicle_id = "";
		try {
			result = statement.executeQuery(query1);
			while (result.next()) {
				category = result.getString("category");
			}
			result2 = statement.executeQuery(query2);
			while(result2.next()) {
				vehicle_id = result2.getString("license_number");
			}
			String query3 = "select * from category_fee where category = '" + category + "';";
			ResultSet result3 = statement.executeQuery(query3);
			String fee = "";
			while (result3.next()) {
				fee = result3.getString("fee");
			}
			String query4 = "select * from vehicle where license_number = '" + vehicle_id + "';";
			ResultSet result4 = statement.executeQuery(query4);
			String is_handicapped = "";
			while (result4.next()) {
				is_handicapped = result4.getString("is_handicapped");
			}
			int fees = 0;
			if (Integer.valueOf(is_handicapped) == 1) {
				fees = Integer.valueOf(fee) / 2;
			} else {
				fees = Integer.valueOf(fee);
			}
			System.out.println("Paid Fee of $" + fees);
            statement.executeUpdate(query);
            System.out.println("Completed: Citation Paid Query Update");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
