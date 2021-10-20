package Tests.Facade;

import DBDAO.CouponDBDAO;
import Facade.ClientFacade;
import Facade.CustomerFacade;
import Login.LoginManager;
import Enum.ClientType;

public class CustomerFacadeTest {
    public static void main(){

        CouponDBDAO couponDBDAO = new CouponDBDAO();

        try {

            LoginManager loginManager = LoginManager.getInstance();

            //---------------------------- LOGGING IN AS FIRST CUSTOMER -------------------------------------

            System.out.println("======================================== CUSTOMER FACADE ============================================");

            ClientFacade clientFacade = loginManager.login("The@Only.one", "GOD", ClientType.Customer);

            System.out.println("Logged customer: " + ((CustomerFacade)clientFacade).getCustomerDetails());

            System.out.println("======================================== CUSTOMER FACADE ============================================");

            //------------------------------- CUSTOMER BUYING 4 COUPONS ------------------------------

            System.out.println("===================================================");

            System.out.println("Customer purchasing 4 coupons....");

            ((CustomerFacade) clientFacade).purchaseCoupon(couponDBDAO.getOneCoupon(1), 1);
            ((CustomerFacade) clientFacade).purchaseCoupon(couponDBDAO.getOneCoupon(2), 2);
            ((CustomerFacade) clientFacade).purchaseCoupon(couponDBDAO.getOneCoupon(3), 3);
            ((CustomerFacade) clientFacade).purchaseCoupon(couponDBDAO.getOneCoupon(4), 4);

            System.out.println("Customer purchased 4 coupons successfully");

            System.out.println("===================================================");

            //--------------------------- GETTING ALL LOGGED IN CUSTOMER COUPONS -----------------------------

            System.out.println("===================================================");

            System.out.println("Getting all customers coupons: ");

            ((CustomerFacade) clientFacade).getCustomerCoupons().forEach(System.out::println);

            System.out.println("===================================================");

            //------------------------ GETTING ALL LOGGED IN CUSTOMER COUPONS BY CATEGORY ------------------------

            System.out.println("===================================================");

            System.out.println("Getting all customer coupons by category: ");

            ((CustomerFacade)clientFacade).getCustomerCoupons(3).forEach(System.out::println);

            System.out.println("===================================================");

            //----------------------- GETTING ALL LOGGED IN CUSTOMER COUPONS BY MAX PRICE --------------------------

            System.out.println("===================================================");

            System.out.println("Getting all customers coupons by max price: ");

            System.out.println("-------------------------- Max price 150 -------------------------------");

            ((CustomerFacade)clientFacade).getCustomerCoupons(150.0).forEach(System.out::println);

            System.out.println("-------------------------- Max price 160 -------------------------------");

            ((CustomerFacade)clientFacade).getCustomerCoupons(160.0).forEach(System.out::println);

            System.out.println("-------------------------- Max price 170 -------------------------------");

            ((CustomerFacade)clientFacade).getCustomerCoupons(170.0).forEach(System.out::println);

            System.out.println("-------------------------- Max price 180 -------------------------------");

            ((CustomerFacade)clientFacade).getCustomerCoupons(180.0).forEach(System.out::println);

            System.out.println("===================================================");

            //------------------------------- GETTING CUSTOMERS DETAILS ---------------------------------------

            System.out.println("===================================================");

            System.out.println("Customers details: ");

            System.out.println(((CustomerFacade)clientFacade).getCustomerDetails());

            System.out.println("===================================================");



        } catch (Exception error) {
            System.out.println("Something somewhere went terrible wrong in Customer facade");
        }
    }
}
