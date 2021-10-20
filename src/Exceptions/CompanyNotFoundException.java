package Exceptions;

public class CompanyNotFoundException extends Exception{

    public CompanyNotFoundException() {
        super("Didn't found the company you wanted.");
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
