package com.tanay;

import java.sql.*;


public class Main {

    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/tgandhi";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;


    public static void main(String[] args) throws SQLException {
        initialize();
        
        // Parking lot Queries
//        ParkingLotDAO parkingLotDAO = new ParkingLotDAO(); 
//        parkingLotDAO.createParkingLot(statement, connection);
//        parkingLotDAO.viewParkingLotByFilters(statement);
//        parkingLotDAO.updateParkingLot(statement);
//        parkingLotDAO.viewAllParkingLot(statement);
//        parkingLotDAO.deleteParkingLotByFilters(statement);
//        parkingLotDAO.viewAllParkingLot(statement);
//        parkingLotDAO.menuParkingLot(statement, connection);
        
        // Zone Queries
//        ZoneDAO zoneDAO = new ZoneDAO();
//        zoneDAO.createZone(statement, connection);
//        zoneDAO.insertZone(statement);
//        zoneDAO.viewAllZone(statement);
//        zoneDAO.viewZoneByFilters(statement);
//        zoneDAO.updateParkingLot(statement);
//        zoneDAO.deleteZoneByFilters(statement);
//        zoneDAO.menuZone(statement, connection);

        // Category
//        CategoryDAO categoryDAO = new CategoryDAO();
//        categoryDAO.menuCategory(statement, connection);
        
        //Citation
        CitationDAO citationDAO = new CitationDAO();
        citationDAO.menuCategory(statement, connection);
        
     // Driver Queries
//    	DriverDAO driverDAO = new DriverDAO();
//    		driverDAO.createDriver(statement);
//    		driverDAO.viewAllDriver(statement);
//    		driverDAO.viewDriverByFilters(statement);
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

    public static void close() {
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
