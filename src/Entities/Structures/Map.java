package Entities.Structures;

/**
 * Created by marcphillips on 5/22/2014.
 */
public interface Map<K, E> extends Iterates<E>{

    void push(K key, E item);

    E pull(K key);

    E peek(K key);

    boolean containsKey(K key);

    boolean isEmpty();

    Iterable<K> keys();

    Iterable<MapNode<K,E>> values();
}
