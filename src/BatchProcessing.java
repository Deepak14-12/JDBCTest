import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";

        try {
            //load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
//establish connection with  db
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established ");
            con.setAutoCommit(false);
          /*
           //using normal statements
            Statement statement  = con.createStatement();
           statement.addBatch("INSERT INTO employee(id,name,job,salary) VALUES(7,'vashu','hr',562210.0)");
           statement.addBatch("INSERT INTO employee(id,name,job,salary) VALUES(8,'dev','designer',600210.0)");
           statement.addBatch("INSERT INTO employee(id,name,job,salary) VALUES(9,'thugesh','web dev',120210.0)");
           int [] batchResult  = statement.executeBatch();
           con.commit();
           System.out.println("batch executed successfully...");
           for (int i : batchResult )
           System.out.println(batchResult);
           */

            //using prepared statements
            String query = "INSERT INTO employee(id,name,job,salary) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("enter id: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("enter name: ");
                String name = scanner.nextLine();
                System.out.println("enter job: ");
                String job = scanner.nextLine();
                System.out.println("enter salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine();

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, job);
                preparedStatement.setDouble(4, salary);

                preparedStatement.addBatch();
                System.out.println("add more values Y/N: ");
                String decision = scanner.nextLine();
                if (decision.equalsIgnoreCase("N"))
                    break;
            }
             preparedStatement.executeBatch();
            con.commit();
            System.out.println("batch executed successfully...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

