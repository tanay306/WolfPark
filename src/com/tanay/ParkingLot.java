package com.tanay;

import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLot {

    String lot_name;
    String address;
    
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
        String query = "Insert into parkinglot Values ('" + this.lot_name + "','" + this.address + "');";
        try {
            statement.executeUpdate(query);
            System.out.println("Parking Lot Query Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
