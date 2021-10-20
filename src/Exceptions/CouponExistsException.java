package Exceptions;

public class CouponExistsException extends Exception{

    public CouponExistsException() {
        super("You cannot add new Coupon with the Same name for the same company," +
                "you can add same name of coupon for different Company");
    }

    public CouponExistsException(String message) {
        super(message);
    }
}
