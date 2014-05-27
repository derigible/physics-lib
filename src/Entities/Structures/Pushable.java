package Entities.Structures;

/**
 * Created by marcphillips on 5/20/2014.
 */
public interface Pushable<E> extends Iterates<E>{

    abstract void push(E item);
}
