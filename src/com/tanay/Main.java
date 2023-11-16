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
        // ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
        // parkingLotDAO.createParkingLot(statement, connection);
        // parkingLotDAO.viewParkingLotByFilters(statement);
        // parkingLotDAO.updateParkingLot(statement);
        // parkingLotDAO.viewAllParkingLot(statement);
        // parkingLotDAO.deleteParkingLotByFilters(statement);
        // parkingLotDAO.viewAllParkingLot(statement);
        // parkingLotDAO.menuParkingLot(statement, connection);

        // Zone Queries
//         ZoneDAO zoneDAO = new ZoneDAO();
        // zoneDAO.createZone(statement, connection);
        // zoneDAO.insertZone(statement);
        // zoneDAO.viewAllZone(statement);
        // zoneDAO.viewZoneByFilters(statement);
        // zoneDAO.updateParkingLot(statement);
        // zoneDAO.deleteZoneByFilters(statement);
//         zoneDAO.menuZone(statement, connection);

        // Space Queries
        // SpaceDAO spaceDAO = new SpaceDAO();
        // spaceDAO.menuSpace(statement, connection);
        
        // Associated With
        AssociatedWithDAO associatedWithDAO = new AssociatedWithDAO();
        associatedWithDAO.menuSpace(statement, connection);

//        CitationDAO c = new CitationDAO();
//        c.menuCategory(statement, connection);

        // ChecksDAO checks = new ChecksDAO();
        // checks.menuCategory(statement, connection);

        // Category
        // CategoryDAO categoryDAO = new CategoryDAO();
        // categoryDAO.menuCategory(statement, connection);

        // Driver Queries
        // DriverDAO driverDAO = new DriverDAO();
        // driverDAO.createDriver(statement, connection);
        // driverDAO.insertDriver(statement);
        // driverDAO.viewAllDriver(statement);
        // driverDAO.viewDriverByFilters(statement);
        // driverDAO.updateDriver(statement);
        // driverDAO.deleteDriverByFilters(statement);
        // driverDAO.menuDriver(statement, connection);

        // Vehicle Queries
        // VehicleDAO vehicleDAO = new VehicleDAO();
        // vehicleDAO.createVehicle(statement, connection);
        // vehicleDAO.insertVehicle(statement);
        // vehicleDAO.viewAllVehicle(statement);
        // vehicleDAO.viewVehicleByFilters(statement);
        // vehicleDAO.updateVehicle(statement);
        // vehicleDAO.deleteVehicleByFilters(statement);
        // vehicleDAO.menuVehicle(statement, connection);

        // Permit Queries
        // PermitDAO permitDAO = new PermitDAO();
        // permitDAO.createPermit(statement, connection);
        // permitDAO.insertPermit(statement);
        // permitDAO.viewAllPermit(statement);
        // permitDAO.viewPermitByFilters(statement);
        // permitDAO.updatePermit(statement);
        // permitDAO.deletePermitByFilters(statement);
        // permitDAO.menuPermit(statement, connection);

        // HasPermit Queries
        // HasPermitDAO hasPermitDAO = new HasPermitDAO();
        // hasPermitDAO.createHasPermit(statement, connection);
        // hasPermitDAO.insertHasPermit(statement);
        // hasPermitDAO.viewAllHasPermit(statement);
        // hasPermitDAO.viewHasPermitByFilters(statement);
        // hasPermitDAO.updateHasPermit(statement);
        // hasPermitDAO.deleteHasPermitByFilters(statement);
        // hasPermitDAO.menuHasPermit(statement, connection);

        close();
    }

    private static void initialize() {
        try {
            connectToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unable to connect to the database\n" + e.getMessage());
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
