package Facade;

import Beans.Company;
import Beans.Coupon;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponDBDAO;
import Enum.CouponType;
import Exceptions.CouponExistsException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

    private int companyID = 0;

    CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();

    public CompanyFacade(){}

    /**
     * this method will log the company from the SQL
     * @param email email of the company
     * @param password password of the company
     * @return returns true/false if we could login
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    @Override
    public boolean login(String email, String password) throws SQLException {
        companyID = companiesDBDAO.isCompanyExistsByEmailAndPassword(email, password);
        return companyID != 0;
    }

    /**
     * a method to creating a new coupon in the system
     * we cannot add new coupon with same name for the same company
     * @param coupon the coupon were adding
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CouponExistsException throws exception if the coupon Exists
     */
    public void addCoupon(Coupon coupon) throws SQLException, CouponExistsException {
        System.out.println("Creating new coupon...");
        if (couponDBDAO.isCouponsExists(companyID, coupon.getTitle())) {
            throw new CouponExistsException();
        } else {
            if (couponDBDAO.addCoupon(coupon)) {
                System.out.println("New Coupon Created!!!");
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * a method to update the fields of the existing coupon
     * you cannot update the code of the coupon
     * you cannot update the code of the company
     * @param coupon the coupon were updating
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     * @throws CouponExistsException throws Exception if the coupon exists
     */
    public void updateCoupon(Coupon coupon) throws SQLException, CouponExistsException {
        System.out.println("Updating a Coupon...");
        System.out.println("---------------------");

        if (couponDBDAO.isCouponsExists(coupon.getCompanyID(), coupon.getTitle())) {
            throw new CouponExistsException();
        } else {
            if (couponDBDAO.updateCoupon(coupon)) {
                System.out.println("Updated a coupon!!!");
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * a method to delete coupon from the system
     * if we delete a coupon we will delete the purchase of the customer of the coupon
     * @param couponID deleting by the coupon ID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public void deleteCoupon(int couponID) throws SQLException {
        System.out.println("Deleting a coupon....");

        try {
            couponDBDAO.deleteCoupon(couponID);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a method to show all coupons of the company that is logged in
     * @return all the company coupons
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCompanyCoupons() throws SQLException {
        return companiesDBDAO.getAllCompanyCoupons(companyID);
    }

    /**
     * a method to return all the logged in company coupons by the category
     * @param categoryID the ID of the category of the coupon
     * @return all the coupons from the category
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCompanyCouponsByCategory(int categoryID) throws SQLException {
        return companiesDBDAO.getAllCompanyCouponsCategory(companyID, categoryID);
    }

    /**
     * a method to return all the coupons of the logged in company between 0 and the max price selected
     * @param maxPrice the max price we want to see the coupons
     * @return the coupons from 0 to the max price selected
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public ArrayList<Coupon> getCompanyCouponsByPrice(double maxPrice) throws SQLException {
        return companiesDBDAO.getAllCompanyCouponsByMaxPrice(companyID, maxPrice);
    }

    /**
     * a method to show the logged in company
     * @return the details of the logged in company
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    public Company getCompanyDetails() throws SQLException {
        return companiesDBDAO.getOneCompany(this.companyID);
    }
}
