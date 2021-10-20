package SQL;

import java.sql.SQLException;

public class DataBaseManager {

    /**
     * Connection to the DB by url, username and password
     */
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";

    /**
     * Create and drop Database
     */
    private static final String CREATE_DB = "CREATE SCHEMA if not exists CouponDB";
    private static final String DROP_DB = "DROP DATABASE CouponDB";

    /**
     * Create Company Table
     */
    private static final String CREATE_TABLE_COMPANY = "CREATE TABLE if not exists `CouponDB`.`COMPANIES` " +
            "(`ID` INT NOT NULL AUTO_INCREMENT," +
            "`NAME` VARCHAR(150) NOT NULL UNIQUE," +
            "`EMAIL` VARCHAR(50) NOT NULL," +
            "`PASSWORD` VARCHAR(50) NOT NULL," +
            "PRIMARY KEY (`ID`));";

    /**
     * Create Customer Table
     */
    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE if not exists `CouponDB`.`CUSTOMERS` " +
            "(`ID` INT NOT NULL  AUTO_INCREMENT," +
            "`FIRST_NAME` VARCHAR(150) NOT NULL," +
            "`LAST_NAME` VARCHAR(150) NOT NULL," +
            "`EMAIL` VARCHAR(150) NOT NULL," +
            "`PASSWORD` VARCHAR(150) NOT NULL," +
            "PRIMARY KEY (`ID`));";

    /**
     * Create Category Table
     */
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE if not exists `CouponDB`.`CATEGORIES` " +
            "(`ID` INT NOT NULL AUTO_INCREMENT," +
            "`NAME` VARCHAR(150) NOT NULL," +
            "PRIMARY KEY (`ID`));";

    /**
     * Create coupons table
     */
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE if not exists `CouponDB`.`COUPONS` " +
            "(`ID` INT NOT NULL AUTO_INCREMENT," +
            "`COMPANY_ID` INT NOT NULL," +
            "`CATEGORY_ID` INT NOT NULL," +
            "`TITLE` VARCHAR(150) NOT NULL," +
            "`DESCRIPTION` VARCHAR(150) NOT NULL," +
            "`START_DATE` DATE NOT NULL," +
            "`END_DATE` DATE NOT NULL," +
            "`AMOUNT` INT NOT NULL," +
            "`PRICE` DOUBLE NOT NULL," +
            "`IMAGE` VARCHAR(150) NOT NULL," +
            "PRIMARY KEY (`ID`)," +
            "FOREIGN KEY (`COMPANY_ID`) REFERENCES `COMPANIES` (`ID`) ON DELETE CASCADE," +
            "FOREIGN KEY (`CATEGORY_ID`) REFERENCES `CATEGORIES` (`ID`) ON DELETE CASCADE);";

    /**
     * Create Customer_vs_coupons table (Customer's purchase storage)
     */
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE if not exists `CouponDB`.`CUSTOMERS_VS_COUPONS`" +
            "(`CUSTOMER_ID` INT NOT NULL," +
            "`COUPON_ID` INT NOT NULL," +
            "PRIMARY KEY (`CUSTOMER_ID`,`COUPON_ID`)," +
            "FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMERS`(`ID`) ON DELETE CASCADE," +
            "FOREIGN KEY (`COUPON_ID`) REFERENCES `COUPONS` (`ID`) ON DELETE CASCADE);";

    /**
     * a method to create the Database
     */
    public static void createDataBase() {
        try {
            DButils.runQuery(CREATE_DB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * A method to create the all tables
     */
    public static void createTables() {
        try {
            DButils.runQuery(CREATE_TABLE_COMPANY);
            DButils.runQuery(CREATE_TABLE_CUSTOMER);
            DButils.runQuery(CREATE_TABLE_CATEGORY);
            DButils.runQuery(CREATE_TABLE_COUPONS);
            DButils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * a method to drop the database
     */
    public static void dropDatabase() {
        try {
            DButils.runQuery(DROP_DB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
