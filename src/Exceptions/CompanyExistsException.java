package Exceptions;

public class CompanyExistsException extends Exception{

    public CompanyExistsException() {
        super("Failed to create a Company with the same Email or Name," + "\n" +
                " Creating a company with an existing name is not allowed.");
    }

    public CompanyExistsException(String message) {
        super(message);
    }
}
