package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class SpaceDAO {
	Scanner sc = new Scanner(System.in);

	public void menuSpace(Statement statement, Connection connection) {
		System.out.print("\nSpace Sub-Menu\n"
				+ "a. Create Space\n"
				+ "b. Insert Space\n"
				+ "c. View All Space\n"
				+ "d. View Specific Space\n"
				+ "e. Update Space\n"
				+ "f. Delete Space\n"
				+ "Select one option: ");

		SpaceDAO spaceDAO = new SpaceDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				spaceDAO.createSpace(statement, connection);
				break;
			case "b":
				spaceDAO.insertSpace(statement);
				break;
			case "c":
				spaceDAO.viewAllSpace(statement);
				break;
			case "d":
				spaceDAO.viewSpaceByFilters(statement);
				break;
			case "e":
				spaceDAO.updateSpace(statement);
				break;
			case "f":
				spaceDAO.deleteSpaceByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

	private void createSpace(Statement statement, Connection connection) {
		try {
			if (SQLHelper.tableExists(connection, "space")) {
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

		if (space.containsSpace(statement)) {
        	System.out.println("Space is already registered, Please try Again!");
        	return;
        }
		
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
				System.out.println("[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), ("
						+ result.getString("space_number") + "), (" + result.getString("type") + "), ("
						+ result.getString("availability_slot") + ")]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void viewSpaceByFilters(Statement statement) {
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
		System.out.print("Enter Type (String): ");
		String type = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Availaibility (Boolean 0/1): ");
		String availability_slot = sc.nextLine();

		Space space = new Space(lot_name, zone_id, space_number, type, availability_slot);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + type.length()
				+ availability_slot.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllSpace(statement);
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
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}
			if (availability_slot.length() > 0) {
				whereMap.put("availability_slot", sqlHelper.singleQuotes(availability_slot));
			}

			query = sqlHelper.merger(whereMap);
			ResultSet result = space.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out.println(
								"[(" + result.getString("lot_name") + "), (" + result.getString("zone_id") + "), ("
										+ result.getString("space_number") + "), (" + result.getString("type") + "), ("
										+ result.getString("availability_slot") + ")]");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateSpace(Statement statement) {
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
		System.out.print("Enter Type (String): ");
		String type = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Type (String): ");
		String type_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Availaibility (Boolean 0/1): ");
		String availability_slot = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Availaibility (Boolean 0/1): ");
		String availability_slot_new = sc.nextLine();

		Space space = new Space(lot_name, zone_id, space_number, type, availability_slot);
		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + type.length()
				+ availability_slot.length() == 0
				|| lot_name_new.length() + zone_id_new.length() + space_number_new.length() + type_new.length()
						+ availability_slot_new.length() == 0) {
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
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}
			if (availability_slot.length() > 0) {
				whereMap.put("availability_slot", sqlHelper.singleQuotes(availability_slot));
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
			if (type_new.length() > 0) {
				setMap.put("type", sqlHelper.singleQuotes(type_new));
			}
			if (availability_slot_new.length() > 0) {
				setMap.put("availability_slot", sqlHelper.singleQuotes(availability_slot_new));
			}
			querySet = sqlHelper.mergerUpdate(setMap);

			if (!space.containsZone(statement)) {
				System.out.println("Zone not present!");
				return;
			}

			Space newSpace = new Space(lot_name_new, zone_id_new, space_number_new, type_new, availability_slot_new);
			if (!newSpace.containsZone(statement)) {
				System.out.println("New Zone not present in the zone table!");
				return;
			}

			space.updateFiltered(statement, queryWhere, querySet);
		}
	}

	private void deleteSpaceByFilters(Statement statement) {
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
		System.out.print("Enter Type (String): ");
		String type = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Availaibility (Boolean 0/1): ");
		String availability_slot = sc.nextLine();

		// Space space = new Space(lot_name, zone_id, space_number, type,
		// availability_slot);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (lot_name.length() + zone_id.length() + space_number.length() + type.length()
				+ availability_slot.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllSpace(statement);
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
			if (type.length() > 0) {
				whereMap.put("type", sqlHelper.singleQuotes(type));
			}
			if (availability_slot.length() > 0) {
				whereMap.put("availability_slot", sqlHelper.singleQuotes(availability_slot));
			}

			query = sqlHelper.merger(whereMap);

			Space.deleteFiltered(statement, query);
		}
	}
}
