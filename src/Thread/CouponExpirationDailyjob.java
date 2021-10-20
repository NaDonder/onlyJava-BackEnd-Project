package Thread;

import DBDAO.CouponDBDAO;

import java.sql.SQLException;

public class CouponExpirationDailyjob implements Runnable {

    private boolean startTheJob;

    CouponDBDAO couponDBDAO = new CouponDBDAO();


    public CouponExpirationDailyjob(boolean startTheJob) {
        this.startTheJob = startTheJob;
    }

    public boolean isStartTheJob() {
        return startTheJob;
    }

    public void setStartTheJob(boolean startTheJob) {
        this.startTheJob = startTheJob;
    }

    /**
     * a thread to delete expired coupons
     * we are using a SQL statement method to delete expired coupons
     * we also have a java code to delete the expired coupons
     * the thread will sleep 1 day and will check again if the date today is bigger that the end date
     */
    @Override
    public void run() {
        while (startTheJob) {
            try {
                //instead of iterator , create a simple sql statement which delete the expired coupon that are before the current date
                /*
                ArrayList<Coupon> removeCoupon = null;
                removeCoupon = couponDBDAO.getAllCouponsByID();
                System.out.println("Did it go inside?");
                Iterator<Coupon> it = removeCoupon.iterator();
                if (it != null) {
                    System.out.println("So its not null");
                    while (it.hasNext()) {
                        Coupon currentCoupon = it.next();
                        if (currentCoupon.getStartDate().after(currentCoupon.getEndDate())) {
                            System.out.println(currentCoupon.getId());
                            couponDBDAO.deleteCoupon(currentCoupon.getId());
                            System.out.println("OMG HOW WE DIDNT NOTICE THAT? I JUST DELETED A EXPIRED COUPON");
                        }
                    }
                }

                 */
                couponDBDAO.deleteCouponJob();
                Thread.sleep( 1000 * 60 * 60 * 24); //1000millis(=1sec) * 60(=1min) * 60(=1hour) *24(=1day)
            } catch (InterruptedException | SQLException error) {
                System.out.println("Expired Coupon thread has been stopped");
                startTheJob = false;
            }
        }
    }
}
