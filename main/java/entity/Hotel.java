package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class Hotel {
    static Logger logger = LoggerFactory.getLogger(Hotel.class);
    Connection con = UtilDb.getConnection();
    public void register(Person person) {
        try{
            if(person.getRole().equals("admin")){

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void login(Person person) {

    }
}
