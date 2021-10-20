package DAO;

import Beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

    /**
     * DAO - Data Access Object
     * @param companyID we checking if the coupon exists by the companyID
     * @param title we checking if the coupon exists by the title
     * @return returns true/false if the coupon exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Boolean isCouponsExists (int companyID, String title) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param coupon Adding coupon to the SQL server table
     * @return returns true/false if we could add the coupon to the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean addCoupon (Coupon coupon) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param coupon Updating coupon ot the SQL server table
     * @return returns true/false if we could update the coupon to the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean updateCoupon (Coupon coupon) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param couponID CouponID for deleting coupon by the ID in the SQL server table
     * @return returns true/false if we could delete the coupon
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean deleteCoupon (int couponID) throws SQLException;

    /**
     * DAO - Data Access Object
     * @return returns all the coupons in the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    ArrayList<Coupon> getAllCoupons() throws SQLException;

    /**
     * DAO - Data Access Object
     * @param couponID CouponID to choose only one coupon in the SQL server table
     * @return returns the coupon from the SQL server
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Coupon getOneCoupon(int couponID) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param customerID to add Coupon purchase we need the CustomerID
     * @param couponID to add Coupon purchase we need the CouponID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @return true/false if we could purchase the coupon
     */
    boolean addCouponPurchase(int customerID, int couponID) throws SQLException;

    /**
     * DAO - Data Access Object
     * @param customerID to delete Coupon purchase we need the CustomerID
     * @param couponID to delete Coupon purchase we need the CouponID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    void deleteCouponPurchase (int customerID, int couponID) throws SQLException;

    
}
