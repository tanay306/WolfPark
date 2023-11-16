package com.tanay;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/snisar";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        initialize();
        menu();
        close();
    }

    private static void menu() {
        while (true) {
            System.out.print("\nMenu\n"
                    + "1. Driver\n"
                    + "2. Vehicle\n"
                    + "3. Permit\n"
                    + "4. Has Permit\n"
                    + "5. Parking Lot\n"
                    + "6. Zones\n"
                    + "7. Space\n"
                    + "8. Associated With\n"
                    + "9. Category\n"
                    + "10. Citation\n"
                    + "11. Check\n"
                    + "12. Appeal/Pay\n"
                    + "13. Results\n"
                    + "-1. End\n"
                    + "\nSelect one option: ");
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    DriverDAO driverDAO = new DriverDAO();
                    driverDAO.menuDriver(statement, connection);
                    break;
                case "2":
                    VehicleDAO vehicleDAO = new VehicleDAO();
                    vehicleDAO.menuVehicle(statement, connection);
                    break;
                case "3":
                    PermitDAO permitDAO = new PermitDAO();
                    permitDAO.menuPermit(statement, connection);
                    break;
                case "4":
                    HasPermitDAO hasPermitDAO = new HasPermitDAO();
                    hasPermitDAO.menuHasPermit(statement, connection);
                    break;
                case "5":
                    ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
                    parkingLotDAO.menuParkingLot(statement, connection);
                    break;
                case "6":
                    ZoneDAO zoneDAO = new ZoneDAO();
                    zoneDAO.menuZone(statement, connection);
                    break;
                case "7":
                    SpaceDAO spaceDAO = new SpaceDAO();
                    spaceDAO.menuSpace(statement, connection);
                    break;
                case "8":
                    AssociatedWithDAO associatedWithDAO = new AssociatedWithDAO();
                    associatedWithDAO.menuAssociatedWith(statement, connection);
                    break;
                case "9":
                    CategoryDAO categoryDAO = new CategoryDAO();
                    categoryDAO.menuCategory(statement, connection);
                    break;    
                case "10":
                    CitationDAO citationDAO = new CitationDAO();
                    citationDAO.menuCategory(statement, connection);
                    break;
                case "11":
                    ChecksDAO checksDAO = new ChecksDAO();
                    checksDAO.menuCategory(statement, connection);
                    break;
                case "12":
                    AppealsDAO appealsDAO = new AppealsDAO();
                    appealsDAO.menuCategory(statement, connection);
                    break;
                case "13":
                	Reports report = new Reports();
                	report.menuCategory(statement, connection);
                	break;
                case "-1":
                    return;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    private static void initialize() {
        try {
            connectToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unable to connect to the database\n" + e.getMessage());
        }
        System.out.println("Connected to database!");
    }

    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");

//        String user = "tgandhi";
//        String password = "200533319";
        String user = "snisar";
        String password = "dr.rada_dbms";

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
        System.out.println("Disconnected from database!");
    }
}
