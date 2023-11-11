package com.tanay;

import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLot {

    int lot_name;
    int address;

    public int getLot_name() {
        return lot_name;
    }

    public void setLot_name(int lot_name) {
        this.lot_name = lot_name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void insert(Statement statement, String lot_name, String address) {
        String query = "Insert into parkinglot Values ('" + lot_name + "','" + address + "');";
        System.out.println(query);
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
