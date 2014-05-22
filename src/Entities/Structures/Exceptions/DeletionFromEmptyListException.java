package Entities.Structures.Exceptions;

/**
 * Created by marcphillips on 5/21/2014.
 */
public class DeletionFromEmptyListException extends Exception{
    public DeletionFromEmptyListException(String msg){
        super(msg);
    }

    public DeletionFromEmptyListException(String msg, Throwable throwable){
        super(msg, throwable);
    }

    public DeletionFromEmptyListException(){
        super("No items in list.");
    }

    public DeletionFromEmptyListException(Throwable throwable){
        super("No items in list.", throwable);
    }
}
