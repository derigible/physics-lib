package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/20/2014.
 */
public interface Iterates<E> extends Iterable<E>, Iterator<E>{

    abstract void push(E item);

    /**
     * Always grabs the item that is about to be removed from the Iterates
     * object. This allows for the iterates object to create structures
     * like stacks and queues and still make those iterable.
     *
     * @return
     */
    abstract E peek();
}
