package entity;

import java.util.Set;

public enum Role {

        admin,
        user;



        public static Role getRole(String roleInput){
            switch(roleInput){
                case "admin":return admin;
                case "user":return user;
                default:return null;
            }
        }


}
