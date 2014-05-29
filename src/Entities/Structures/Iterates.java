package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/20/2014.
 */
public interface Iterates<E> extends Iterable<E>, Iterator<E>{

    abstract int size();

    //TODO consider making a setminsize and setcapacity function

    /**
     * Always grabs the item that is about to be removed from the Iterates
     * object. This allows for the iterates object to create structures
     * like stacks and queues and still make those iterable.
     *
     * @return
     */
    abstract E peek();

    /**
     * Get the next data in the structure and delete the data.
     *
     * @return the next data
     */
    abstract E pull();

    /**
     * Remove the specified item. Fails silently if item not
     * found.
     *
     * @param item the item to remove
     */
    abstract void remove(E item);
}
