package exception;

public class LeaseNotFoundException extends Exception {

    // Constructor that accepts only a message
    public LeaseNotFoundException(String message) {
        super(message);
    }

   
    public LeaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
