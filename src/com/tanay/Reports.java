package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reports {

    Scanner sc = new Scanner(System.in);

    public Reports() {

    }

    public void menuCategory(Statement statement, Connection connection) {
		System.out.print("\nReoprt Sub-Menu\n"
				+ "a. Generate a report for citations.\n"
				+ "b. For each lot, generate a report for the total number of citations given in all zones in the lot\n"
				+ "c. Return the list of zones for each lot as tuple pairs (lot, zone). \n"
				+ "d. Return the number of cars that are currently in violation.\n"
                + "e. Return the number of employees having permits for a given parking zone.\n"
				+ "f. Return permit information given an ID or phone number.\n"
				+ "g. Return an available space number given a space type in a given parking lot.\n"
				+ "Select one option: ");
		
		Reports report = new Reports();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				report.getAllCitations(statement);
				break;
			case "b": 
				report.totalCitations(statement);
				break;
			case "c":
				report.returnList(statement);
				break;
			case "d":
				report.carsInViolation(statement);
				break;
			case "e":
				report.numEmployees(statement);
				break;
			case "f":
				report.permitInfo(statement);
				break;
            case "g":
                report.availSpace(statement);
                break;
			default:
				System.out.println("Invalid Entry");
		}
	}

    public void getAllCitations(Statement statement) {
        CitationDAO c = new CitationDAO();
        c.viewAllCitation(statement);
    }

    public void totalCitations(Statement statement) {
        String query = "select lot_name,zone_id, count(*) as total_no_of_citations from citation group by lot_name,zone_id;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), (" + result.getString("total_no_of_citations") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnList(Statement statement) {
        String query = "select lot_name, zone_id from space;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void carsInViolation(Statement statement) {
        String query = "select count(*) as total from checks where citation_number is not null;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("total") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void numEmployees(Statement statement) {
        System.out.print("Enter Parking Lot (String): ");
		String lot = sc.nextLine();
		System.out.print("Enter Zone (String): ");
        String zone = sc.nextLine();
        String query = "select count(*) as total from driver natural join haspermit natural join associatedwith where status = 'E' and lot_name = '" + lot + "' and zone_id = '" + zone + "';";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("total") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void permitInfo(Statement statement) {
        SQLHelper.skipper();
        System.out.print("Enter UnivId (String): ");
		String univ_id = sc.nextLine();
        SQLHelper.skipper();
		System.out.print("Enter Phone number (String): ");
        String phone_number = sc.nextLine();
        
        if (univ_id.length() == 0 && phone_number.length() == 0) {
            System.out.println("No Filters provided!");
            return;
        }
        SQLHelper sqlHelper = new SQLHelper();
        HashMap<String, String> whereMap = new HashMap<String, String>();
        if(univ_id.length() > 0) {
            whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
        }
        if(phone_number.length() > 0) {
            whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
        }
        String where = sqlHelper.merger(whereMap);
        String query = "select * from haspermit natural join permit natural join associatedwith where " + where + ";";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("permit_id") + "), (" + result.getString("univ_id") + "), (" + result.getString("phone_number") + "), (" + result.getString("special_event") + "), (" + result.getString("start_date") + "), (" + result.getString("expiration_date") + "), (" + result.getString("expiration_time") + "), (" + result.getString("vehicle_id") + "), (" + result.getString("type") + "), (" + result.getString("space_number") + "), (" + result.getString("zone_id") + "), (" + result.getString("lot_name") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void availSpace(Statement statement) {
        System.out.print("Enter Space Type (String): ");
		String type = sc.nextLine();
		if (!isPresent(statement, type)) {
            System.out.println("No such type is present!");
            return;
        }
        String query = "select * from space where availability_slot = 1 and type = '" + type + "';";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), (" + result.getString("space_number") + ")]");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPresent(Statement statement, String type) {
        String query = "select * from space where type = '" + type + "';";
        ResultSet result = null;
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
        return false;
    }
}
