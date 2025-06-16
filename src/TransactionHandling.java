import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";

        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try {//load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
//establish connection
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully.");
            connection.setAutoCommit(false);
            try {
                //prepared Statements
                PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = connection.prepareStatement(depositQuery);
                withdrawStatement.setDouble(1, 500.00);
                withdrawStatement.setString(2, "a123");
                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2, "a546");
                int rowsAffectedWithdrawl = withdrawStatement.executeUpdate();
                int rowsAffectedDeposite = depositStatement.executeUpdate();
                if (rowsAffectedDeposite > 0 && rowsAffectedWithdrawl > 0) {//transaction handling
                    connection.commit();
                    System.out.println("transaction successful. ");
                } else {
                    connection.rollback();
                    System.out.println("transaction failed. ");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
