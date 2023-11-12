package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLotDAO {
	Scanner sc = new Scanner(System.in);
	
	public void createParkingLot(Statement statement, Connection connection) throws SQLException {
		SQLHelper sqlHelper = new SQLHelper();
		if(sqlHelper.tableExists(connection, "parkinglot")) {
			System.out.println("Table Already Exists");
		} else {
			ParkingLot parkingLot = new ParkingLot();
			parkingLot.create(statement);
		}
	}
	
	public void insertParkingLot(Statement statement) {
		System.out.print("Enter Lot Name (String): ");
		String lot_name = sc.nextLine();
		System.out.print("Enter Address (String): ");
        String address = sc.nextLine();
        
        ParkingLot parkingLot = new ParkingLot(lot_name, address);
        parkingLot.insert(statement);
	}
	
	public void viewAllParkingLot(Statement statement) {
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
	
	public void viewParkingLotByFilters(Statement statement) {
		System.out.print("Click enter to skip this filter\nEnter Lot Name(String): ");
		String lot_name = sc.nextLine();
		System.out.print("Click enter to skip this filter\nEnter Address (String): ");
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
	
	public void updateParkingLot(Statement statement) {
		System.out.print("Click enter to skip this filter\nEnter Lot Name(String): ");
		String lot_name = sc.nextLine();
		System.out.print("Click enter to skip this filter\nEnter NEW Lot Name(String): ");
		String lot_name_new = sc.nextLine();
		
		System.out.print("Click enter to skip this filter\nEnter Address(String): ");
        String address = sc.nextLine();
        System.out.print("Click enter to skip this filter\nEnter NEW Address(String): ");
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
	        if(lot_name.length() > 0) {
	        	setMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
	        }
	        if(address.length() > 0) {
	        	setMap.put("address", sqlHelper.singleQuotes(address));
	        }
	        
	        querySet = sqlHelper.merger(whereMap);
	        
	        parkingLot.viewUpdateFiltered(statement, queryWhere, querySet);
        }
	}
}
