package Tests;

import SQL.ConnectionPool;

import Tests.Facade.testAll;
import Thread.CouponExpirationDailyjob;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {

        try {

            CouponExpirationDailyjob couponExpirationDailyjob = new CouponExpirationDailyjob(true);

            Thread myThread = new Thread(couponExpirationDailyjob);

            myThread.start();

            testAll.main();

            myThread.interrupt();

            ConnectionPool.getInstance().closeAllConnection();

        } catch (InterruptedException | SQLException error) {
            System.out.println("Start again?");
        }
    }
}
