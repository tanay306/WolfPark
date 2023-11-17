package com.tanay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class VehicleDAO {
	Scanner sc = new Scanner(System.in);

	public void menuVehicle(Statement statement, Connection connection) {
		System.out.print("\nVehicle Sub-Menu\n"
				+ "a. Create Vehicle\n"
				+ "b. Insert Vehicle\n"
				+ "c. View All Vehicle\n"
				+ "d. View Specific Vehicle\n"
				+ "e. Update Vehicle\n"
				+ "f. Delete Vehicle\n"
				+ "Select one option: ");

		VehicleDAO vehicleDAO = new VehicleDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				try {
					vehicleDAO.createVehicle(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					Main.close();
				}
				break;
			case "b":
				vehicleDAO.insertVehicle(statement);
				break;
			case "c":
				vehicleDAO.viewAllVehicle(statement);
				break;
			case "d":
				vehicleDAO.viewVehicleByFilters(statement);
				break;
			case "e":
				vehicleDAO.updateVehicle(statement);
				break;
			case "f":
				vehicleDAO.deleteVehicleByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
				break;
		}
	}

	public void createVehicle(Statement statement, Connection connection) throws SQLException {
		if (SQLHelper.tableExists(connection, "vehicle")) {
			System.out.println("Table Already Exists");
		} else {
			Vehicle.create(statement);
		}
	}

	public void insertVehicle(Statement statement) {

		System.out.print("Enter your License Number (String): ");
		String license_number = sc.nextLine();

		System.out.print("Enter your Car Color(String): ");
		String color = sc.nextLine();

		System.out.print("Enter your Car Model (String): ");
		String model = sc.nextLine();

		System.out.print("Enter your Manufacturer year (Date in yyyy-mm-dd format): ");
		String manufacture_year = sc.nextLine();

		System.out.print("Enter 1 if you are Handicapped (Integer): ");
		int is_handicapped;
		String int_input = sc.nextLine();
		if (int_input.isEmpty()) {
			is_handicapped = 0;
		} else {
			is_handicapped = Integer.parseInt(int_input);
		}

		System.out.print("Enter your Manufacturing Company (String): ");
		String manufacturer = sc.nextLine();

		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();

		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

		Vehicle vehicle = new Vehicle(license_number, color, model, manufacture_year, is_handicapped, manufacturer,
				univ_id, phone_number);

		if (vehicle.containsVehicle(statement)) {
			System.out.println("Vehicle is already registered, Please try Again!");
			return;
		}

		if (!vehicle.containsDriver(statement)) {
			System.out.println("Driver is not registered, Incorrect University Id or Phone Number!");
			return;
		}

		vehicle.insert(statement);
	}

	public void viewAllVehicle(Statement statement) {
		ResultSet result = Vehicle.view(statement);

		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("license_number") + "), (" + result.getString("color")
						+ "), (" + result.getString("model")
						+ "), (" + result.getString("manufacture_year") + "), (" + result.getString("is_handicapped")
						+ "), (" + result.getString("manufacturer")
						+ "), (" + result.getString("univ_id") + "), (" + result.getString("phone_number") + ")]\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewVehicleByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your License Number (String): ");
		String license_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Color(String): ");
		String color = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Model (String): ");
		String model = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturer year (Date in yyyy-mm-dd format): ");
		String manufacture_year = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter 1 if you are Handicapped (Integer): ");
		int is_handicapped;
		String int_input = sc.nextLine();
		if (int_input.isEmpty()) {
			is_handicapped = 0;
		} else {
			is_handicapped = Integer.parseInt(int_input);
		}

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturing Company (String): ");
		String manufacturer = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

		Vehicle vehicle = new Vehicle(license_number, color, model, manufacture_year, is_handicapped, manufacturer,
				univ_id, phone_number);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();

		if (license_number.length() + color.length() + model.length() + manufacture_year.length() + is_handicapped
				+ manufacturer.length() + univ_id.length() + phone_number.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllVehicle(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (color.length() > 0) {
				whereMap.put("color", sqlHelper.singleQuotes(color));
			}
			if (model.length() > 0) {
				whereMap.put("model", sqlHelper.singleQuotes(model));
			}
			if (manufacture_year.length() > 0) {
				whereMap.put("manufacture_year", sqlHelper.singleQuotes(manufacture_year));
			}
			if (manufacturer.length() > 0) {
				whereMap.put("manufacturer", sqlHelper.singleQuotes(manufacturer));
			}
			if (univ_id.length() > 0) {
				whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
			}
			if (phone_number.length() > 0) {
				whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
			}

			query = sqlHelper.merger(whereMap);

			if (is_handicapped > 0) {
				if (query.length() > 0) {
					query += "AND is_handicapped=" + is_handicapped;
				} else {
					query += "is_handicapped=" + is_handicapped;
				}
			}
			ResultSet result = vehicle.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out.println(
								"[(" + result.getString("license_number") + "), (" + result.getString("color") + "), ("
										+ result.getString("model") + "), (" + result.getString("manufacture_year")
										+ "), (" + result.getString("is_handicapped") + "), ("
										+ result.getString("manufacturer") + "), (" + result.getString("univ_id")
										+ "), (" + result.getString("phone_number") + ")]\n");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updateVehicle(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your License Number (String): ");
		String license_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW License Number (String): ");
		String license_number_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Color(String): ");
		String color = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Car Color(String): ");
		String color_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Model (String): ");
		String model = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Car Model (String): ");
		String model_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturer year (Date in yyyy-mm-dd format): ");
		String manufacture_year = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Manufacturer year (Date in yyyy-mm-dd format): ");
		String manufacture_year_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter 1 if you are Handicapped (Integer): ");
		int is_handicapped;
		String int_input = sc.nextLine();
		if (int_input.isEmpty()) {
			is_handicapped = 0;
		} else {
			is_handicapped = Integer.parseInt(int_input);
		}

		SQLHelper.skipper();
		System.out.print("Update your handicapped status, Enter 1 if you are Handicapped (Integer): ");
		int is_handicapped_new;
		String int_input_new = sc.nextLine();
		if (int_input_new.isEmpty()) {
			is_handicapped_new = 0;
		} else {
			is_handicapped_new = Integer.parseInt(int_input_new);
		}

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturing Company (String): ");
		String manufacturer = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Manufacturing Company (String): ");
		String manufacturer_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW University Id (String): ");
		String univ_id_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your NEW Phone Number (String): ");
		String phone_number_new = sc.nextLine();

		Vehicle vehicle = new Vehicle(license_number, color, model, manufacture_year, is_handicapped, manufacturer,
				univ_id, phone_number);
		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (license_number.length() + color.length() + model.length() + manufacture_year.length() + is_handicapped
				+ manufacturer.length() + univ_id.length() + phone_number.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllVehicle(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (color.length() > 0) {
				whereMap.put("color", sqlHelper.singleQuotes(color));
			}
			if (model.length() > 0) {
				whereMap.put("model", sqlHelper.singleQuotes(model));
			}
			if (manufacture_year.length() > 0) {
				whereMap.put("manufacture_year", sqlHelper.singleQuotes(manufacture_year));
			}
			if (manufacturer.length() > 0) {
				whereMap.put("manufacturer", sqlHelper.singleQuotes(manufacturer));
			}
			if (univ_id.length() > 0) {
				whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
			}
			if (phone_number.length() > 0) {
				whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
			}

			queryWhere = sqlHelper.merger(whereMap);

			if (is_handicapped > 0) {
				if (queryWhere.length() > 0) {
					queryWhere += "AND is_handicapped=" + is_handicapped;
				} else {
					queryWhere += "is_handicapped=" + is_handicapped;
				}
			}

			HashMap<String, String> setMap = new HashMap<String, String>();
			if (license_number_new.length() > 0) {
				setMap.put("license_number", sqlHelper.singleQuotes(license_number_new));
				if (Vehicle.containsVehicle(statement, license_number_new)) {
					System.out.println("Vehicle is already registered!");
					return;
				}
			}
			if (color_new.length() > 0) {
				setMap.put("color", sqlHelper.singleQuotes(color_new));
			}
			if (model_new.length() > 0) {
				setMap.put("model", sqlHelper.singleQuotes(model_new));
			}
			if (manufacture_year_new.length() > 0) {
				setMap.put("manufacture_year", sqlHelper.singleQuotes(manufacture_year_new));
			}
			if (manufacturer_new.length() > 0) {
				setMap.put("manufacturer", sqlHelper.singleQuotes(manufacturer_new));
			}
			if (univ_id_new.length() > 0) {
				setMap.put("univ_id", sqlHelper.singleQuotes(univ_id_new));
			}
			if (phone_number_new.length() > 0) {
				setMap.put("phone_number", sqlHelper.singleQuotes(phone_number_new));
			}

			if (univ_id_new.length() > 0 && phone_number_new.length() > 0) {
				if (!Vehicle.containsDriver(statement, univ_id_new, phone_number_new)) {
					System.out.println("Driver is not registered, Incorrect University Id or Phone Number!");
					return;
				}
			}

			querySet = sqlHelper.merger(setMap, ", ");

			if (is_handicapped_new > 0) {
				if (querySet.length() > 0) {
					querySet += ", is_handicapped=" + is_handicapped_new;
				} else {
					querySet += "is_handicapped=" + is_handicapped_new;
				}
			}

			vehicle.updateFiltered(statement, queryWhere, querySet);
			;
		}
	}

	public void deleteVehicleByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter your License Number (String): ");
		String license_number = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Color(String): ");
		String color = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Car Model (String): ");
		String model = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturer year (Date in yyyy-mm-dd format): ");
		String manufacture_year = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter 1 if you are Handicapped (Integer): ");
		int is_handicapped;
		String int_input = sc.nextLine();
		if (int_input.isEmpty()) {
			is_handicapped = 0;
		} else {
			is_handicapped = Integer.parseInt(int_input);
		}

		SQLHelper.skipper();
		System.out.print("Enter your Manufacturing Company (String): ");
		String manufacturer = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter University Id (String): ");
		String univ_id = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Phone Number (String): ");
		String phone_number = sc.nextLine();

		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (license_number.length() + color.length() + model.length() + manufacture_year.length() + is_handicapped
				+ manufacturer.length() + univ_id.length() + phone_number.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllVehicle(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (license_number.length() > 0) {
				whereMap.put("license_number", sqlHelper.singleQuotes(license_number));
			}
			if (color.length() > 0) {
				whereMap.put("color", sqlHelper.singleQuotes(color));
			}
			if (model.length() > 0) {
				whereMap.put("model", sqlHelper.singleQuotes(model));
			}
			if (manufacture_year.length() > 0) {
				whereMap.put("manufacture_year", sqlHelper.singleQuotes(manufacture_year));
			}
			if (manufacturer.length() > 0) {
				whereMap.put("manufacturer", sqlHelper.singleQuotes(manufacturer));
			}
			if (univ_id.length() > 0) {
				whereMap.put("univ_id", sqlHelper.singleQuotes(univ_id));
			}
			if (phone_number.length() > 0) {
				whereMap.put("phone_number", sqlHelper.singleQuotes(phone_number));
			}

			query = sqlHelper.merger(whereMap);

			if (is_handicapped > 0) {
				if (query.length() > 0) {
					query += "AND is_handicapped=" + is_handicapped;
				} else {
					query += "is_handicapped=" + is_handicapped;
				}
			}
			Vehicle.deleteFiltered(statement, query);
		}
	}
}
