import java.sql.*;
public class JDBCBasic {
    public static void main(String[] args) throws ClassNotFoundException {
try {
    //load drivers
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Drivers loaded successfully");
}catch (ClassNotFoundException e){
    System.out.println(e.getMessage());

}
    }
}
