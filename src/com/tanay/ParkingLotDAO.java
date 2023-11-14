package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLotDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuParkingLot(Statement statement, Connection connection) {
		System.out.print("\nParking Lot Sub-Menu\n"
				+ "a. Create Parking Lot\n"
				+ "b. Insert Parking Lot\n"
				+ "c. View All Parking Lot\n"
				+ "d. View Specific Parking Lot\n"
				+ "e. Update Parking Lot\n"
				+ "f. Delete Parking Lot\n"
				+ "Select one option: ");

		ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
		String input = sc.nextLine();
		switch(input) {
			case "a": 
				try {
					parkingLotDAO.createParkingLot(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
		            Main.close();
				}
				break;
			case "b": 
				parkingLotDAO.insertParkingLot(statement);
				break;
			case "c":
				parkingLotDAO.viewAllParkingLot(statement);
				break;
			case "d":
				parkingLotDAO.viewParkingLotByFilters(statement);
				break;
			case "e":
				parkingLotDAO.updateParkingLot(statement);
				break;
			case "f":
				parkingLotDAO.deleteParkingLotByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}
	
	private void createParkingLot(Statement statement, Connection connection) throws SQLException {
		if(SQLHelper.tableExists(connection, "parkinglot")) {
			System.out.println("Table Already Exists");
		} else {
			ParkingLot parkingLot = new ParkingLot();
			parkingLot.create(statement);
		}
	}
	
	private void insertParkingLot(Statement statement) {
		System.out.print("Enter Lot Name (String): ");
		String lot_name = sc.nextLine();
		System.out.print("Enter Address (String): ");
        String address = sc.nextLine();
        
        ParkingLot parkingLot = new ParkingLot(lot_name, address);
        parkingLot.insert(statement);
	}
	
	private void viewAllParkingLot(Statement statement) {
		ParkingLot parkingLot = new ParkingLot();
		ResultSet result = parkingLot.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("address") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void viewParkingLotByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();
		
		SQLHelper.skipper();
		System.out.print("Enter Address (String): ");
        String address = sc.nextLine();
        
        ParkingLot parkingLot = new ParkingLot(lot_name, address);
        String query = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + address.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllParkingLot(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(address.length() > 0) {
	        	whereMap.put("address", sqlHelper.singleQuotes(address));
	        }
	        
	        query = sqlHelper.merger(whereMap);
	        ResultSet result = parkingLot.viewFiltered(statement, query);
	        
	        try {
	        	if(result.next()) {
	        		do{
	        			System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("address") + ")]");
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
		System.out.print("Enter Address(String): ");
        String address = sc.nextLine();
        
        SQLHelper.skipper();
        System.out.print("Enter NEW Address(String): ");
		String address_new = sc.nextLine();
        
        ParkingLot parkingLot = new ParkingLot(lot_name, address);
        String queryWhere = "";
        String querySet = "";
        
        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + address.length() == 0 || lot_name_new.length() + address_new.length() == 0) {
        	System.out.println("No Filters provided, cannot update a row");
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(address.length() > 0) {
	        	whereMap.put("address", sqlHelper.singleQuotes(address));
	        }
	        
	        queryWhere = sqlHelper.merger(whereMap);
	        
	        HashMap<String, String> setMap = new HashMap<String, String>();
	        if(lot_name_new.length() > 0) {
	        	setMap.put("lot_name", sqlHelper.singleQuotes(lot_name_new));
	        }
	        if(address_new.length() > 0) {
	        	setMap.put("address", sqlHelper.singleQuotes(address_new));
	        }
	        querySet = sqlHelper.merger(setMap);

	        parkingLot.viewUpdateFiltered(statement, queryWhere, querySet);
        }
	}
	
	private void deleteParkingLotByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Address (String): ");
        String address = sc.nextLine();

        ParkingLot parkingLot = new ParkingLot(lot_name, address);
        String query = "";

        SQLHelper sqlHelper = new SQLHelper();
        if(lot_name.length() + address.length() == 0) {
        	System.out.println("No Filters provided, showing all rows");
        	viewAllParkingLot(statement);
        } else {
        	HashMap<String, String> whereMap = new HashMap<String, String>();
	        if(lot_name.length() > 0) {
	        	whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(address.length() > 0) {
	        	whereMap.put("address", sqlHelper.singleQuotes(address));
	        }

	        query = sqlHelper.merger(whereMap);
	        parkingLot.deleteFiltered(statement, query);
        }
	}
}
