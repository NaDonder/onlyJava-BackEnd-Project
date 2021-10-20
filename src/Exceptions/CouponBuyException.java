package Exceptions;

public class CouponBuyException extends Exception{

    public CouponBuyException() {
        super("You cannot buy the same coupon twice!");
    }

    public CouponBuyException(String message) {
        super(message);
    }
}
