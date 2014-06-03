package Entities.Structures;

/**
 * Created by marcphillips on 5/22/2014.
 */
public interface Map<K extends Comparable<K>, E> extends Iterates<E>{

    /**
     * Add a key value pair onto the Map.
     *
     * @param key the key of the item
     * @param item the item
     */
    void push(K key, E item);

    /**
     * Pull the item off the Map defined by the key and delete
     * that item.
     *
     * @param key the key of the item
     * @return the item
     */
    E pull(K key);

    /**
     * Get the item off the Map defined by the key and do not
     * delete that item.
     *
     * @param key the key of the item
     * @return the item
     */
    E peek(K key);

    /**
     * Check if map contains the given key.
     *
     * @param key the key of the item
     * @return true if item key found
     */
    boolean containsKey(K key);

    /**
     * Check if any items are in the map.
     *
     * @return true if the size of map != 0
     */
    boolean isEmpty();

    /**
     * Return the keys as an iterable list. Note that
     * this list of keys is not connected to the underlying map,
     * and any change to this iterable will not affect the
     * Map in any way. Keys should be returned in order,
     * least to greatest. If the map is empty, return an
     * empty iterable.
     *
     * @return the iterable of keys
     */
    Iterable<K> keys();

    /**
     * Return the key-value pairs of the map as an iterable. This
     * is connected to the Map, and changes made to a MapNode
     * will be reflected in the Map. The iterable returns values
     * in ascending order according to the Node keys.
     *
     * @return the iterable of key-value pairs
     */
    Iterable<MapNode<K,E>> values();
}
