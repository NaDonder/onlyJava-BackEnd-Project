package Tests.Facade;

import Beans.Company;
import Beans.Customer;
import DBDAO.CouponDBDAO;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Login.LoginManager;
import Enum.ClientType;
import SQL.DataBaseManager;

public class AdminFacadeTest {
    public static void main() {

        CouponDBDAO couponDBDAO = new CouponDBDAO();

        try {
            LoginManager loginManager = LoginManager.getInstance();


            System.out.println("Dropping database.");
            DataBaseManager.dropDatabase();
            System.out.println("Creating database in SQL");
            DataBaseManager.createDataBase();
            System.out.println("Creating all tables");
            DataBaseManager.createTables();
            System.out.println("Creating all categories");
            couponDBDAO.addCategories("Electricity");
            couponDBDAO.addCategories("Restaurants");
            couponDBDAO.addCategories("Healthcare");
            couponDBDAO.addCategories("Sports");
            couponDBDAO.addCategories("Camping");
            couponDBDAO.addCategories("Travelling");

            //------------------------------------------- ADMIN LOGIN --------------------------------------------

            System.out.println("======================================== ADMIN FACADE ============================================");

            System.out.println("Logging in as administrator...");

            ClientFacade clientFacade = loginManager.login("admin@admin.com", "admin", ClientType.Administrator);

            System.out.println("Logged as administrator!!!");

            System.out.println("======================================== ADMIN FACADE ============================================");

            //---------------------------------------- CREATING A NEW COMPANY ----------------------------

            System.out.println("========================================================");

            System.out.println("Creating 3 new Companies...");

            //((AdminFacade) clientFacade).addCompany(new Company("Natan's new Sausage shop", "hi@bye.com", "12345"));

            Company bestCompany1 = new Company("Natan's new Sausage shop", "hi@bye.com", "12345");
            Company bestCompany2 = new Company("Cristopher's new Gaming shop", "hello@goodbye.com", "23456");
            Company bestCompany3 = new Company("Karoline's new Doll shop", "bye@goodbye.com", "34567");

            ((AdminFacade)clientFacade).addCompany(bestCompany1);
            ((AdminFacade)clientFacade).addCompany(bestCompany2);
            ((AdminFacade)clientFacade).addCompany(bestCompany3);

            System.out.println("You successfully added 3 companies!!!");

            System.out.println("========================================================");

            //------------------------------------------ SHOW ALL COMPANY ----------------------------------

            System.out.println("========================================================");

            System.out.println("Getting all companies...");

            ((AdminFacade) clientFacade).getAllCompanies().forEach(System.out::println);

            System.out.println("========================================================");

            //------------------------------------------- UPDATE 1 COMPANY ------------------------------------------

            System.out.println("========================================================");

            System.out.println("Updating 1 company...");

            Company updatingCompany = ((AdminFacade) clientFacade).getOneCompany(1);

            System.out.println("The Company being updated: ");

            System.out.println(((AdminFacade)clientFacade).getOneCompany(1).toString());

            updatingCompany.setName(updatingCompany.getName()); //changing the name of the company is not allowed
            updatingCompany.setEmail("why@hate.bye");
            updatingCompany.setPassword("98765");
            updatingCompany.setId(((AdminFacade) clientFacade).getOneCompany(1).getId());

            ((AdminFacade)clientFacade).updateCompany(updatingCompany);

            System.out.println("You successfully updated a company!!!");

            System.out.println("========================================================");

            // ------------------------------------ SHOW ALL COMPANY AFTER UPDATE ------------------------------------

            System.out.println("========================================================");

            System.out.println("Getting all companies after update...");

            ((AdminFacade) clientFacade).getAllCompanies().forEach(System.out::println);

            System.out.println("========================================================");

            // ------------------------------------------- SHOW ONLY 1 COMPANY -------------------------------------------

            System.out.println("========================================================");

            System.out.println("Showing chosen company...");

            System.out.println(((AdminFacade) clientFacade).getOneCompany(1));

            System.out.println("========================================================");

            //------------------------------------------------- DELETE A COMPANY -----------------------------------------

            System.out.println("========================================================");

            System.out.println("Deleting a company...");

            System.out.println("The company being deleted: ");

            System.out.println(((AdminFacade)clientFacade).getOneCompany(2));

            ((AdminFacade) clientFacade).deleteCompany(2);

            System.out.println("Deleting was successful");

            System.out.println("========================================================");

            //------------------------------ SHOW ALL COMPANIES AFTER DELETING 1 ------------------------------------

            System.out.println("========================================================");

            System.out.println("Showing all companies after deleting 1 company...");

            ((AdminFacade) clientFacade).getAllCompanies().forEach(System.out::println);

            System.out.println("========================================================");

            //------------------------------ CREATING NEW CUSTOMER --------------------------------

            System.out.println("========================================================");

            System.out.println("Creating 3 new customers...");

            //((AdminFacade) clientFacade).addCustomer(new Customer("Natan", "Donder", "The@Only.one", "GOD"));

            Customer bestCustomer1 = new Customer("Natan", "Donder", "The@Only.one", "GOD");
            Customer bestCustomer2 = new Customer("Dori", "Muhamed", "If@only.haha", "456456");
            Customer bestCustomer3 = new Customer("Noa", "Peled", "The@Magic.one", "neverLand");

            ((AdminFacade)clientFacade).addCustomer(bestCustomer1);
            ((AdminFacade)clientFacade).addCustomer(bestCustomer2);
            ((AdminFacade)clientFacade).addCustomer(bestCustomer3);

            System.out.println("Successfully added 3 new customer!!!");

            System.out.println("========================================================");

            //-------------------------------- SHOW ALL CUSTOMERS ------------------------------

            System.out.println("========================================================");

            System.out.println("Showing all customers...");

            ((AdminFacade) clientFacade).getAllCustomers().forEach(System.out::println);

            System.out.println("========================================================");

            //----------------------------- UPDATE A CUSTOMER -----------------------------------------

            System.out.println("========================================================");

            System.out.println("Updating a customer...");

            Customer updatingCustomer = ((AdminFacade)clientFacade).getOneCustomer(2);

            System.out.println("The Customer being updated: ");

            System.out.println(((AdminFacade)clientFacade).getOneCustomer(2));

            updatingCustomer.setFirstName("Liron");
            updatingCustomer.setLastName("Brener");
            updatingCustomer.setEmail("vong@never.again");
            updatingCustomer.setPassword("1803_Like_Always");
            updatingCustomer.setId(((AdminFacade)clientFacade).getOneCustomer(2).getId());

            ((AdminFacade) clientFacade).updateCustomer(updatingCustomer);

            System.out.println("========================================================");

            //-------------------------------- SHOW ALL CUSTOMERS AFTER UPDATE ------------------------------

            System.out.println("========================================================");

            System.out.println("Showing all customers after update...");

            ((AdminFacade) clientFacade).getAllCustomers().forEach(System.out::println);

            System.out.println("========================================================");

            //--------------------------- SHOW ONLY 1 CUSTOMER ---------------------------------

            System.out.println("========================================================");

            System.out.println("Showing chosen customer...");

            System.out.println(((AdminFacade) clientFacade).getOneCustomer(1));

            System.out.println("========================================================");

            //------------------------------- DELETE A CUSTOMER ---------------------------------

            System.out.println("========================================================");

            System.out.println("Deleting a customer...");

            System.out.println("The customer being deleted:");

            System.out.println(((AdminFacade)clientFacade).getOneCustomer(2));

            ((AdminFacade) clientFacade).deleteCustomer(2);

            System.out.println("========================================================");

            //----------------------------- SHOW ALL CUSTOMER AFTER DELETING 1 --------------------------------

            System.out.println("========================================================");

            System.out.println("Showing all customer after deleting 1 customer....");

            ((AdminFacade) clientFacade).getAllCustomers().forEach(System.out::println);

            System.out.println("========================================================");

        } catch (Exception error) {
            System.out.println("Something somewhere went terrible wrong in Admin facade");
        }


    }


}
