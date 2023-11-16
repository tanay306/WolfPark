package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Checks {

    protected String license_number;
    protected String permit_id;
    protected String citation_number;

    protected Checks() {
        
    }

    protected Checks(String license_number, String permit_id, String citation_number) {
        this.license_number = license_number;
        this.permit_id = permit_id;
        this.citation_number = citation_number;
    }

    protected String getLicense_number() {
        return license_number;
    }

    protected void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    protected String getPermit_id() {
        return permit_id;
    }

    protected void setPermit_id(String permit_id) {
        this.permit_id = permit_id;
    }

    protected String getCitation_number() {
        return citation_number;
    }

    protected void setCitation_number(String citation_number) {
        this.citation_number = citation_number;
    }

    protected boolean containsLicenseNumber(Statement statement) {
        ResultSet result = null;
        String query = "Select * from vehicle where license_number = '" + this.license_number + "';";
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

    protected boolean containsPermit(Statement statement) {
        ResultSet result = null;
        String query = "Select * from permit where permit_id = '" + this.permit_id + "';";
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

    protected boolean containsPermit(Statement statement, String permit_id) {
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

    protected boolean containsAll(Statement statement, String license_number, String permit_id, String citation_number) {
        ResultSet result = null;
        String query;
        if (citation_number == "") {
            query = "Select * from checks where license_number = '" + license_number + "' and permit_id = " + permit_id + " and citation_number is null;";
        } else {
            query = "Select * from checks where license_number = '" + license_number + "' and permit_id = " + permit_id + " and citation_number = " + citation_number + ";";
        }
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
    	String query = "CREATE TABLE checks ( license_number VARCHAR(8), citation_number varchar(10), permit_id varchar(10), PRIMARY KEY (license_number, citation_number, permit_id), FOREIGN KEY(license_number) REFERENCES vehicle(license_number) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(citation_number) REFERENCES citation(citation_number) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(permit_id) REFERENCES permit(permit_id) ON UPDATE CASCADE ON DELETE CASCADE );";
    	try {
            statement.executeQuery(query);
            System.out.println("Completed: Checks Table Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void insert(Statement statement) {
    	String query;
    	if (this.citation_number.length() == 0) {
    		query = "INSERT INTO checks VALUES ('" + this.license_number + "',null,'" + this.permit_id + "');";
    	} else if (this.license_number.length() == 0) {
    		query = "INSERT INTO checks VALUES (null,'" + this.citation_number + "','" + this.permit_id + "');";
    	}
    	else {
    		query = "INSERT INTO checks VALUES ('" + this.license_number + "','" + this.permit_id + "','" + this.citation_number + "');";
    	}
    	System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Checks Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected ResultSet view(Statement statement) {
        String query = "SELECT * FROM checks;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Checks Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM checks WHERE " + queryParams + ";";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Checks Query Select with Where");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void viewUpdateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE checks SET " + querySet + " WHERE " + queryParams + ";";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Checks Query Update");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM checks WHERE " + queryParams + ";";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Checks Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
}
