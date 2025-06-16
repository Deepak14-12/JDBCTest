import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class PreparedStatementsJDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";

        String query = "SELECT * FROM EMPLOYEE WHERE NAME = ? AND JOB = ?";
        String query1 = "INSERT INTO employee(id,name,job,salary) VALUES(?,?,?,?)";
        try {
            //load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {//establish connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully.");

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Name: ");
            String name = sc.nextLine();
            System.out.println("Enter Job: ");
            String job = sc.nextLine();
            //Statement statement = connection.createStatement();

            //prepared statements 1 (retrieval of data)
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            /*preparedStatement.setString(1, "deepak");
              preparedStatement.setString(2, "intern");
            */
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, job);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                 name = resultSet.getString("name");
                 job = resultSet.getString("job");
                double salary = resultSet.getDouble("salary");
                System.out.println("ID: " + id);
                System.out.println("NAME: " + name);
                System.out.println("JOB: " + job);
                System.out.println("SALARY: " + salary);
            }

            System.out.println("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Name: ");
            name = sc.nextLine();
            System.out.println("Enter Job: ");
            job = sc.nextLine();
            System.out.println("Enter Salary: ");
            double salary = sc.nextDouble();
            /*
            //prepared statements 2 (insertion of data)
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "rohit");
            preparedStatement.setString(3, "farming");
            preparedStatement.setDouble(4, 45265.25);
            */
            //prepared statements 2 (insertion of data)
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, job);
            preparedStatement.setDouble(4, salary);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, 4);
            preparedStatement.setString(2, "rohit");
            preparedStatement.setString(3, "farming");
            preparedStatement.setDouble(4, 45265.25);

            if (rowsAffected > 0)
                System.out.println("data inserted successfully.");
            else
                System.out.println("data insertion failed");


            connection.close();
            resultSet.close();
            preparedStatement.close();

            System.out.println();
            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
