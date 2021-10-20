package Tests.Facade;

import Beans.Coupon;
import DBDAO.CompaniesDBDAO;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Login.LoginManager;
import Enum.ClientType;
import Enum.CouponType;

import java.sql.Date;
import java.sql.SQLException;

public class CompanyFacadeTest {
    public static void main() {

        try {

            LoginManager loginManager = LoginManager.getInstance();

            //------------------------- LOGGING IN WITH FIRST COMPANY --------------------------

            System.out.println("======================================== COMPANY FACADE ============================================");

            System.out.println("Logging in first company:");

            ClientFacade clientFacade = loginManager.login("why@hate.bye", "98765", ClientType.Company);

            System.out.println("Logged company: " + ((CompanyFacade)clientFacade).getCompanyDetails());

            System.out.println("======================================== COMPANY FACADE ============================================");


            //---------------------------- COUPON #1 -----------------------------------

            Coupon myCoupon1 = new Coupon(1,
                    CouponType.valueOf("Sports"),
                    "Sports coupon",
                    "New Electric Duck 5001",
                    Date.valueOf("2021-08-24"),
                    Date.valueOf("2021-08-30"),
                    10,
                    150.0,
                    "Very beautiful");

            //---------------------------- COUPON #2 -----------------------------------

            Coupon myCoupon2 = new Coupon(1,
                    CouponType.valueOf("Sports"),
                    "Sports coupon",
                    "New Electric Duck 5002",
                    Date.valueOf("2021-08-24"),
                    Date.valueOf("2021-08-30"),
                    10,
                    160.0,
                    "Very beautiful");

            //---------------------------- COUPON #3 -----------------------------------

            Coupon myCoupon3 = new Coupon(1,
                    CouponType.valueOf("Sports"),
                    "Sports coupon",
                    "New Electric Duck 5003",
                    Date.valueOf("2021-08-24"),
                    Date.valueOf("2021-08-30"),
                    10,
                    170.0,
                    "Very beautiful");

            //---------------------------- COUPON #4 -----------------------------------

            Coupon myCoupon4 = new Coupon(1,
                    CouponType.valueOf("Sports"),
                    "Sports coupon",
                    "New Electric Duck 5004",
                    Date.valueOf("2021-08-24"),
                    Date.valueOf("2021-08-30"),
                    10,
                    180.0,
                    "Very beautiful");

            //---------------------------- COUPON #5 -----------------------------------

            Coupon myCoupon5 = new Coupon(1,
                    CouponType.valueOf("Sports"),
                    "Sports coupon",
                    "New Electric Duck 5005",
                    Date.valueOf("2021-08-24"),
                    Date.valueOf("2021-08-30"),
                    10,
                    190.0,
                    "Very beautiful");

            //----------------------------- ADDING 4 COUPONS TO THE SYSTEM ------------------------------

            System.out.println("===================================================");

            System.out.println("Adding 4 coupons to the system");

            ((CompanyFacade) clientFacade).addCoupon(myCoupon1);
            ((CompanyFacade) clientFacade).addCoupon(myCoupon2);
            ((CompanyFacade) clientFacade).addCoupon(myCoupon3);
            ((CompanyFacade) clientFacade).addCoupon(myCoupon4);

            System.out.println("===================================================");

            //------------------------ SHOWING ALL THE COUPONS OF THE THE LOGGED IN COMPANY -------------------

            System.out.println("===================================================");

            System.out.println("Showing all the coupons of the logged in company...");

            ((CompanyFacade) clientFacade).getCompanyCoupons().forEach(System.out::println);

            System.out.println("===================================================");

            //---------------------------------------- UPDATING COUPON #2 --------------------------------------

            System.out.println("===================================================");

            System.out.println("Updating coupon #2...");

            Coupon updatingCoupon = ((CompanyFacade)clientFacade).getCompanyCoupons().get(2);

            System.out.println("The coupon being updated: ");

            System.out.println(((CompanyFacade)clientFacade).getCompanyCoupons().get(2));

            updatingCoupon.setAmount(5);
            updatingCoupon.setDescription("NEW Coupon Electric duck 5002");
            updatingCoupon.setImage("Even more beautiful");
            updatingCoupon.setPrice(182);
            updatingCoupon.setTitle("New Electric Duck5002");
            updatingCoupon.setId(2);

            ((CompanyFacade) clientFacade).updateCoupon(updatingCoupon);

            System.out.println("===================================================");

            //-------------------------------------- DELETING COUPON #5 -----------------------------------------

            System.out.println("===================================================");

            System.out.println("Deleting coupon #5...");

            ((CompanyFacade) clientFacade).deleteCoupon(5);

            System.out.println("===================================================");

            //----------------- SHOWING ALL THE COUPONS OF THE THE LOGGED IN COMPANY AFTER UPDATE -------------------

            System.out.println("===================================================");

            System.out.println("Showing all the coupons of the logged in company after update...");

            ((CompanyFacade) clientFacade).getCompanyCoupons().forEach(System.out::println);

            System.out.println("===================================================");

            //-------------------- SHOWING ALL THE COUPONS OF THE LOGGED IN COMPANY BY CATEGORY ------------------------

            System.out.println("===================================================");

            System.out.println("Showing all the coupons of the logged in company by the " + CouponType.Sports + " Category");

            ((CompanyFacade) clientFacade).getCompanyCouponsByCategory(3).forEach(System.out::println);

            System.out.println("===================================================");

            //-------------------- SHOWING ALL THE COUPONS OF THE LOGGED IN COMPANY BY MAX PRICE ---------------------

            System.out.println("===================================================");

            System.out.println("Showing company's coupons by max price:");

            System.out.println("Max price: 150.");

            ((CompanyFacade) clientFacade).getCompanyCouponsByPrice(150.0).forEach(System.out::println);

            System.out.println("Max price 160.");

            ((CompanyFacade) clientFacade).getCompanyCouponsByPrice(160.0).forEach(System.out::println);

            System.out.println("Max price 170.");

            ((CompanyFacade) clientFacade).getCompanyCouponsByPrice(170.0).forEach(System.out::println);

            System.out.println("===================================================");

            System.out.println("Getting Company details");

            System.out.println(((CompanyFacade) clientFacade).getCompanyDetails());

            System.out.println("===================================================");


        } catch (Exception error) {
            System.out.println("Something somewhere went terrible wrong in Company facade");
        }

    }
}
