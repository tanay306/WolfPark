package com.tanay;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChecksDAO {

	Scanner sc = new Scanner(System.in);

	public void menuCategory(Statement statement, Connection connection) {
		System.out.print("\nChecks Sub-Menu\n"
				+ "a. Create Checks\n"
				+ "b. Insert Checks\n"
				+ "c. View All Checks\n"
				+ "d. View Specific Checks\n"
				+ "e. Update Checks\n"
				+ "f. Delete Checks\n"
				+ "Select one option: ");

		ChecksDAO checksDAO = new ChecksDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				try {
					checksDAO.createChecks(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					Main.close();
				}
				break;
			case "b":
				checksDAO.insertChecks(statement);
				break;
			case "c":
				checksDAO.viewAllChecks(statement);
				break;
			case "d":
				checksDAO.viewChecksByFilters(statement);
				break;
			case "e":
				checksDAO.updateChecks(statement);
				break;
			case "f":
				checksDAO.deleteChecksByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

	public void createChecks(Statement statement, Connection connection) throws SQLException {
		if (SQLHelper.tableExists(connection, "checks")) {
			System.out.println("lll");
			System.out.println("Table Already Exists");
		} else {
			Checks checks = new Checks();
			checks.create(statement);
		}
	}

	public void insertChecks(Statement statement) {
		System.out.print("Enter License Number (String): ");
		String license_number = sc.nextLine();
		System.out.print("Enter Permit Id (Int): ");
		String permit_id = sc.nextLine();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();

		Checks checks = new Checks(license_number, permit_id, citation_number);
		if (checks.containsAll(statement, license_number, permit_id, citation_number)) {
			System.out.println("Checks already present!");
			return;
		}
		checks.insert(statement);
	}

	public void viewAllChecks(Statement statement) {
		Checks checks = new Checks();
		ResultSet result = checks.view(statement);

		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("license_number") + "), (" + result.getString("permit_id")
						+ "), (" + result.getString("citation_number") + ")]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewChecksByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter License Number (String): ");
		String license_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Int): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();

		Checks checks = new Checks(license_number, permit_id, citation_number);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (license_number.length() + permit_id.length() + citation_number.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllChecks(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (citation_number.length() > 0) {
				whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
			}
			query = sqlHelper.merger(whereMap);
			ResultSet result = checks.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out.println("[(" + result.getString("license_number") + "), ("
								+ result.getString("permit_id") + "), (" + result.getString("citation_number") + ")]");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updateChecks(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter License Number (String): ");
		String license_number = sc.nextLine();
		SQLHelper.skipper();
		System.out.print("Enter New License Number (String): ");
		String license_number_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Int): ");
		String permit_id = sc.nextLine();
		SQLHelper.skipper();
		System.out.print("Enter New Permit Id (Int): ");
		String permit_id_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();
		SQLHelper.skipper();
		System.out.print("Enter New Citation Number (Int): ");
		String citation_number_new = sc.nextLine();

		Checks checks = new Checks(license_number, permit_id, citation_number);
		if (license_number_new.length() > 0 && !checks.containsLicenseNumber(statement, license_number_new)) {
			System.out.println("License Number not present!");
			return;
		}
		if (permit_id_new.length() > 0 && !checks.containsPermit(statement, permit_id_new)) {
			System.out.println("Permit ID not present!");
			return;
		}
		if (citation_number_new.length() > 0 && !checks.containsCitation(statement, citation_number_new)) {
			System.out.println("Citation Number not present!");
			return;
		}

		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (license_number.length() + permit_id.length() + citation_number.length() == 0
				|| license_number_new.length() + permit_id_new.length() + citation_number_new.length() == 0) {
			System.out.println("No Filters provided, cannot update a row");
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (citation_number.length() > 0) {
				whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
			}
			queryWhere = sqlHelper.merger(whereMap);

			HashMap<String, String> setMap = new HashMap<String, String>();
			if (license_number_new.length() > 0) {
				whereMap.put("license_number_new", sqlHelper.singleQuotes(license_number_new));
			}
			if (permit_id_new.length() > 0) {
				whereMap.put("permit_id_new", sqlHelper.singleQuotes(permit_id_new));
			}
			if (citation_number_new.length() > 0) {
				whereMap.put("citation_number_new", sqlHelper.singleQuotes(citation_number_new));
			}
			querySet = sqlHelper.mergerUpdate(setMap);

			checks.viewUpdateFiltered(statement, queryWhere, querySet);
		}
	}

	public void deleteChecksByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter License Number (String): ");
		String license_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Permit Id (Int): ");
		String permit_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Citation Number (Int): ");
		String citation_number = sc.nextLine();

		Checks checks = new Checks(license_number, permit_id, citation_number);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (license_number.length() + permit_id.length() + citation_number.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllChecks(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (permit_id.length() > 0) {
				whereMap.put("permit_id", sqlHelper.singleQuotes(permit_id));
			}
			if (citation_number.length() > 0) {
				whereMap.put("citation_number", sqlHelper.singleQuotes(citation_number));
			}
			query = sqlHelper.merger(whereMap);
			checks.deleteFiltered(statement, query);
		}
	}

}
