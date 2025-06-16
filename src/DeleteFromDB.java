import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteFromDB {
    public static void main(String[] args) {
// setting database
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";
//        String query = "delete  from employee where id = 4";
        String query1 = "UPDATE employee SET salary = 10000000 WHERE id = 3";
        try {
            //load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        //establish connection with db and perform operations
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established ");
            Statement st = con.createStatement();
//            int rowsAffected = st.executeUpdate(query);
            int rowsAffected = st.executeUpdate(query1);
            if (rowsAffected > 0) {
//                System.out.println("deletion successfull. " + rowsAffected + "row(s) affected.");
                System.out.println("Updation successfull. " + rowsAffected + "row(s) affected.");
            } else {
//                System.out.println("deletion failed.");
                System.out.println("Updation failed.");
            }

            st.close();
            con.close();
            System.out.println("connection closed successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
