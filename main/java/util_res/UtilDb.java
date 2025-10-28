package util_res;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class UtilDb {
    private static Connection con = null;
    UtilDb() {}
    public static Connection getConnection() {
        try {
            if (con == null) {
                Properties prop = new Properties();
                InputStream input = new FileInputStream("src/main/resources/db.properties");
                prop.load(input);

                String driver = prop.getProperty("db.driver");
                String url = prop.getProperty("db.url");
                String username = prop.getProperty("db.username");
                String password = prop.getProperty("db.password");

                Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
