package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import DAO.CustomersDAO;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDBDAO implements CustomersDAO {
    /**
     * All the SQL syntax statements
     */
    private static final String ADD_CUSTOMER = "INSERT INTO `CouponDB`.`CUSTOMERS` (`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PASSWORD`) VALUES (?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `CouponDB`.`CUSTOMERS` SET FIRST_NAME=?,LAST_NAME=?, EMAIL=?, PASSWORD=? WHERE id=?";
    private static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM `CouponDB`.`CUSTOMERS` WHERE id=?";
    private static final String GET_ALL_CUSTOMER = "SELECT * FROM `CouponDB`.`CUSTOMERS`";
    private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM `CouponDB`.`CUSTOMERS` WHERE id=?";
    private static final String IS_CUSTOMER_EXIST = "SELECT count(*) FROM `CouponDB`.`CUSTOMERS` WHERE email=?";
    private static final String GET_CUSTOMER_EMAIL_AND_PASSWORD = "SELECT `ID` FROM `CouponDB`.`CUSTOMERS` WHERE email=? AND password=?";
    private static final String GET_ALL_CUSTOMER_COUPONS = "SELECT `ID`, `Company_Id`," +
            "`Amount`, `Price`, `Category_Id`," +
            "`Title`, `Description`, `Image`," +
            "`Start_Date`, `End_Date`" +
            "FROM `coupondb`.`coupons`" +
            "INNER JOIN `coupondb`.`customers_vs_coupons`" +
            "ON coupons.ID = customers_vs_coupons.Coupon_Id " +
            "WHERE customers_vs_coupons.CUSTOMER_ID=?";
    private static final String GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY = "SELECT `ID`, `Company_Id`," +
            "`Amount`, `Price`, `Category_Id`," +
            "`Title`, `Description`, `Image`," +
            "`Start_Date`, `End_Date`" +
            "FROM `coupondb`.`coupons`" +
            "INNER JOIN `coupondb`.`customers_vs_coupons`" +
            "ON coupons.ID = customers_vs_coupons.Coupon_Id " +
            "WHERE customers_vs_coupons.Customer_Id=? AND coupons.Category_Id=?" ;
    private static final String GET_ALL_CUSTOMER_COUPONS_BY_MAXPRICE = "SELECT `ID`, `Company_Id`," +
            "`Amount`, `Price`, `Category_Id`," +
            "`Title`, `Description`, `Image`," +
            "`Start_Date`, `End_Date` " +
            "FROM `coupondb`.`coupons` " +
            "INNER JOIN `coupondb`.`customers_vs_coupons` " +
            "ON coupons.ID = customers_vs_coupons.Coupon_Id " +
            "WHERE customers_vs_coupons.Customer_Id=? AND coupons.Price BETWEEN 0 AND ? ";


    /**
     * The Connection to the database
     */
    Connection connection;

    /**
     * Default constructor with ConnectionPool instance (SingleTon Class)
     */
    public CustomerDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException throwables) {
            System.out.println("Connection disturbed");
        }
    }

    /**
     * a method to get the customer logged into the platform
     * @param email Checking by the email
     * @param password Checking by the password
     * @return return 1 or 0 if the customer could connect
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public int isCustomerExistsByEmailAndPassword(String email, String password) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return 0;
    } // --------------- DONE -------------------

    /**
     * a method to check if the customer exists
     * @param email Email to check if the customer exists
     * @return true/false if the customer exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Boolean isCustomerExists(String email) throws SQLException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            //prepare sql statement
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXIST);
            //set email
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            // --- DOESN'T WORK --- = You can't use resultset after executing a statement
            //statement.execute();
            //return (resultSet.getInt(1) > 0);

            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    } // --------------- DONE -------------------

    /**
     * a method to add the customer to the SQL server table
     * @param customer to add the Customer
     * @return true/false if we could add the customer
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Boolean addCustomer(Customer customer) throws SQLException {
        try {
            // get connection to the database
            connection = ConnectionPool.getInstance().getConnection();

            //create a prepared sql statement
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    } // --------------- DONE -------------------

    /**
     * a method to update the customer in the SQL server table
     * @param customer to update the Customer
     * @return true/false if we could update the customer
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();

            //create a prepared sql statement
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getId());
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    } // --------------- DONE -------------------

    /**
     * a method to delete the customer in the SQL server table
     * @param customerID CustomerID to delete the Customer by the SQL server ID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public void deleteCustomer(int customerID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
            statement.setInt(1, customerID);
            statement.execute();
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    } // --------------- DONE -------------------

    /**
     * a method to get all the customer from the SQL server table
     * @return all the customer in the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));

                customers.add(customer);
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customers;
    } // --------------- DONE -------------------

    /**
     * a method to get a single customer from the SQL server table
     * @param customerID CustomerID to get single Customer from the SQL server
     * @return the customer chosen from the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Customer getOneCustomer(int customerID) throws SQLException {
        Customer customer = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BY_ID);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customer;
    } // --------------- DONE -------------------

    /**
     * a method to get all the coupons of the logged in customer
     * @param customerID get the customer that is logged in
     * @return all the customer's coupons
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCustomerCoupons(int customerID) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER_COUPONS);
            statement.setInt(1, customerID);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getString("description"),
                        resultSet.getString("title"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * a method to get all the coupons of the logged in customer by category
     * @param customerID the customer id login
     * @param categoryID the category id
     * @return all the coupons by the category
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCustomerCouponsByCategory(int customerID, int categoryID) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY);
            statement.setInt(1, customerID);
            statement.setInt(2,categoryID);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getString("description"),
                        resultSet.getString("title"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * a method to get all coupons of the logged in customer by max price
     * @param customerID the customer id login
     * @param price the max price selected
     * @return all the coupons by the max price selected
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCustomerCouponsByMaxPrice(int customerID, double price) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER_COUPONS_BY_MAXPRICE);
            statement.setInt(1, customerID);
            statement.setDouble(2, price);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getString("description"),
                        resultSet.getString("title"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
            }
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }
}
