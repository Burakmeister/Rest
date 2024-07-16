package pl.projekt.projekt_rest.exception;

public class NotEnoughParamsException extends Exception{
    public NotEnoughParamsException(String message) {
        super(message);
    }
}
