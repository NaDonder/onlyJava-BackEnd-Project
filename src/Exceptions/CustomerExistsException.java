package Exceptions;

public class CustomerExistsException extends Exception{
    public CustomerExistsException() {
        super("Failed to create a Customer with the same Email," + "\n" +
                " Creating a customer with an existing Email is not allowed.");
    }

    public CustomerExistsException(String message) {
        super(message);
    }
}
