package util_res;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class UtilDb {
    public static Connection connetion(){
        Properties prop = new Properties();
        Connection con;
        try {
            InputStream input =new FileInputStream("src/main/resources/db.properties");
            prop.load(input);
            String driver=prop.getProperty("db.driver");
            String url=prop.getProperty("db.url");
            String username=prop.getProperty("db.username");
            String password=prop.getProperty("db.password");
            Class.forName(driver);
            con= DriverManager.getConnection(url,username,password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
