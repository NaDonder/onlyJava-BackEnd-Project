package DAO;

import Beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {

    /**
     * DAO - Data Access Object
     * @param email Email to check if the company exists
     * @return returns true/false if the company exists
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Boolean isCompanyExists(String email) throws SQLException; // does exists?

    /**
     * DAO - Data Access Object
     * @param company Adding company to the SQL server table
     * @return returns true/false if we added the company or not
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean addCompany(Company company) throws SQLException; // add company

    /**
     * DAO - Data Access Object
     * @param company Updating a company to the SQL server table
     * @return returns true/false if we could update the company or not
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    boolean updateCompany(Company company) throws SQLException; // update company

    /**
     * DAO - Data Access Object
     * @param companyID companyID needs to match SQL server table companyID
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    void deleteCompany(int companyID) throws SQLException; // delete company

    /**
     * DAO - Data Access Object
     * @return returns all the companies in the SQL server table
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    ArrayList<Company> getAllCompanies() throws SQLException; // get to show ALL companies

    /**
     * DAO - Data Access Object
     * @param companyID for getting a single Company from the SQL server table
     * @return the company details
     * @throws SQLException if the info inserted doesn't match the SQL (or any other SQL error)
     */
    Company getOneCompany(int companyID) throws SQLException; // get to show ONE company
}
