package com.tanay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CitationDAO {

    Scanner sc = new Scanner(System.in);
	
	public void menuCategory(Statement statement, Connection connection) {
		System.out.print("\nCitation Sub-Menu\n"
				+ "a. Create Citation\n"
				+ "b. Insert Citation\n"
				+ "c. View All Citation\n"
				+ "d. View Specific Citation\n"
				+ "e. Update Citation\n"
				+ "f. Delete Citation\n"
				+ "Select one option: ");
		
		CitationDAO citationDAO = new CitationDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					citationDAO.createCitation(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				citationDAO.insertCitation(statement);
				break;
			case "c":
				citationDAO.viewAllCitation(statement);
				break;
			case "d":
				citationDAO.viewCitationByFilters(statement);
				break;
			case "e":
				citationDAO.updateCitation(statement);
				break;
			case "f":
				// citationDAO.deleteCitationByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

    public void createCitation(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "citation")) {
			System.out.println("Table Already Exists");
		} else {
			Citation citation = new Citation();
			citation.create(statement);
		}
	}

    public void insertCitation(Statement statement) {
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();
		System.out.print("Enter Citation Date (Date): ");
        String citation_date = sc.nextLine();
        System.out.print("Enter Citation Time (Time): ");
        String citation_time = sc.nextLine();
        System.out.print("Enter Category (String): ");
        String category = sc.nextLine();
        System.out.print("Enter Payment Status (Boolean): ");
        String payment_status = sc.nextLine();
        System.out.print("Enter Parking Lot (String): ");
        String lot_name = sc.nextLine();
        System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        System.out.print("Enter Space Number (String): ");
        String space_number = sc.nextLine();

        Citation citation = new Citation(citation_number, citation_date, citation_time, category, payment_status, lot_name, zone_id, space_number);
        if (citation.containsCitation(statement)) {
        	System.out.println("Citation already present!");
        	return;
        }
        if (! citation.containsCategory(statement)) {
            System.out.println("Category not present!");
        	return;
        }
        if (! citation.containsLot(statement)) {
            System.out.println("Parking Lot not present!");
        	return;
        }
        if (! citation.containsZone(statement)) {
            System.out.println("Zone not present!");
        	return;
        }
        if (! citation.containsSpace(statement)) {
            System.out.println("Space not present!");
        	return;
        }
        citation.insert(statement);
	}

    public void viewAllCitation(Statement statement) {
		Citation citation = new Citation();
		ResultSet result = citation.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("citation_number") + "), (" + result.getString("citation_date") + "), (" + result.getString("citation_time") + "), (" + result.getString("category") + "), (" + result.getString("payment_status") + "), (" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), (" + result.getString("space_number") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void viewCitationByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter Citation Date (Date): ");
        String citation_date = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Citation Time (Time): ");
        String citation_time = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Category (String): ");
        String category = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Payment Status (Boolean): ");
        String payment_status = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Parking Lot (String): ");
        String lot_name = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Space Number (String): ");
        String space_number = sc.nextLine();
        
        Citation citation = new Citation(citation_number, citation_date, citation_time, category, payment_status, lot_name, zone_id, space_number);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(citation_number.length() + citation_date.length() + citation_time.length() + category.length() + payment_status.length() + lot_name.length() + zone_id.length() + space_number.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllCitation(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(citation_number.length() > 0) {
	        	whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
	        }
	        if(citation_date.length() > 0) {
	        	whereMap.put("citation_date", sqlHelper.singleQuotes(citation_date));
	        }
            if(citation_time.length() > 0) {
	        	whereMap.put("citation_time", sqlHelper.singleQuotes(citation_time));
	        }
            if(category.length() > 0) {
	        	whereMap.put("category", sqlHelper.singleQuotes(category));
	        }
            if(payment_status.length() > 0) {
	        	whereMap.put("payment_status", sqlHelper.singleQuotes(payment_status));
	        }
            if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
            if(zone_id.length() > 0) {
	        	whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
	        }
            if(space_number.length() > 0) {
	        	whereMap.put("space_number", sqlHelper.singleQuotes(space_number));
	        }
	        query = sqlHelper.merger(whereMap);
            System.out.println(query);
	        ResultSet result = citation.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("citation_number") + "), (" + result.getString("citation_date") + "), (" + result.getString("citation_time") + "), (" + result.getString("category") + "), (" + result.getString("payment_status") + "), (" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), (" + result.getString("space_number") + ")]");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}

    public void updateCitation(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter New Citation Number (Int): ");
		String citation_number_new = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter Citation Date (Date): ");
        String citation_date = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter New Citation Date (Date): ");
        String citation_date_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Citation Time (Time): ");
        String citation_time = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Citation Time (Time): ");
        String citation_time_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Category (String): ");
        String category = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Category (String): ");
        String category_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Payment Status (Boolean): ");
        String payment_status = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Payment Status (Boolean): ");
        String payment_status_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Parking Lot (String): ");
        String lot_name = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Parking Lot (String): ");
        String lot_name_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Zone ID (String): ");
        String zone_id_new = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter Space Number (String): ");
        String space_number = sc.nextLine();
        SQLHelper.skipper();
        System.out.print("Enter New Space Number (String): ");
        String space_number_new = sc.nextLine();
        
        Citation citation = new Citation(citation_number, citation_date, citation_time, category, payment_status, lot_name, zone_id, space_number);
        
        if (citation_number_new.length() > 0 && citation.containsCitation(statement, citation_number_new)) {
        	System.out.println("Citation already present!");
        	return;
        }
        if (! citation.containsCategory(statement, category_new) && category_new.length() > 0) {
            System.out.println("Category not present!");
        	return;
        }
        if (! citation.containsLot(statement, lot_name_new) && lot_name_new.length() > 0) {
            System.out.println("Parking Lot not present!");
        	return;
        }
        if (! citation.containsZone(statement, zone_id_new) && zone_id_new.length() > 0) {
            System.out.println("Zone not present!");
        	return;
        }
        if (! citation.containsSpace(statement, space_number_new) && space_number_new.length() > 0) {
            System.out.println("Space not present!");
        	return;
        }
        String check_lot = lot_name_new == null ? lot_name: lot_name_new;
        String check_zone = zone_id_new == null ? zone_id: zone_id_new;
        String check_space = space_number_new == null ? space_number: space_number_new;
        if (! citation.containsAll(statement, check_lot, check_zone, check_space)) {
        	System.out.println("Invalid input!");
        	return;
        }
        String queryWhere = "";
        String querySet = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(citation_number.length() + citation_date.length() + citation_time.length() + category.length() + payment_status.length() + lot_name.length() + zone_id.length() + space_number.length() == 0 || citation_number_new.length() + citation_date_new.length() + citation_time_new.length() + category_new.length() + payment_status_new.length() + lot_name_new.length() + zone_id_new.length() + space_number_new.length() == 0) {
        	System.out.println("No Filters provided, cannot update a row");
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(citation_number.length() > 0) {
	        	whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
	        }
	        if(citation_date.length() > 0) {
	        	whereMap.put("citation_date", sqlHelper.singleQuotes(citation_date));
	        }
            if(citation_time.length() > 0) {
	        	whereMap.put("citation_time", sqlHelper.singleQuotes(citation_time));
	        }
            if(category.length() > 0) {
	        	whereMap.put("category", sqlHelper.singleQuotes(category));
	        }
            if(payment_status.length() > 0) {
	        	whereMap.put("payment_status", sqlHelper.singleQuotes(payment_status));
	        }
            if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
            if(zone_id.length() > 0) {
	        	whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
	        }
            if(space_number.length() > 0) {
	        	whereMap.put("space_number", sqlHelper.singleQuotes(space_number));
	        }
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(citation_number_new.length() > 0) {
	        	setMap.put("citation_number", sqlHelper.singleQuotes(citation_number_new));
	        }
	        if(citation_date_new.length() > 0) {
	        	setMap.put("citation_date", sqlHelper.singleQuotes(citation_date_new));
	        }
            if(citation_time_new.length() > 0) {
	        	setMap.put("citation_time", sqlHelper.singleQuotes(citation_time_new));
	        }
            if(category_new.length() > 0) {
	        	setMap.put("category", sqlHelper.singleQuotes(category_new));
	        }
            if(payment_status_new.length() > 0) {
	        	setMap.put("payment_status", sqlHelper.singleQuotes(payment_status_new));
	        }
            if(lot_name_new.length() > 0) {
	        	setMap.put("lot_name", sqlHelper.singleQuotes(lot_name_new));
	        }
            if(zone_id_new.length() > 0) {
	        	setMap.put("zone_id", sqlHelper.singleQuotes(zone_id_new));
	        }
            if(space_number_new.length() > 0) {
	        	setMap.put("space_number", sqlHelper.singleQuotes(space_number_new));
	        }
	        querySet = sqlHelper.merger(setMap);
	        
	        citation.viewUpdateFiltered(statement, queryWhere, querySet);
        }
	}

}