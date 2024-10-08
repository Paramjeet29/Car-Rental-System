package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection(String connString) {
        Connection con = null;
        
        if (connString == null || connString.trim().isEmpty()) {
            System.err.println("Error: Connection string is null or empty");
            return null;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully");
            
            con = DriverManager.getConnection(connString);
            System.out.println("Connected to the database successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Unable to connect to the database");
            e.printStackTrace();
        }
        
        return con;
    }
}