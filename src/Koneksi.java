import java.sql.*;

public class Koneksi {

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_bioskop", "root", "");
    }
}
