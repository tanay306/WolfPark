package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SpaceDAO {
	Scanner sc = new Scanner(System.in);
	
	public void menuSpace(Statement statement, Connection connection) {
		//			this.createSpace(statement, connection);
//		this.insertSpace(statement);
		this.viewAllSpace(statement);
	}
	private void createSpace(Statement statement, Connection connection){
		try {
			if(SQLHelper.tableExists(connection, "space")) {
				System.out.println("Table Already Exists");
			} else {
				Space.create(statement);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void insertSpace(Statement statement) {
		System.out.print("Enter Lot Name (String): ");
		String lot_name = sc.nextLine();
		System.out.print("Enter Zone ID (String): ");
        String zone_id = sc.nextLine();
        System.out.print("Enter Space Number (Int): ");
        String space_number = sc.nextLine();
        System.out.print("Enter Type (String): ");
        String type = sc.nextLine();
        System.out.print("Enter Availaibility (Boolean 0/1): ");
        String availability_slot = sc.nextLine();
        
        Space space = new Space(lot_name, zone_id, space_number, type, availability_slot);
        
        if (!space.containsZone(statement)) {
        	System.out.println("Zone not present!");
        	return;
        }
        
        space.insert(statement);
	}
	
	private void viewAllSpace(Statement statement) {
		ResultSet result = Space.view(statement);
		
		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), (" + result.getString("space_number") + "), (" + result.getString("type") + "), (" + result.getString("availability_slot") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
