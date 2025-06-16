package hotelservice;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;


public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {//load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            //establish connection
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            while (true) {
                System.out.println();
                System.out.println("Hotel Reservation System");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. New Reservation");
                System.out.println("2. Check Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. delete Reservation");
                System.out.println("6. Exit");
                System.out.println("Enter Choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(scanner, statement);
                        break;
                    case 2:
                        viewReservation(statement);
                        break;
                    case 3:
                        getRoomNumber(scanner, statement);
                        break;
                    case 4:
                        updateReservation(connection, scanner, statement);
                        break;
                    case 5:
                        deleteReservation(connection, scanner, statement);
                        break;
                    case 6:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("invalid choice. try again");
                }


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        System.out.println("Thank You For Using Hotel Reservation System.");
    }


    private static void reserveRoom(Scanner scanner, Statement statement) {
        try {
            System.out.println("Enter Room Number");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter Contact Number");
            int contactNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Guest Name: ");
            String guestName = scanner.nextLine();


            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number)" +
                    "values('" + guestName + "'," + roomNumber + ",'" + contactNumber + "')";
            try {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0)
                    System.out.println("Reservation successful.");
                else
                    System.out.println("Reservation failed!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewReservation(Statement statement) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations;";
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("+----------------------------------------------------------------------------+");
            System.out.printf("| %-15s | %-20s | %-12s | %-15s | %-12s |\n",
                    "Reservation ID", "Guest Name", "Room Number", "Contact Number", "Date");
            System.out.println("+----------------------------------------------------------------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                int contactNumber = resultSet.getInt("contact_number");
                String reservationDate = resultSet.getString("reservation_date");

                System.out.printf("| %-15d | %-20s | %-12d | %-15d | %-12s |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+----------------------------------------------------------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void getRoomNumber(Scanner scanner, Statement statement) {
        try {
            System.out.println("Enter Reservation Id: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            String sql = " SELECT room_number FROM reservations" +
                    " WHERE reservation_id = " + reservationId +

                    " AND guest_name = '" + guestName + "'";
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation ID " + reservationId + " and Guest " + guestName + " is " + roomNumber);
                } else
                    System.out.println("Reservation not found for the given ID and Guest name.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateReservation(Connection connection, Scanner scanner, Statement statement) {
        try {
            System.out.println("Enter reservation Id to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            if (!reservationExists(reservationId, statement)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            System.out.println("Enter New Guest Name: ");
            String newGuestName = scanner.nextLine();
            scanner.nextLine();
            System.out.println("Enter New Room Number");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter New Contact Number");
            int newContactNumber = scanner.nextInt();

            String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "'," +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;
            try {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0)
                    System.out.println("Reservation updated successfully.");
                else
                    System.out.println("Reservation updated failed.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner, Statement statement) {
        System.out.println("Enter Reservation ID: ");
        int reservationID = scanner.nextInt();

        if (!reservationExists(reservationID, statement)) {
            System.out.println("Reservation not found for the given ID.");
            return;
        }
        String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationID;

        try {
            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0)
                System.out.println("Reservation deleted successfully.");
            else
                System.out.println("Reservation deletion failed. ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean reservationExists(int reservationId, Statement statement) {
        try {
            String sql = "SELECT reservation_id FROM reservations Where reservation_id = " + reservationId;

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next(); //if there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; //handle database errors as needed
        }

    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(400);
            i--;
        }

    }

}
