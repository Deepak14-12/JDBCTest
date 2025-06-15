import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
 //database url
        String url = "jdbc:mysql://localhost:3306/jdbc";

        //database credentials
        String username = "root";
        String password = "root";

        //Establish the connection

        try(Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to the database");
            System.out.println(connection);

//perform database operations here
        }catch (SQLException e){
            System.out.println("connection failed: "+e.getMessage());
        }

    }
}