package service;

import entity.Person;
import entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginRegister {
    static Logger logger = LoggerFactory.getLogger(LoginRegister.class);
    Connection con = UtilDb.getConnection();
    public void register(Person person) {

        try{
            if(person.getRole().toString().equals("admin")){
                Role role=person.getRole();

                //name,phoneNumber,email,role,admin_role,address,password
                PreparedStatement pre=con.prepareStatement("insert into persons (name,phoneNumber,email,role,admin_role,address,password) values(?,?,?,?,?,?,?)");
                pre.setString(1, person.getName());
                pre.setLong(2, person.getPhoneNumber());
                pre.setString(3, person.getEmail());
                pre.setString(4,person.getRole().toString());
                pre.setString(5,person.getAdmin_role());
                pre.setString(6,person.getAddress());
                pre.setString(7,person.getPassword());
               int res=pre.executeUpdate();
                if(res>0){
                    System.out.println("Register successfully!");
                }
                else{
                    System.out.println("Register failed!");
                }
            }
            else if(person.getRole().toString().equals("user")){
                Role role=person.getRole();
                PreparedStatement pre=con.prepareStatement("insert into persons (name,phoneNumber,email,role,address,password) values(?,?,?,?,?,?)");
                pre.setString(1, person.getName());
                pre.setLong(2, person.getPhoneNumber());
                pre.setString(3, person.getEmail());
                pre.setString(4,person.getRole().toString());
                pre.setString(5,person.getAddress());
                pre.setString(6,person.getPassword());
                int res=pre.executeUpdate();
                if(res>0){
                    System.out.println("Register successfully!");
                }else{
                    System.out.println("Register failed!");
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void login(String email,String password) {
            try{
                PreparedStatement pre=con.prepareStatement("select * from persons where email=? and password=?");
                pre.setString(1, email);
                pre.setString(2, password);
                ResultSet rs=pre.executeQuery();
                if(rs.next()){

                    String role=rs.getString("role");
                   System.out.println(role);
                    if(role.equalsIgnoreCase("admin")){
                        System.out.println("Login successfully! Welcome to Admin DashBoard "+rs.getString("name"));
                    }
                    else if(role.equals("user")){
                        System.out.println("Login successfully! Welcome to User DashBoard "+rs.getString("name"));
                    }
                }
                else{
                    System.out.println("Login failed!,Give Correct Credentials...");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
