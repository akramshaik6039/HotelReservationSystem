package service;

import dao.Payment;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class PaymentImpliment implements Payment {
    static int paymentId;
    boolean exist = false;
    Random rand = new Random();
    Connection connection= UtilDb.getConnection();
    @Override
    public  int generatePaymentId() {
        do{
           paymentId=100000000+rand.nextInt(100000000);
            try {
                PreparedStatement pre=connection.prepareStatement("select * from payment where  paymentId=?");
                pre.setInt(1,paymentId);
                ResultSet rs=pre.executeQuery();
                while(rs.next()){
                    exist=true;
                }
                if(exist==false){
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }while(exist);

       return paymentId;
    }
}
