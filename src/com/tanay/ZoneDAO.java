package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class ZoneDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuZone(Statement statement, Connection connection) {
		System.out.print("\nZone Sub-Menu\n"
				+ "a. Create Zone\n"
				+ "b. Insert Zone\n"
				+ "c. View All Zone\n"
				+ "d. View Specific Zone\n"
				+ "e. Update Zone\n"
				+ "f. Delete Zone\n"
				+ "Select one option: ");
		
		ZoneDAO zoneDAO = new ZoneDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					zoneDAO.createZone(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				zoneDAO.insertZone(statement);
				break;
			case "c":
				zoneDAO.viewAllZone(statement);
				break;
			case "d":
				zoneDAO.viewZoneByFilters(statement);
				break;
			case "e":
				zoneDAO.updateParkingLot(statement);
				break;
			case "f":
				zoneDAO.deleteZoneByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}
	
	private void createZone(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "zone")) {
			System.out.println("Table Already Exists");
		} else {
			Zone.create(statement);
		}
	}
	
	private void insertZone(Statement statement) {
		System.out.print("Enter Lot Name (String): ");
		String lot_name = sc.nextLine();
		System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        
        Zone zone = new Zone(lot_name, zone_id);
        
        if (!zone.containsParkingLot(statement)) {
        	System.out.println("Parking Lot not present!");
        	return;
        }
        
        zone.insert(statement);
	}
	
	private void viewAllZone(Statement statement) {
		ResultSet result = Zone.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void viewZoneByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        
        Zone zone = new Zone(lot_name, zone_id);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + zone_id.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllZone(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(zone_id.length() > 0) {
	        	whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
	        }
	        
	        query = sqlHelper.merger(whereMap);
	        ResultSet result = zone.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + ")]");
	        		}while (result.next());
	        	} else {
	        		System.out.println("*** No Rows returned ***");
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	private void updateParkingLot(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter NEW Lot Name(String): ");
		String lot_name_new = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Zone ID(String): ");
        String zone_id = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Zone ID(String): ");
		String zone_id_new = sc.nextLine();
        
        Zone zone = new Zone(lot_name, zone_id);
        String queryWhere = "";
        String querySet = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + zone_id.length() == 0 || lot_name_new.length() + zone_id_new.length() == 0) {
        	System.out.println("No Filters provided, cannot update a row");
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(zone_id.length() > 0) {
	        	whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
	        }
	        
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(lot_name_new.length() > 0) {
	        	setMap.put("lot_name", sqlHelper.singleQuotes(lot_name_new));
	        }
	        if(zone_id_new.length() > 0) {
	        	setMap.put("zone_id", sqlHelper.singleQuotes(zone_id_new));
	        }
	        querySet = sqlHelper.mergerUpdate(setMap);
	        
	        zone.updateFiltered(statement, queryWhere, querySet);;
        }
	}
	
	private void deleteZoneByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + zone_id.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllZone(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(zone_id.length() > 0) {
	        	whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
	        }
	        
	        query = sqlHelper.merger(whereMap);
	        Zone.deleteFiltered(statement, query);
        }
	}
}
