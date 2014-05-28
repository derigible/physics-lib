package Entities.Structures;

/**
 * Created by marcphillips on 5/20/2014.
 */
public interface Pushable<E> extends Iterates<E>{

    /**
     * Push an item onto the data structure.
     *
     * @param item the item to push
     */
    abstract void push(E item);
}
