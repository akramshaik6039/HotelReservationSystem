package dao;

import entity.Person;

public interface ManagePerson {
    boolean viewDetails(String email,String role);
    void updateDetails(String email,String role,String name,String address,long phoneNumber,String admin_role);
    boolean deletePerson(String email);
    void addBalance(String email,double amount);
    void viewUserBookings(int Userid);
}
