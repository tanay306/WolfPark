package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class PermitDAO {
	Scanner sc = new Scanner(System.in);

	public void menuPermit(Statement statement, Connection connection) {
		System.out.print("\nPermit Sub-Menu\n"
				+ "a. Create Permit\n"
				+ "b. Insert Permit\n"
				+ "c. View All Permit\n"
				+ "d. View Specific Permit\n"
				+ "e. Update Permit\n"
				+ "f. Delete Permit\n"
				+ "Select one option: ");

		PermitDAO permitDAO = new PermitDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				try {
					permitDAO.createPermit(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					Main.close();
				}
				break;
			case "b":
				permitDAO.insertPermit(statement, connection);
				break;
			case "c":
				permitDAO.viewAllPermit(statement);
				break;
			case "d":
				permitDAO.viewPermitByFilters(statement);
				break;
			case "e":
				permitDAO.updatePermit(statement);
				break;
			case "f":
				permitDAO.deletePermitByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

	public void createPermit(Statement statement, Connection connection) throws SQLException {
		if (SQLHelper.tableExists(connection, "permit")) {
			System.out.println("Table Already Exists");
		} else {
			Permit.create(statement);
		}
	}

	public void insertPermit(Statement statement, Connection connection) {

		System.out.print("Enter Permit Id (String): ");
		String permit_id = sc.nextLine();

		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
		String start_date = sc.nextLine();

		System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
		String expiration_date = sc.nextLine();

		System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
		String expiration_time = sc.nextLine();

		System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();

		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();

		Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);

		char special_event = '0';
		String univ_id = permit.getUnivId(statement);
		String phone_number = permit.getPhoneNumber(statement);

		switch (type) {
			case "special event":
				special_event = '1';
				break;
			case "Park & Ride":
				special_event = '1';
				break;
		}

		HasPermit hasPermit = new HasPermit(univ_id, phone_number, permit_id, String.valueOf(special_event));

		if (permit.containsPermit(statement)) {
			System.out.println("Permit already present!");
			return;
		}

		if (!permit.containsVehicleForeign(statement)) {
			System.out.println("Vehicle license number is incorrect, Vehicle is not registered!");
			return;
		}

		char status = hasPermit.status(statement).charAt(0);
		int no_of_permits_count = Integer.parseInt(hasPermit.noOfPermits(statement));
		int special_event_count = hasPermit.noOfSpecialPermit(statement);

		if (hasPermit.containsHasPermitAll(statement)) {
			System.out.println("Duplicate Primary key, Cannot insert this ROW.");
			return;
		}

		if (status == 'E' && ((no_of_permits_count == 2 && special_event == '0')
				|| (special_event_count == 2 && special_event == '1'))) {
			System.out.println("Maximum permit limit reached for Employee i.e 2 + 2, Cannot add a Permit!");
			return;
		}

		if (status == 'S' && ((no_of_permits_count == 1 && special_event == '0')
				|| (special_event_count == 1 && special_event == '1'))) {
			System.out.println("Maximum permit limit reached for Student i.e 1 + 1, Cannot add a Permit!");
			return;
		}

		if (status == 'V' && no_of_permits_count == 1) {
			System.out.println("Maximum permit limit reached for Visitor i.e 1, Cannot add a Permit!");
			return;
		}

		try {
			// ********* Starting Transaction **************
			connection.setAutoCommit(false);

			// 1 or more queries or updates
			permit.insert(statement);
			hasPermit.insert(statement);
			if (Integer.parseInt(univ_id) == -1) {
				System.out.println("Unknown Driver Detected....");
			} else if (status == 'V') {
				hasPermit.updateDriver(statement, 0, true);
			} else if (special_event == '0') {
				hasPermit.updateDriver(statement, no_of_permits_count, true);
			}
			// Code to test Transactions.
			// if(Integer.parseInt(univ_id) == -1) {
			// throw new SQLException("Checking Transaction");
			// }
			// ********* Commiting Transaction **************
			connection.commit();
			System.out.println("Transaction => Success");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			if (connection != null) {
				try {
					// ********* Rollback Transaction **************
					connection.rollback();
					System.out.println("Transaction Failed, Rolling back...");
					connection.setAutoCommit(true);
				} catch (SQLException e1) {
					System.out.println(e.getMessage());
				}

			}
		}
	}

	public void viewAllPermit(Statement statement) {
		ResultSet result = Permit.view(statement);

		try {
			while (result.next()) {
				System.out
						.println("[(" + result.getString("permit_id") + "), (" + result.getString("start_date") + "), ("
								+ result.getString("expiration_date") + "), (" + result.getString("expiration_time")
								+ "), (" + result.getString("vehicle_id") + "), ("
								+ result.getString("type") + ")]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewPermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (String): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
		String start_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
		String expiration_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
		String expiration_time = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();

		SQLHelper sqlHelper = new SQLHelper();

		if (permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length()
				+ vehicle_id.length() + type.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllPermit(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (start_date.length() > 0) {
				whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
			}
			if (expiration_date.length() > 0) {
				whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
			}
			if (expiration_time.length() > 0) {
				whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
			}
			if (vehicle_id.length() > 0) {
				whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
			}
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}

			Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);
			String query = "";

			query = sqlHelper.merger(whereMap);

			ResultSet result = permit.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out.println("[(" + result.getString("permit_id") + "), ("
								+ result.getString("start_date") + "), ("
								+ result.getString("expiration_date") + "), (" + result.getString("expiration_time")
								+ "), (" + result.getString("vehicle_id") + "), ("
								+ result.getString("type") + ")]");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updatePermit(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (String): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Permit Id (Integer): ");
		String permit_id_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
		String start_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Permit Start Date (Date in yyyy-mm-dd format): ");
		String start_date_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
		String expiration_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Permit expiration Date (Date in yyyy-mm-dd format): ");
		String expiration_date_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
		String expiration_time = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Permit Expiration time (Time in hh:mm:ss format): ");
		String expiration_time_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Vehicle license number (String): ");
		String vehicle_id_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type_new = sc.nextLine();

		Permit permit = new Permit(permit_id, start_date, expiration_date, expiration_time, vehicle_id, type);
		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();

		if (permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length()
				+ vehicle_id.length() + type.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllPermit(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (start_date.length() > 0) {
				whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
			}
			if (expiration_date.length() > 0) {
				whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
			}
			if (expiration_time.length() > 0) {
				whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
			}
			if (vehicle_id.length() > 0) {
				whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
			}
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}

			queryWhere = sqlHelper.merger(whereMap);

			HashMap<String, String> setMap = new HashMap<String, String>();
			if (permit_id_new.length() > 0) {
				setMap.put("permit_id", sqlHelper.singleQuotes(permit_id_new));
				if (Permit.containsPermit(statement, permit_id_new)) {
					System.out.println("Permit already present!");
					return;
				}
			}
			if (start_date_new.length() > 0) {
				setMap.put("start_date", sqlHelper.singleQuotes(start_date_new));
			}
			if (expiration_date_new.length() > 0) {
				setMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date_new));
			}
			if (expiration_time_new.length() > 0) {
				setMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time_new));
			}
			if (vehicle_id_new.length() > 0) {
				setMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id_new));

				if (Permit.containsVehicle(statement, vehicle_id_new)) {
					System.out.println("Vehicle already registered!");
					return;
				}

				if (!Permit.containsVehicleForeign(statement, vehicle_id_new)) {
					System.out.println("Vehicle license number is incorrect, Vehicle is not registered!");
					return;
				}
			}
			if (type_new.length() > 0) {
				setMap.put("type", sqlHelper.singleQuotes(type_new));
			}

			querySet = sqlHelper.merger(setMap, ", ");

			permit.updateFiltered(statement, queryWhere, querySet);

		}

	}

	public void deletePermitByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Permit Id (String): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Start Date (Date in yyyy-mm-dd format): ");
		String start_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit expiration Date (Date in yyyy-mm-dd format): ");
		String expiration_date = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Expiration time (Time in hh:mm:ss format): ");
		String expiration_time = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Vehicle license number (String): ");
		String vehicle_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Permit type from the following (String): \n "
				+ "residential, commuter, peak hours, special event, and Park & Ride: ");
		String type = sc.nextLine();

		SQLHelper sqlHelper = new SQLHelper();

		if (permit_id.length() + start_date.length() + expiration_date.length() + expiration_time.length()
				+ vehicle_id.length() + type.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllPermit(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (start_date.length() > 0) {
				whereMap.put("start_date", sqlHelper.singleQuotes(start_date));
			}
			if (expiration_date.length() > 0) {
				whereMap.put("expiration_date", sqlHelper.singleQuotes(expiration_date));
			}
			if (expiration_time.length() > 0) {
				whereMap.put("expiration_time", sqlHelper.singleQuotes(expiration_time));
			}
			if (vehicle_id.length() > 0) {
				whereMap.put("vehicle_id", sqlHelper.singleQuotes(vehicle_id));
			}
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}

			String query = "";

			query = sqlHelper.merger(whereMap);

			Permit.deleteFiltered(statement, query);
		}
	}
}
