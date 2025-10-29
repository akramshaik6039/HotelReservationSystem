package hotel;

import entity.Person;
import entity.Role;
import org.slf4j.LoggerFactory;
import service.LoginRegister;

import java.util.Scanner;
import java.util.logging.Logger;


public class HotelMain {
    final static Logger logger= Logger.getLogger(HotelMain.class.getName());
       static  org.slf4j.Logger logger1= LoggerFactory.getLogger(HotelMain.class.getName());

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean start = true;
        Person person ;
        LoginRegister loginRegister = new LoginRegister();
        while(start){
            logger1.info("********** HOTEL RESERVATION SYSTEM **********");
            System.out.println("1) Register \n" +
                             "2) Login \n" +
                             "3) Exit");
            int choice=sc.nextInt();
            switch (choice){

                case 1:
                    System.out.println("Please enter your name");
                    String name=sc.next();
                    System.out.println("Please enter your Email");
                String email=sc.next();
                    System.out.println("Please enter your Password");
                String password=sc.next();
                    System.out.println("Please enter your Phone Number");
                long phoneNumber=sc.nextLong();
                    System.out.println("Please enter your Address");
                String address=sc.next();
                    System.out.println("Please choose your Role (admin/user)");
                String roleInput=sc.next();
                Role role=Role.getRole(roleInput);
                String admin_role=null;
                while(role==null){
                    System.out.println("Invalid Role! ,Please choose your Role  agaim (admin/user)");
                    roleInput=sc.next();
                    role=Role.getRole(roleInput);
                }
                if(roleInput.equalsIgnoreCase("admin")){
                    System.out.println("Please enter your Role in Hotel");

                   admin_role =sc.next();
                }
                else if(roleInput.equalsIgnoreCase("user")){
                    admin_role = null;
                }


                loginRegister.register(new Person(name,phoneNumber,email,address,password,role,admin_role));
                    break;
                case 2:
                    System.out.println("Please enter your Email");
                    String email1=sc.next();
                    System.out.println("Please enter your Password");
                    String password1=sc.next();
                    loginRegister.login(email1,password1);
                    break;
                case 3:System.out.println("----- Thank you for using Hotel Reservation System -----");
                    start=false;
                    break;
                    default:logger.info("Invalid choice");

            }

        }
    }
}
