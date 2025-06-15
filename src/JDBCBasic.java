import java.sql.*;
public class JDBCBasic {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";

try {
    //load drivers
    Class.forName("com.mysql.cj.jdbc.Driver");
    System.out.println("Drivers loaded successfully");
}catch (ClassNotFoundException e){
    System.out.println(e.getMessage());
}
//establish connection with db
try{
     Connection con = DriverManager.getConnection(url,username,password);
     System.out.println("connection established ");
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery("select *  from employee;");

        while(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String job = rs.getString("job");
            double salary = rs.getDouble("salary");
            System.out.println();
            System.out.println("-------------------------");
            System.out.println("ID = "+id);
            System.out.println("Name: "+name);
            System.out.println("job: " +job);
            System.out.println("Salary: " +salary);
        }
        rs.close();
        st.close();
        con.close();
        System.out.println("connection closed successfully");
}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }
}
