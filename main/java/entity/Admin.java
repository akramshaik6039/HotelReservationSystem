package entity;

import dao.ManagePerson;

public class Admin extends Person  {

    public Admin(){}

    public Admin(String name, long phoneNumber, String email, String address, String password, Role role, String admin_role) {
        super(name,phoneNumber,email,address,password,role,admin_role);

    }


}
