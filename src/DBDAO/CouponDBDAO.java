package DBDAO;

import Beans.Coupon;
import DAO.CouponsDAO;
import SQL.ConnectionPool;
import Enum.CouponType;

import java.sql.*;
import java.util.ArrayList;

public class CouponDBDAO implements CouponsDAO {


    /**
     * All the SQL syntax statements
     */
    private static final String ADD_COUPON = " INSERT INTO `CouponDB`.`COUPONS` (`company_id`,`category_id`,`start_date`,`end_date`"
            + ",`amount`,`description`,`image`,`price`,`title`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `CouponDB`.`COUPONS` SET end_date=?, "
            + "amount=?, description=?, image=?, price=?,title=? WHERE id=? ";
    private static final String DELETE_COUPON_BY_ID = "DELETE FROM `CouponDB`.`COUPONS` WHERE id=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `CouponDB`.`COUPONS`";
    private static final String GET_ONE_COUPON_BY_ID = "SELECT * FROM `CouponDB`.`COUPONS` WHERE id=? ";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `CouponDB`.`CUSTOMERS_VS_COUPONS` (`CUSTOMER_ID`,`COUPON_ID`) VALUES (?,?) ";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `CouponDB`.`CUSTOMERS_VS_COUPONS` WHERE `CUSTOMER_ID=?` AND `COUPON_ID=?` ";
    private static final String IS_EXISTS = " SELECT count(*) FROM `CouponDB`.`COUPONS` WHERE COMPANY_ID=? AND TITLE=? ";
    private static final String DECREASE_AMOUNT = "UPDATE `CouponDB`.`COUPONS` SET amount=? WHERE id=? ";
    private static final String ADD_CATEGORIES = " INSERT INTO `CouponDB`.`CATEGORIES` (`NAME`) VALUES (?)";
    private static final String DELETE_COUPON_JOB = " DELETE FROM `CouponDB`.`COUPONS` WHERE ? > END_DATE ";

    /**
     * The Connection to the database
     */
    Connection connection;

    /**
     * Default constructor with ConnectionPool instance (SingleTon Class)
     */
    public CouponDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException throwables) {
            System.out.println("Connection disturbed");
        }
    }

    /**
     * a method to check if the coupon exists
     * @param companyID we checking if the coupon exists by the companyID
     * @param title we checking if the coupon exists by the title
     * @return returns true/false if the coupon exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Boolean isCouponsExists(int companyID, String title) throws SQLException {
        try {
            // get connection to db
            connection = ConnectionPool.getInstance().getConnection();
            // prepare SQL statement
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            // set coupon ID
            statement.setInt(1, companyID);
            // set company ID
            statement.setString(2, title);
            ResultSet resultSet = statement.executeQuery();

            // --- DOESN'T WORK --- = You can't use resultset after executing a statement
            //statement.execute();
            //return (resultSet.getInt(1) > 0);

            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to add coupon to the SQL server table
     * @param coupon Adding coupon to the SQL server table
     * @return returns true/false if we could add the coupon to the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean addCoupon(Coupon coupon) throws SQLException {
        try {
            //connection to database
            connection = ConnectionPool.getInstance().getConnection();

            //create SQL statement
            PreparedStatement statment = connection.prepareStatement(ADD_COUPON);
            statment.setInt(1, coupon.getCompanyID());
            statment.setInt(2, coupon.getCategory());
            statment.setDate(3, coupon.getStartDate());
            statment.setDate(4, coupon.getEndDate());
            statment.setInt(5, coupon.getAmount());
            statment.setString(6, coupon.getDescription());
            statment.setString(7, coupon.getImage());
            statment.setDouble(8, coupon.getPrice());
            statment.setString(9, coupon.getTitle());
            statment.execute();
            return true;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to update the coupon in the SQL server table
     * @param coupon Updating coupon ot the SQL server table
     * @return returns true/false if we could update the coupon in the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean updateCoupon(Coupon coupon) throws SQLException {
        try {
            //connection to database
            connection = ConnectionPool.getInstance().getConnection();

            //create SQL statement
            PreparedStatement statment = connection.prepareStatement(UPDATE_COUPON);
            statment.setDate(1, coupon.getEndDate());
            statment.setInt(2, coupon.getAmount());
            statment.setString(3, coupon.getDescription());
            statment.setString(4, coupon.getImage());
            statment.setDouble(5, coupon.getPrice());
            statment.setString(6, coupon.getTitle());
            statment.setInt(7, coupon.getId());
            statment.execute();
            return true;
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * a method to delete the coupon in the SQL server table
     * @param couponID CouponID for deleting coupon by the ID in the SQL server table
     * @return returns true/false if we could delete the coupon
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean deleteCoupon(int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_BY_ID);
            statement.setInt(1, couponID);
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
     * a method to get single coupon details from the SQL server
     * @param couponID CouponID to choose only one coupon in the SQL server table
     * @return returns one coupon from the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Coupon coupon = null;
        try {
            //connection to database
            connection = ConnectionPool.getInstance().getConnection();
            // create SQL statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_ID);
            statement.setInt(1, couponID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(
                        resultSet.getInt("company_ID"),
                        CouponType.values()[resultSet.getInt("category_id")], //int from SQL
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_Date"),
                        resultSet.getDate("end_Date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));
            }
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }

    /**
     * a method to get all the coupons exists in the SQL server
     * @return returns all the coupons from the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt("company_ID"),
                        CouponType.values()[resultSet.getInt("category_id")], //int from SQL
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_Date"),
                        resultSet.getDate("end_Date"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"));

                coupons.add(coupon);
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * a method for the DailyExpirationDailyJob thread to delete the expired coupons (no SQL statements)
     * @return coupons with ID, start date and end date
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getAllCouponsByID() throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt("ID"),
                        resultSet.getDate("start_Date"),
                        resultSet.getDate("end_Date")
                );

                coupons.add(coupon);
            }
        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * a method to add a coupon purchase by the customer
     * @param customerID to add Coupon purchase we need the CustomerID
     * @param couponID to add Coupon purchase we need the CouponID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @return returns true/false if we could or could not purchase the coupon
     */
    @Override
    public boolean addCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON_PURCHASE);

            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
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
     * a method to delete a coupon purchase
     * @param customerID to delete Coupon purchase we need the CustomerID
     * @param couponID to delete Coupon purchase we need the CouponID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE);

        statement.setInt(1,customerID);
        statement.setInt(2,couponID);
        statement.execute();

        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * a method to decrease coupon amount by 1 if bought by the customer
     * @param coupon choosing the coupon
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws InterruptedException Throw interrupted exception if interrupted
     */
    public void decreaseCouponAmountby1(Coupon coupon) throws InterruptedException, SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DECREASE_AMOUNT);
            statement.setInt(1, coupon.getAmount());
            statement.setInt(2, coupon.getId());
            statement.execute();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * a method to delete expired coupons by an SQL statement
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public void deleteCouponJob() throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_JOB);
            String myDate = new Date(new java.util.Date().getTime())+"";
            statement.setString(1,myDate);
            statement.execute();
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * a method to add category to the SQL server
     * @param name name of the category
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public void addCategories(String name) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_CATEGORIES);

            statement.setString(1,name);

            statement.execute();

        } catch (InterruptedException | SQLException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}

