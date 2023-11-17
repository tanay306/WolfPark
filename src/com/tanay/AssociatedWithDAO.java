package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class AssociatedWithDAO {
	Scanner sc = new Scanner(System.in);

	public void menuAssociatedWith(Statement statement, Connection connection) {
		System.out.print("\nAssociatedWith Sub-Menu\n"
				+ "a. Create AssociatedWith\n"
				+ "b. Insert AssociatedWith\n"
				+ "c. View All AssociatedWith\n"
				+ "d. View Specific AssociatedWith\n"
				+ "e. Update AssociatedWith\n"
				+ "f. Delete AssociatedWith\n"
				+ "Select one option: ");

		AssociatedWithDAO associatedWithDAO = new AssociatedWithDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				associatedWithDAO.createAssociatedWith(statement, connection);
				break;
			case "b":
				associatedWithDAO.insertAssociatedWith(statement);
				break;
			case "c":
				associatedWithDAO.viewAllAssociatedWith(statement);
				break;
			case "d":
				associatedWithDAO.viewAssociatedWithByFilters(statement);
				break;
			case "e":
				associatedWithDAO.updateAssociatedWith(statement);
				break;
			case "f":
				associatedWithDAO.deleteAssociatedWithByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}

	}

	private void createAssociatedWith(Statement statement, Connection connection) {
		try {
			if (SQLHelper.tableExists(connection, "associatedwith")) {
				System.out.println("Table Already Exists");
			} else {
				if (SQLHelper.tableExists(connection, "space") && SQLHelper.tableExists(connection, "permit")) {
					AssociatedWith.create(statement);
				} else {
					System.out.println("\nCannot create table(associatedwith) without space and permit table");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void insertAssociatedWith(Statement statement) {
		System.out.print("Enter Lot Name (String): ");
		String lot_name = sc.nextLine();
		System.out.print("Enter Zone ID (String): ");
		String zone_id = sc.nextLine();
		System.out.print("Enter Space Number (Int): ");
		String space_number = sc.nextLine();
		System.out.print("Enter Permit ID (String): ");
		String permit_id = sc.nextLine();

		AssociatedWith associatedWith = new AssociatedWith(permit_id, space_number, zone_id, lot_name);

		if (!associatedWith.containsPermit(statement)) {
			System.out.println("Permit not present!");
			return;
		}
		if (!associatedWith.containsSpace(statement)) {
			System.out.println("Space not present!");
			return;
		}
		if (associatedWith.containsPermitInAssociatedWith(statement)) {
			System.out.println("Duplicate Permit ID detected!");
			return;
		}

		associatedWith.insert(statement);
	}

	private void viewAllAssociatedWith(Statement statement) {
		ResultSet result = AssociatedWith.view(statement);

		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("permit_id") + "), (" + result.getString("lot_name") + "), ("
						+ result.getString("zone_id") + "), ("
						+ result.getString("space_number") + ")]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void viewAssociatedWithByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Zone ID (String): ");
		String zone_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Space Number (Int): ");
		String space_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit ID (String): ");
		String permit_id = sc.nextLine();

		AssociatedWith associatedWith = new AssociatedWith(permit_id, space_number, zone_id, lot_name);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + permit_id.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllAssociatedWith(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (lot_name.length() > 0) {
				whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
			}
			if (zone_id.length() > 0) {
				whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
			}
			if (space_number.length() > 0) {
				whereMap.put("space_number", sqlHelper.singleQuotes(space_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}

			query = sqlHelper.merger(whereMap);
			ResultSet result = associatedWith.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out.println(
								"[(" + result.getString("permit_id") + "), (" + result.getString("lot_name") + "), ("
										+ result.getString("zone_id") + "), ("
										+ result.getString("space_number") + ")]");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void updateAssociatedWith(Statement statement) {
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

		SQLHelper.skipper();
		System.out.print("Enter Space Number (Int): ");
		String space_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Space Number (Int): ");
		String space_number_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit ID (String): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Permit ID (String): ");
		String permit_id_new = sc.nextLine();

		AssociatedWith associatedWith = new AssociatedWith(permit_id, space_number, zone_id, lot_name);
		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + permit_id.length() == 0
				|| lot_name_new.length() + zone_id_new.length() + space_number_new.length()
						+ permit_id_new.length() == 0) {
			System.out.println("No Filters provided, cannot update a row");
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (lot_name.length() > 0) {
				whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
			}
			if (zone_id.length() > 0) {
				whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
			}
			if (space_number.length() > 0) {
				whereMap.put("space_number", sqlHelper.singleQuotes(space_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			queryWhere = sqlHelper.merger(whereMap);

			HashMap<String, String> setMap = new HashMap<String, String>();
			if (lot_name_new.length() > 0) {
				setMap.put("lot_name", sqlHelper.singleQuotes(lot_name_new));
			} else {
				lot_name_new = lot_name;
			}
			if (zone_id_new.length() > 0) {
				setMap.put("zone_id", sqlHelper.singleQuotes(zone_id_new));
			} else {
				zone_id_new = zone_id;
			}
			if (space_number_new.length() > 0) {
				setMap.put("space_number", sqlHelper.singleQuotes(space_number_new));
			}
			if (permit_id_new.length() > 0) {
				setMap.put("permit_id", sqlHelper.singleQuotes(permit_id_new));
			}
			querySet = sqlHelper.mergerUpdate(setMap);

			if (permit_id.length() > 0 && !associatedWith.containsPermit(statement)) {
				System.out.println("Permit not present!");
				return;
			}
			if (space_number.length() > 0 && !associatedWith.containsSpace(statement)) {
				System.out.println("Space not present!");
				return;
			}

			AssociatedWith associatedWithNew = new AssociatedWith(permit_id_new, space_number_new, zone_id_new,
					lot_name_new);
			if (permit_id_new.length() > 0 && !associatedWithNew.containsPermit(statement)) {
				System.out.println("New Permit ID not present in the Permit table!");
				return;
			}
			if (space_number_new.length() > 0 && !associatedWithNew.containsSpace(statement)) {
				System.out.println("New Space not present in the space table!");
				return;
			}

			associatedWith.updateFiltered(statement, queryWhere, querySet);
		}
	}

	private void deleteAssociatedWithByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Lot Name(String): ");
		String lot_name = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Zone ID (String): ");
		String zone_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Space Number (Int): ");
		String space_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit ID (String): ");
		String permit_id = sc.nextLine();

		// Space space = new Space(lot_name, zone_id, space_number, type,
		// availability_slot);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + permit_id.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllAssociatedWith(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (lot_name.length() > 0) {
				whereMap.put("lot_name", sqlHelper.singleQuotes(lot_name));
			}
			if (zone_id.length() > 0) {
				whereMap.put("zone_id", sqlHelper.singleQuotes(zone_id));
			}
			if (space_number.length() > 0) {
				whereMap.put("space_number", sqlHelper.singleQuotes(space_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}

			query = sqlHelper.merger(whereMap);

			AssociatedWith.deleteFiltered(statement, query);
		}
	}
}
