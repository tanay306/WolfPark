package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AssociatedWith {
	private String permit_id;
	private String space_number;
	private String zone_id;
	private String lot_name;

	AssociatedWith() {
	}

	AssociatedWith(String permit_id, String space_number, String zone_id, String lot_name) {
		this.permit_id = permit_id;
		this.space_number = space_number;
		this.zone_id = zone_id;
		this.lot_name = lot_name;
	}

	protected boolean containsPermit(Statement statement) {
		ResultSet result = null;
		String query = "SELECT * FROM permit WHERE permit_id = '" + this.permit_id + "';";
		System.out.println(query);
		try {
			result = statement.executeQuery(query);
			if (result.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	protected boolean containsSpace(Statement statement) {
		ResultSet result = null;
		String query = "";
		if (this.space_number.length() > 0) {
			query = "SELECT * FROM space WHERE lot_name = '" + this.lot_name + "' AND zone_id = '" + this.zone_id
					+ "' AND space_number = '" + this.space_number + "';";
		} else {
			query = "SELECT * FROM space WHERE lot_name = '" + this.lot_name + "' AND zone_id = '" + this.zone_id
					+ "';";
		}
		System.out.println(query);
		try {
			result = statement.executeQuery(query);
			if (result.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	protected boolean containsPermitInAssociatedWith(Statement statement) {
		ResultSet result = null;
		String query = "SELECT * FROM associatedwith WHERE permit_id = '" + this.permit_id + "';";
		System.out.println(query);
		try {
			result = statement.executeQuery(query);
			if (result.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	protected static void create(Statement statement) {
		String query = "CREATE TABLE associatedwith"
				+ "("
				+ " permit_id INT UNIQUE,"
				+ " space_number INT,"
				+ " zone_id VARCHAR (2),"
				+ " lot_name VARCHAR (50),"
				+ " PRIMARY KEY (permit_id, space_number, zone_id, lot_name),"
				+ " FOREIGN KEY(lot_name, zone_id, space_number) REFERENCES space(lot_name, zone_id, space_number) ON UPDATE CASCADE ON DELETE CASCADE,"
				+ " FOREIGN KEY(permit_id) REFERENCES permit(permit_id) ON UPDATE CASCADE ON DELETE CASCADE"
				+ ");";
		try {
			statement.executeQuery(query);
			System.out.println("Completed: AssociatedWith Query Create");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Main.close();
		}
	}

	protected void insert(Statement statement) {
		String query = "INSERT INTO associatedwith VALUES ('" + this.permit_id + "','" + this.space_number + "','"
				+ this.zone_id + "','" + this.lot_name + "');";
		System.out.println(query);
		try {
			statement.executeUpdate(query);
			System.out.println("Completed: AssociatedWith Query Insert");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Main.close();
		}
	}

	protected static ResultSet view(Statement statement) {
		String query = "SELECT * FROM associatedwith;";
		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
			System.out.println("Completed: associatedwith Query Select");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Main.close();
		}
		return result;
	}

	protected ResultSet viewFiltered(Statement statement, String queryParams) {
		String query = "SELECT * FROM associatedwith WHERE " + queryParams + ";";
		ResultSet result = null;
		System.out.println(query);
		try {
			result = statement.executeQuery(query);
			System.out.println("Completed: Associatedwith Query Select with Where");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Main.close();
		}
		return result;
	}

	protected void updateFiltered(Statement statement, String queryParams, String querySet) {
		String query = "UPDATE associatedwith SET " + querySet + " WHERE " + queryParams + ";";
		System.out.println(query);
		try {
			statement.executeUpdate(query);
			System.out.println("Completed: Associatedwith Query Update");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n\nChanges trashed, try again!");
			Main.close();
		}
	}

	protected static void deleteFiltered(Statement statement, String queryParams) {
		String query = "DELETE FROM associatedwith WHERE " + queryParams + ";";
		System.out.println(query);
		try {
			statement.executeQuery(query);
			System.out.println("Completed: Associatedwith Query Delete");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Main.close();
		}
	}
}
