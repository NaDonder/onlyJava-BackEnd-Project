package Facade;

import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.CouponNoAmountException;
import Exceptions.CouponOutOfDateException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {

    private int customerID;

    CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();

    public CustomerFacade(){}

    /**
     * a method to login to the customer from the SQL
     * @param email email of the customer
     * @param password password of the customer
     * @return returns true/false if we could connect or not
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean login(String email, String password) throws SQLException {
        this.customerID = customerDBDAO.isCustomerExistsByEmailAndPassword(email, password);
        return customerID != 0;
    }

    /**
     * a method to purchase coupon from the system
     * you cannot buy the same coupon more than once
     * you cannot by the coupon if the amount is 0
     * you cannot buy an expired coupon
     * after buying a coupon his amount will decrease by 1
     * @param coupon the coupon were purchasing
     * @param couponID the ID of the coupon
     * @throws CouponNoAmountException throws exception if there is no amount left from the coupon
     * @throws CouponOutOfDateException throws exception if the coupon is out of date
     */
    public void purchaseCoupon(Coupon coupon,int couponID) throws CouponNoAmountException, CouponOutOfDateException {

        Coupon decrease = coupon;

        if (coupon.getAmount() == 0) {
            throw new CouponNoAmountException();
        } else if (new Date(new java.util.Date().getTime()).after(coupon.getEndDate())) {
            throw new CouponOutOfDateException();
        } else {
            try {
                System.out.println("Buying a Coupon....");
                if (couponDBDAO.addCouponPurchase(customerID, couponID)) {
                    decrease.setAmount(decrease.getAmount() - 1);
                decrease.setId(couponID);
                couponDBDAO.decreaseCouponAmountby1(decrease);
            }
            } catch (SQLException | InterruptedException error) {
                System.out.println(error.getMessage());
            }
        }
    }

    /**
     * a method to get all the customers coupons
     * @return return the logged in customer coupons
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCustomerCoupons() throws SQLException {
        return customerDBDAO.getAllCustomerCoupons(customerID);
    }

    /**
     * a method to show the coupons by the category selected
     * @param coupontype the coupon type by number
     * @return the coupons of the logged in customer by the category
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCustomerCoupons(int coupontype) throws SQLException {
        return customerDBDAO.getAllCustomerCouponsByCategory(customerID,coupontype);
    }

    /**
     * a method to show all the coupons between 0 and the max price selected
     * @param maxPrice max price of the coupons
     * @return the coupons of the customer logged in by the max price selected
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException {
        return customerDBDAO.getAllCustomerCouponsByMaxPrice(this.customerID,maxPrice);
    }

    /**
     * a method to get the details of the customer that is logged in
     * @return all the details of the customer that is logged in
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public Customer getCustomerDetails() throws SQLException {
        return customerDBDAO.getOneCustomer(this.customerID);
    }
}
