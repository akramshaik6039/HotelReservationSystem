package entity;

public class Admin extends Person {
    public Admin(){}

    public Admin(String name, long phoneNumber, String email, String address, String password, Role role) {
        super(name,phoneNumber,email,address,password,role);
    }


}
