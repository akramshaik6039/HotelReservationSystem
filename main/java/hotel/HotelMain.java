package hotel;

import java.util.Scanner;
import java.util.logging.Logger;


public class HotelMain {
    final static Logger logger= Logger.getLogger(HotelMain.class.getName());
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean start = true;
        while(start){
            logger.info("********** HOTEL RESERVATION SYSTEM **********");
            logger.info("1) Register \n" +
                             "2) Login \n" +
                             "3) Exit");
            int choice=sc.nextInt();
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                    default:logger.info("Invalid choice");

            }

        }
    }
}
