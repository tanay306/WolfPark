package com.tanay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDAO {

	Scanner sc = new Scanner(System.in);

	public void menuCategory(Statement statement, Connection connection) {
		System.out.print("\nCategory Fee Sub-Menu\n"
				+ "a. Create Category Fee\n"
				+ "b. Insert Category Fee\n"
				+ "c. View All Category Fee\n"
				+ "d. View Specific Category Fee\n"
				+ "e. Update Category Fee\n"
				+ "f. Delete Category Fee\n"
				+ "Select one option: ");

		CategoryDAO categoryDAO = new CategoryDAO();
		String input = sc.nextLine();
		switch (input) {
			case "a":
				try {
					categoryDAO.createCategory(statement, connection);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					Main.close();
				}
				break;
			case "b":
				categoryDAO.insertCategory(statement);
				break;
			case "c":
				categoryDAO.viewAllCategory(statement);
				break;
			case "d":
				categoryDAO.viewCategoryByFilters(statement);
				break;
			case "e":
				categoryDAO.updateCategory(statement);
				break;
			case "f":
				categoryDAO.deleteCategoryByFilters(statement);
				break;
			default:
				System.out.println("Invalid Entry");
		}
	}

	public void createCategory(Statement statement, Connection connection) throws SQLException {
		if (SQLHelper.tableExists(connection, "category_fee")) {
			System.out.println("Table Already Exists");
		} else {
			Category category = new Category();
			category.create(statement);
		}
	}

	public void insertCategory(Statement statement) {
		System.out.print("Enter Category (String): ");
		String category = sc.nextLine();
		System.out.print("Enter Fee (Int): ");
		String fee = sc.nextLine();

		Category category_ = new Category(category, fee);
		if (category_.containsCategory(statement)) {
			System.out.println("Category already present!");
			return;
		}
		category_.insert(statement);
	}

	public void viewAllCategory(Statement statement) {
		Category category_ = new Category();
		ResultSet result = category_.view(statement);

		try {
			while (result.next()) {
				System.out.println("[(" + result.getString("category") + "), (" + result.getString("fee") + ")]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void viewCategoryByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Category (String): ");
		String category = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Fee (Int): ");
		String fee = sc.nextLine();

		Category category_ = new Category(category, fee);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (category.length() + fee.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllCategory(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (category.length() > 0) {
				whereMap.put("category", sqlHelper.singleQuotes(category));
			}
			if (String.valueOf(fee).length() > 0) {
				whereMap.put("fee", sqlHelper.singleQuotes(fee));
			}
			query = sqlHelper.merger(whereMap);
			ResultSet result = category_.viewFiltered(statement, query);

			try {
				if (result.next()) {
					do {
						System.out
								.println("[(" + result.getString("category") + "), (" + result.getString("fee") + ")]");
					} while (result.next());
				} else {
					System.out.println("*** No Rows returned ***");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updateCategory(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Category (String): ");
		String category = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Category (String): ");
		String category_new = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Fee (Int): ");
		String fee = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter NEW Fee (Int): ");
		String fee_new = sc.nextLine();

		Category category_ = new Category(category, fee);
		if (category_new.length() > 0 && category_.containsCategory(statement, category_new)) {
			System.out.println("Category already present!");
			return;
		}
		String queryWhere = "";
		String querySet = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (category.length() + fee.length() == 0 || category_new.length() + fee_new.length() == 0) {
			System.out.println("No Filters provided, cannot update a row");
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (category.length() > 0) {
				whereMap.put("category", sqlHelper.singleQuotes(category));
			}
			if (fee.length() > 0) {
				whereMap.put("fee", fee);
			}
			queryWhere = sqlHelper.merger(whereMap);

			HashMap<String, String> setMap = new HashMap<String, String>();
			if (category_new.length() > 0) {
				setMap.put("category", sqlHelper.singleQuotes(category_new));
			}
			if (fee_new.length() > 0) {
				setMap.put("fee", sqlHelper.singleQuotes(fee_new));
			}
			querySet = sqlHelper.mergerUpdate(setMap);

			category_.viewUpdateFiltered(statement, queryWhere, querySet);
		}
	}

	public void deleteCategoryByFilters(Statement statement) {
		SQLHelper.skipper();
		System.out.print("Enter Category (String): ");
		String category = sc.nextLine();

		SQLHelper.skipper();
		System.out.print("Enter Fee (Int): ");
		String fee = sc.nextLine();

		Category category_ = new Category(category, fee);
		String query = "";

		SQLHelper sqlHelper = new SQLHelper();
		if (category.length() + fee.length() == 0) {
			System.out.println("No Filters provided, showing all rows");
			viewAllCategory(statement);
		} else {
			HashMap<String, String> whereMap = new HashMap<String, String>();
			if (category.length() > 0) {
				whereMap.put("category", sqlHelper.singleQuotes(category));
			}
			if (fee.length() > 0) {
				whereMap.put("fee", sqlHelper.singleQuotes(fee));
			}
			query = sqlHelper.merger(whereMap);
			category_.deleteFiltered(statement, query);
		}
	}

}
