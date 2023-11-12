package com.tanay;

import java.sql.*;


public class Main {

    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/tgandhi";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;


    public static void main(String[] args) throws SQLException {
        initialize();
        ParkingLotDAO parkingLotDAO = new ParkingLotDAO(); 
//        parkingLotDAO.createParkingLot(statement, connection);
//        parkingLotDAO.viewParkingLotByFilters(statement);
        parkingLotDAO.updateParkingLot(statement);
        parkingLotDAO.viewAllParkingLot(statement);
        close();
    }

    private static void initialize() {
        try {
            connectToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");

        String user = "tgandhi";
        String password = "200533319";

        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
    }

    private static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
