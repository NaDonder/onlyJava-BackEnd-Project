package Exceptions;

public class WrongInputException extends Exception{

    public WrongInputException() {
        super("Invalid input inserted, please try again");
    }

    public WrongInputException(String message) {
        super(message);
    }
}
