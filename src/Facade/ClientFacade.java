package Facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import Exceptions.WrongInputException;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public ClientFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
        this.companiesDAO = companiesDAO;
        this.customersDAO = customersDAO;
        this.couponsDAO = couponsDAO;
    }

    protected ClientFacade() {
    }

    public abstract boolean login(String email, String password) throws WrongInputException, SQLException;

}
