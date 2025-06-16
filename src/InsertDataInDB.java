import java.sql.*;

public class InsertDataInDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";
        String query = "insert into employee values(4,'rony','developer',56000.0);";

        try {
            //load drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
         //establish connection with db
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established ");
            Statement st = con.createStatement();
            int rowsAffected = st.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("insert successfull. " + rowsAffected + "row(s) affected.");
            } else {
                System.out.println("insertion failed.");
            }

            st.close();
            con.close();
            System.out.println("connection closed successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
