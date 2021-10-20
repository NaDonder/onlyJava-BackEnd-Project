package DBDAO;

import Beans.Company;
import Beans.Coupon;
import DAO.CompaniesDAO;
import Exceptions.CompanyException;
import Exceptions.CompanyNotFoundException;
import SQL.ConnectionPool;
import Enum.CouponType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {
    /**
     * All the SQL syntax statements
     */
    private static final String ADD_COMPANY = "INSERT INTO `CouponDB`.`COMPANIES` (`name`,`email`,`password`) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `CouponDB`.`COMPANIES` SET name=?, email=?, password=? WHERE id=?";
    private static final String DELETE_COMPANY_BY_ID = "DELETE FROM `CouponDB`.`COMPANIES` WHERE id=?";
    private static final String GET_ALL_COMPANY = "SELECT * FROM `CouponDB`.`COMPANIES`";
    private static final String GET_COMPANY_BY_ID = "SELECT * FROM `CouponDB`.`COMPANIES` WHERE id=?";
    private static final String IS_EXISTS = "SELECT count(*) FROM `CouponDB`.`COMPANIES` WHERE email=?";
    private static final String GET_ALL_COMPANY_COUPONS_BY_CATEGORY = "SELECT * FROM `CouponDB`.`COUPONS` WHERE COMPANY_ID=? AND CATEGORY_ID=?";
    private static final String GET_COMPANY_EMAIL_AND_PASSWORD = "SELECT `ID` FROM `CouponDB`.`COMPANIES` WHERE email=? AND password=?";
    private static final String GET_ALL_COMPANY_COUPONS_BY_MAXPRICE = "SELECT * FROM `CouponDB`.`COUPONS` WHERE COMPANY_ID=? AND price BETWEEN 0 AND ?";
    private static final String GET_ALL_COMPANY_COUPONS = "SELECT * FROM `CouponDB`.`COUPONS` WHERE COMPANY_ID=?";

    /**
     * The Connection to the database
     */
    Connection connection;


    /**
     * Default constructor with ConnectionPool instance (SingleTon Class)
     */
    public CompaniesDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException throwables) {
            System.out.println("Connection disturbed");
        }
    }

    /**
     * A method to get the logged Customer into the platform
     * @param email Checking by the email
     * @param password Checking by the password
     * @return 1 or 0 if the customer could connect
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public int isCompanyExistsByEmailAndPassword(String email, String password) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_EMAIL_AND_PASSWORD);
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
    }

    /**
     * a method to check if the Company exists
     * @param email Email to check if the company exists
     * @return returns true/false if the company exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Boolean isCompanyExists(String email) throws SQLException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();
            //prepare sql statement
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            //set email
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();


            // --- DOESN'T WORK --- = You can't use resultset after executing a statement
            //statement.execute();
            //return (resultSet.getInt(1) > 0);

            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to add the company to the SQL server
     * @param company Adding company to the SQL server table
     * @return returns true/false if we could add the company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean addCompany(Company company) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(ADD_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();

            return true;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to update the company in the SQL server
     * @param company Updating a company to the SQL server table
     * @return returns true/false if we could update the company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean updateCompany(Company company) throws SQLException {
        try {
            //get connection to the database
            connection = ConnectionPool.getInstance().getConnection();

            //create a prepared sql statement
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.setInt(4, company.getId());
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException error) {
            new CompanyException("Something went wrong while trying to update a company");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to delet the company in the SQL server
     * @param companyID companyID needs to match SQL server table companyID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public void deleteCompany(int companyID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY_BY_ID);
            statement.setInt(1, companyID);
            statement.execute();
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * a method to get all the companies in the SQL server
     * @return return all the companies in the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException {
        ArrayList<Company> companies = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));

                companies.add(company);
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companies;
    }

    /**
     * a method to get a company by the ID from the SQL server
     * @param companyID for getting a single Company from the SQL server table
     * @return returns the chosen company from the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Company getOneCompany(int companyID) throws SQLException {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_BY_ID);
            statement.setInt(1, companyID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return company;
    }

    /**
     * a method to get all the coupons of the company that logged in
     * @param companyID to get the company that logged in
     * @return returns all the coupons of the logged company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCompanyCoupons(int companyID) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY_COUPONS);
            statement.setInt(1,companyID);

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
     * a method to get all the coupons of the company that logged in by the Category
     * @param companyID to get the company that logged in
     * @param categoryID to get the Company's coupons by the Category
     * @return returns all the coupons of the company by the Category
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCompanyCouponsCategory(int companyID, int categoryID) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY_COUPONS_BY_CATEGORY);
            statement.setInt(1, companyID);
            statement.setInt(2, categoryID); //CouponType.values()[resultSet.getInt("category_id")],
            //statement.execute();
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
     * a method to get all the coupons of the company that logged in by the max price
     * @param companyID to get the company that logged in
     * @param maxPrice for the max price of coupons
     * @return returns all the coupons of the company by the maxprice
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCompanyCouponsByMaxPrice(int companyID, double maxPrice) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY_COUPONS_BY_MAXPRICE);
            statement.setInt(1, companyID);
            statement.setDouble(2, maxPrice);

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

