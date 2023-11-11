package com.tanay;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ParkingLot {

    String lot_name;
    String address;
    
    public ParkingLot() {}
    
    public ParkingLot(String lot_name, String address) {
    	this.lot_name = lot_name;
    	this.address = address;
    }

    public String getLot_name() {
        return lot_name;
    }

    public void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void insert(Statement statement) {
        String query = "INSERT INTO parkinglot VALUES ('" + this.lot_name + "','" + this.address + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Completed: Parking Lot Query Insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet view(Statement statement) {
        String query = "SELECT * FROM parkinglot;";
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Select");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet viewFiltered(Statement statement, String queryParams) {
        String query = "SELECT * FROM parkinglot WHERE " + queryParams + ";";
        System.out.println(query);
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
            System.out.println("Completed: Parking Lot Query Select with Where");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
