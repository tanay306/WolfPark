package com.tanay;

import java.util.Scanner;
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
}
