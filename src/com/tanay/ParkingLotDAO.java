package com.tanay;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLotDAO {
	Scanner sc = new Scanner(System.in);
	
	public void createParkingLot(Statement statement) {
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
        if(lot_name.length() > 0) {
        	query += "lot_name=" + sqlHelper.singleQuotes(lot_name);
        }
        if(address.length() > 0) {
        	query += "AND address=" + sqlHelper.singleQuotes(address);
        }
        
        ResultSet result = parkingLot.viewFiltered(statement, query);
        
        try {
        	if(!result.next()) {
        		System.out.println("No Rows returned");
        	}
			while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("address") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
