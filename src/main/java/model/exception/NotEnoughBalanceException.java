package model.exception;

/**
 * Created by adam on 05/03/2017.
 */
public class NotEnoughBalanceException extends Exception{
    public NotEnoughBalanceException() {
        super("Not enough balance on our account");
    }
}
