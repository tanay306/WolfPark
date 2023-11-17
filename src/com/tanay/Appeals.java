package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Appeals {

    protected String univ_id;
    protected String phone_number;
    protected String citation_number;

    protected Appeals() {

    }

    protected Appeals(String univ_id, String phone_number, String citation_number) {
        this.univ_id = univ_id;
        this.phone_number = phone_number;
        this.citation_number = citation_number;
    }

    protected String getUniv_id() {
        return univ_id;
    }

    protected void setUniv_id(String univ_id) {
        this.univ_id = univ_id;
    }

    protected String getPhone_number() {
        return phone_number;
    }

    protected void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    protected String getCitation_number() {
        return citation_number;
    }

    protected void setCitation_number(String citation_number) {
        this.citation_number = citation_number;
    }

    protected boolean containsAppeals(Statement statement) {
        ResultSet result = null;
        String query = "SELECT * FROM appeals WHERE univ_id = '" + this.univ_id + "' AND phone_number = '"
                + this.phone_number + "' AND citation_number = " + this.citation_number + ";";
        try {
            result = statement.executeQuery(query);
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    protected boolean containsDriver(Statement statement) {
        ResultSet result = null;
        String query = "SELECT * FROM driver WHERE univ_id = '" + this.univ_id + "' AND phone_number = '"
                + this.phone_number + "';";
        try {
            result = statement.executeQuery(query);
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    protected boolean containsDriver(Statement statement, String univ_id, String phone_number) {
        ResultSet result = null;
        String query = "SELECT * FROM driver WHERE univ_id = '" + univ_id + "' AND phone_number = '" + phone_number
                + "';";
        try {
            result = statement.executeQuery(query);
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return true;
    }

    protected void create(Statement statement) {
        String query = "CREATE TABLE appeals ( univ_id CHAR(9), phone_number CHAR(10), citation_number VARCHAR(10), PRIMARY KEY (univ_id, phone_number, citation_number), FOREIGN KEY (univ_id, phone_number) REFERENCES driver (univ_id, phone_number) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (citation_number) REFERENCES citation (citation_number) ON UPDATE CASCADE ON DELETE CASCADE );";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Appeals Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void insert(Statement statement) {
        String query = "INSERT INTO appeals VALUES ('" + this.univ_id + "','" + this.phone_number + "','"
                + this.citation_number + "');";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Appeals Query Insert");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected ResultSet view(Statement statement) {
        String query = "SELECT * FROM appeals;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Appeals Query Select");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM appeals WHERE " + queryParams + ";";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Appeals Query Select with Where");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    protected void viewUpdateFiltered(Statement statement, String queryParams, String querySet) {
        String query = "UPDATE appeals SET " + querySet + " WHERE " + queryParams + ";";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Appeals Query Update");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM appeals WHERE " + queryParams + ";";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Appeals Query Delete");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Main.close();
        }
    }

}
