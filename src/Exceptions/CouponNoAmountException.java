package Exceptions;

public class CouponNoAmountException extends Exception{
    public CouponNoAmountException() {
        super("The amount left from the coupon is: 0, you cannot buy more of the coupon");
    }

    public CouponNoAmountException(String message) {
        super(message);
    }
}
