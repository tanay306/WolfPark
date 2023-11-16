package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Citation {

		protected String citation_number;
		protected String citation_date;
		protected String citation_time;
		protected String category;
		protected String payment_status;
		protected String lot_name;
		protected String zone_id;
		protected String space_number;
		protected String license_number;
		protected String permit_id;

		protected Citation() {

		}

		protected Citation(String citation_number, String citation_date, String citation_time, String category, String payment_status, String lot_name, String zone_id, String space_number) {
			this.citation_number = citation_number;
			this.citation_date = citation_date;
			this.citation_time = citation_time;
			this.category = category;
			this.payment_status = payment_status;
			this.lot_name = lot_name;
			this.zone_id = zone_id;
			this.space_number = space_number;
		}

		protected String getCitation_number() {
			return citation_number;
		}

		protected void setCitation_number(String citation_number) {
			this.citation_number = citation_number;
		}

		protected String getCitation_date() {
			return citation_date;
		}

		protected void setCitation_date(String citation_date) {
			this.citation_date = citation_date;
		}

		protected String getCitation_time() {
			return citation_time;
		}

		protected void setCitation_time(String citation_time) {
			this.citation_time = citation_time;
		}

		protected String getCategory() {
			return category;
		}

		protected void setCategory(String category) {
			this.category = category;
		}

		protected String isPayment_status() {
			return payment_status;
		}

		protected void setPayment_status(String payment_status) {
			this.payment_status = payment_status;
		}

		protected String getLot_name() {
			return lot_name;
		}

		protected void setLot_name(String lot_name) {
			this.lot_name = lot_name;
		}

		protected String getZone_id() {
			return zone_id;
		}

		protected void setZone_id(String zone_id) {
			this.zone_id = zone_id;
		}

		protected String getSpace_number() {
			return space_number;
		}

		protected void setSpace_number(String space_number) {
			this.space_number = space_number;
		}

		protected boolean containsCitation(Statement statement) {
			ResultSet result = null;
			String query = "Select * from citation where citation_number = '" + this.citation_number + "';";
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

		protected boolean containsCitation(Statement statement, String citation_number) {
			ResultSet result = null;
			String query = "Select * from citation where citation_number = '" + citation_number + "';";
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

		protected boolean containsCategory(Statement statement) {
			ResultSet result = null;
			String query = "Select * from category_fee where category = '" + this.category + "';";
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

		protected boolean containsCategory(Statement statement, String category) {
			ResultSet result = null;
			String query = "Select * from category_fee where category = '" + category + "';";
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

		protected boolean containsLot(Statement statement) {
			ResultSet result = null;
			String query = "Select * from parkinglot where lot_name = '" + this.lot_name + "';";
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

		protected boolean containsLot(Statement statement, String lot_name) {
			ResultSet result = null;
			String query = "Select * from parkinglot where lot_name = '" + lot_name + "';";
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

		protected boolean containsZone(Statement statement) {
			ResultSet result = null;
			String query = "Select * from zone where zone_id = '" + this.zone_id + "';";
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

		protected boolean containsZone(Statement statement, String zone_id) {
			ResultSet result = null;
			String query = "Select * from zone where zone_id = '" + zone_id + "';";
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
			String query = "Select * from space where space_number = '" + this.space_number + "';";
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

		protected boolean containsSpace(Statement statement, String space_number) {
			ResultSet result = null;
			String query = "Select * from space where space_number = " + space_number + ";";
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
		
		protected boolean containsAll(Statement statement, String lot_name, String zone_id, String space_number) {
			ResultSet result = null;
			String query = "Select * from space where lot_name = '" + lot_name + "'and zone_id = '" + zone_id + "' and space_number = " + space_number + " and  availability_slot = 1;";
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

		protected boolean containsLicenseNumber(Statement statement, String license_number) {
			ResultSet result = null;
			String query = "Select * from vehicle where license_number = '" + license_number + "';";
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

		protected boolean containsPermitId(Statement statement, String permit_id) {
			ResultSet result = null;
			String query = "Select * from permit where permit_id = '" + permit_id + "';";
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

		protected void create(Statement statement) {
			String query = "CREATE TABLE citation ( citation_number INT, citation_date DATE NOT NULL, citation_time TIME NOT NULL, category VARCHAR(50) NOT NULL, payment_status BOOL NOT NULL DEFAULT false, lot_name VARCHAR(50), zone_id VARCHAR(2), space_number INT, FOREIGN KEY(category) REFERENCES category_fee(category) ON UPDATE CASCADE, PRIMARY KEY (citation_number) );";
			try {
				statement.executeQuery(query);
				System.out.println("Completed: Citation Query Create");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		protected void insert(Statement statement) {
			String query = "INSERT INTO citation VALUES ('" + this.citation_number + "','" + this.citation_date + "','" + this.citation_time + "','" + this.category + "','" + this.payment_status + "','" + this.lot_name + "','" + this.zone_id + "','" + this.space_number + "');";
			try {
				statement.executeUpdate(query);
				System.out.println("Completed: Citation Query Insert");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		protected ResultSet view(Statement statement) {
			String query = "SELECT * FROM citation;";
			ResultSet result = null;
			try {
				result = statement.executeQuery(query);
				System.out.println("Completed: Citation Query Select");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		protected ResultSet viewFiltered(Statement statement, String queryParams) {
			String query = "SELECT * FROM citation WHERE " + queryParams + ";";
			ResultSet result = null;
			try {
				result = statement.executeQuery(query);
				System.out.println("Completed: Citation Query Select with Where");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		protected void viewUpdateFiltered(Statement statement, String queryParams, String querySet) {
			String query = "UPDATE citation SET " + querySet + " WHERE " + queryParams + ";";
			try {
				statement.executeUpdate(query);
				System.out.println("Completed: Citation Query Update");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		protected void deleteFiltered(Statement statement, String queryParams) {
			String query = "DELETE FROM citation WHERE " + queryParams + ";";
			try {
				statement.executeQuery(query);
				System.out.println("Completed: Citation Query Delete");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				Main.close();
			}
		}

}
