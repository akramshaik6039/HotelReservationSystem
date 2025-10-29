package entity;

public class User extends Person {
    User(){}
    User(String name, long phoneNumber, String email, String address, String password, Role role,String role_id) {
        super(name,phoneNumber,email,address,password,role,role_id);
    }
}
