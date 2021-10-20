package Beans;

import java.util.List;

public class Customer {


    /**
     * Fields of the Customer Bean class
     */
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;


    /**
     * Constructor without the ID and the Coupon List
     * @param firstName First Name of the customer
     * @param lastName Last Name of the customer
     * @param email Email of the customer (for login)
     * @param password Password of the customer (for login)
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor with all fields
     * @param id id of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param email email of the customer
     * @param password password of the customer
     */
    public Customer(int id, String firstName, String lastName, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor with only id
     * @param id id of the customer by the SQL server
     */
    public Customer(int id) {
        this.id = id;
    }

    /**
     * String builder toString() method because he in not immutable (doesn't stay in memory)
     * we don't want to BOMB the memory!
     * @return the text written in the StringBuilder
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Customer name: " + lastName + " " + firstName + "\n");
        sb.append("The mail of the customer: " + email + "\n");
        sb.append("The password of the customer is: " + password + "\n");

        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
