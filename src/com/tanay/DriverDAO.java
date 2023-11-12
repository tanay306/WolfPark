package com.tanay;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverDAO {
	Scanner sc = new Scanner(System.in);
	
	public void createDriver(Statement statement) {
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();
		System.out.print("Enter Phone Number (String): ");
        String phone_number = sc.nextLine();
        System.out.print("Enter your Name (String): ");
        String name = sc.nextLine();
        System.out.print("Enter Status (String): ");
        String status = sc.nextLine();
        System.out.print("Enter Number of Permits (String): ");
        int no_of_permits = Integer.parseInt(sc.nextLine());
        
        Driver driver= new Driver(univ_id, phone_number, name, status, no_of_permits);
        driver.insert(statement);
	}
	
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
}
