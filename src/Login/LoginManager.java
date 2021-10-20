package Login;

import Beans.Customer;
import Exceptions.WrongInputException;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import Enum.ClientType;

public class LoginManager {
    /**
     * Create the instance for the singleton class
     */
    private static LoginManager instance = null;  // SingleTon

    /**
     * private constructor for the singleton class, you can only show this class once
     * @throws Exception if you couldn't log into the system
     */
    private LoginManager() throws Exception {  // private constructor for SingleTon Class
    }

    /**
     * pure singleton
     * @return if no connection exists it will get us the LoginManager class
     * @throws Exception if any problem appeared
     */
    public static LoginManager getInstance() throws Exception {
        if (instance == null) {  // first check
            synchronized (LoginManager.class) {  // class synchronized for protection
                if (instance == null) {  // DOUBLE CHECK
                    instance = new LoginManager();  // if all "null" create new LoginManager
                }
            }
        }
        return instance;
    }

    /**
     * a method for the login: Admin, Company, Customer.
     * @param email Email for the login
     * @param password Password for the login
     * @param clientType the client type tring to log in.
     * @return the Facade if the email and the password were the right one.
     * @throws Exception if any problem appeared
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws Exception {


        switch (clientType){
            // ---------------- Administrator Case --------------------
            case Administrator:
                AdminFacade adminFacade = new AdminFacade();
                if (!adminFacade.login(email, password)) {
                    adminFacade = null;
                    throw new WrongInputException();
                } else {
                    return adminFacade;
                }


            // ------------------ Company Case ------------------------
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                if (!companyFacade.login(email, password)) {
                    companyFacade = null;
                    throw new WrongInputException();
                } else {
                    return companyFacade;
                }


            // ----------------- Customer Case ------------------------
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade();
                if (!customerFacade.login(email, password)) {
                    customerFacade = null;
                    throw new WrongInputException();
                } else {
                    return customerFacade;
                }

            // ------- default case -------
            default:
                System.out.println("How did you get here?");
                return null;
        }
    }
}
