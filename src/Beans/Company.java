package Beans;

import java.util.List;
import java.util.Objects;

public class Company {


    /**
     * fields of the Company Bean class
     */
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;


    /**
     * Constructor without the ID
     * @param name name of the Company
     * @param email email of the Company (for Login)
     * @param password password of the company (for login)
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * constructor with all fields
     * @param id id of the company
     * @param name name of the company
     * @param email email of the company
     * @param password password of the company
     */
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * constructor with only ID, to get company ID from SQL server
     * @param id id of the company.
     */
    public Company(int id) {
        this.id = id;
    }

    /**
     * Default constructor
     */
    public Company() {};

    /**
     * String builder toString() method because he in not immutable (doesn't stay in memory)
     * we don't want to BOMB the memory!
     * @return the text written in the StringBuilder
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Company Name: " + name + "\n");
        sb.append("The mail of the company: " + email + "\n");
        sb.append("The password of the company is: " + password + "\n");

        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password) && Objects.equals(coupons, company.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, coupons);
    }


}
