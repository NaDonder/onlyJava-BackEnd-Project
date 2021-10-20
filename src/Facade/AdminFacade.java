package Facade;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.CompanyExistsException;
import Exceptions.CustomerExistsException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    CustomerDBDAO customerDBDAO = new CustomerDBDAO();


    public AdminFacade(){
    }

    /**
     * a method to login as the admin of the Coupons System
     * only for the administrator no need to check the email and password
     * the email always will be "admin@admin.com" and the password always will be "admin"
     * @param email Email for the login (admin@admin.com)
     * @param password Password for the login (admin)
     * @return returns true if we could connect, returns false if we couldn't connect
     */
    @Override
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        } else {
            System.out.println("How the flipping pancakes did you get here?");
            return false;
        }
    }

    /**
     * a method to add the company to the SQL server
     * the method will check if new company with the same name as an existing one exists
     * and will check if new company with the same email as an existing one exists
     * @param company adding new company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CompanyExistsException if the company name exists
     */
    public void addCompany(Company company) throws SQLException, CompanyExistsException {
        System.out.println("Creating new company...");
        if (companiesDBDAO.isCompanyExists(company.getEmail())) {
            throw new CompanyExistsException();
        } else {
            if (companiesDBDAO.addCompany(company)) {
                System.out.println("New Company Created!!!");
            } else {
                System.out.println("The name of the company already exists!");
            }
        }
    }

    /**
     * a method to update the company
     * the method will get the company from the SQL
     * move variables to new place and update them
     * and will send send back to the SQL
     * will check if the name or the email already exsists
     * @param company the company were getting from SQL
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CompanyExistsException if the company name exists
     */
    public void updateCompany(Company company) throws SQLException, CompanyExistsException {
        System.out.println("Updating the Company...");

        if (companiesDBDAO.isCompanyExists(company.getEmail())) {
            throw new CompanyExistsException();
        } else {
            if (companiesDBDAO.updateCompany(company)) {
                System.out.println("Updated the Company!!!");
            } else {
                throw new SQLException();
            }
        }

    }

    /**
     * a method to delete the company
     * if we delete a company all the coupons of the company will be deleted as well
     * if we delete a company all the purchase history of the coupon that the customer bought will be deleted
     * @param companyID deleting the company by the ID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public void deleteCompany(int companyID) throws SQLException {
        try {
            companiesDBDAO.deleteCompany(companyID);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a method to get all the companies exists
     * @return all the companies
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Company> getAllCompanies() throws SQLException {
        return companiesDBDAO.getAllCompanies();
    }

    /**
     * a method to get a single company from the SQL server table
     * @param companyID getting the company by ID
     * @return return the company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public Company getOneCompany(int companyID) throws SQLException {
        return companiesDBDAO.getOneCompany(companyID);
    }

    /**
     * a method to add a new customer
     * the method will check if a customer with same email already exists
     * @param customer the customer were adding
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CustomerExistsException throws an exception if the customer already exists
     */
    public void addCustomer(Customer customer) throws SQLException, CustomerExistsException {
        System.out.println("Creating new Customer...");

        if (customerDBDAO.isCustomerExists(customer.getEmail())) {
            throw new CustomerExistsException();
        } else {
            if (customerDBDAO.addCustomer(customer)) {
                System.out.println("New Customer Created!!!");
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * a method to update a customer
     * there is no option to update the customer ID
     * @param customer the customer were updating
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CustomerExistsException will give an exception if the email already exists
     */
    public void updateCustomer(Customer customer) throws SQLException, CustomerExistsException {
        System.out.println("Updating a customer...");

        if (customerDBDAO.isCustomerExists(customer.getEmail())) {
            throw new CustomerExistsException();
        } else {
            if (customerDBDAO.updateCustomer(customer)) {
                System.out.println("Updated the customer!!!");
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * a method to delete the customer from the SQL server table
     * if we delete a customer we will delete the history of the purchase of the same customer
     * @param customerID delete the customer by the ID
     */
    public void deleteCustomer(int customerID) {
        try {
            customerDBDAO.deleteCustomer(customerID);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a method to get all the customer from the SQL server table
     * @return all the customers exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        return customerDBDAO.getAllCustomer();
    }

    /**
     * a method to get single customer from the SQL server table
     * @param customerID the customer ID we wanna get
     * @return the customer we chose
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public Customer getOneCustomer(int customerID) throws SQLException {
        return customerDBDAO.getOneCustomer(customerID);
    }
}
