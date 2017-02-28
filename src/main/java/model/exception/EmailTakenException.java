package model.exception;

/**
 * Created by adam on 28/02/2017.
 */
public class EmailTakenException extends Exception{
    public EmailTakenException() {
        super("E-mail is taken by somebody else.");
    }
}
