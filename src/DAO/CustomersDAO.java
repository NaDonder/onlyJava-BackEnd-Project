package DAO;

import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {
    /**
     * DAO - Data Access Object
     * @param email Email to check if the customer exists
     * @return returns true/false if the customer exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Boolean isCustomerExists(String email) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param customer to add the Customer
     * @return returns true/false if we could add customer to the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Boolean addCustomer(Customer customer) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param customer to update the Customer
     * @return returns true/false if we could update the customer at the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean updateCustomer(Customer customer) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param customerID CustomerID to delete the Customer by the SQL server ID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    void deleteCustomer(int customerID) throws SQLException;

    /**
     * DAO - Data Access Object
     * @return returns all the customers on the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    ArrayList<Customer> getAllCustomer() throws SQLException;

    /**
     *  DAO - Data Access Object
     * @param customerID CustomerID to get single Customer from the SQL server
     * @return returns the customer from the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Customer getOneCustomer(int customerID) throws SQLException;
}
