package Exceptions;

public class CouponOutOfDateException extends Exception{
    public CouponOutOfDateException() {
        super("The coupon is out of date.");
    }

    public CouponOutOfDateException(String message) {
        super(message);
    }
}
