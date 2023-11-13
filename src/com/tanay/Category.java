package com.tanay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Category {
	
	protected String category;
	protected String fee;
	
	protected Category() {
		
	}
	
	protected Category(String category, String fee) {
		this.category = category;
		this.fee = fee;
	}

    protected String getCategory() {
        return category;
    }

    protected void setCategory(String category) {
        this.category = category;
    }

    protected String getFee() {
        return fee;
    }

    protected void setFee(String fee) {
        this.fee = fee;
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
    
    protected void create(Statement statement) {
    	String query = "CREATE TABLE category_fee ( category VARCHAR(50), fee INT NOT NULL, PRIMARY KEY (category) );";
    	try {
            statement.executeQuery(query);
            System.out.println("Completed: Category Fee Query Create");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void insert(Statement statement) {
    	String query = "INSERT INTO category_fee VALUES ('" + this.category + "','" + Integer.valueOf(this.fee) + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Category Fee Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected ResultSet view(Statement statement) {
        String query = "SELECT * FROM category_fee;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Category Fee Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    protected ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM category_fee WHERE " + queryParams + ";";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Category_Fee Query Select with Where");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    protected void viewUpdateFiltered(Statement statement, String queryParams, String querySet) {
    	String query = "UPDATE category_fee SET " + querySet + " WHERE " + queryParams + ";";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Category Fee Query Update");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected void deleteFiltered(Statement statement, String queryParams) {
        String query = "DELETE FROM category_fee WHERE " + queryParams + ";";
        try {
            statement.executeQuery(query);
            System.out.println("Completed: Category Fee Query Delete");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            Main.close();
        }
    }
    
}
