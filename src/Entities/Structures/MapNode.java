package Entities.Structures;

/**
 * Created by marcphillips on 5/22/2014.
 * A simple container class for a map.
 */
public class MapNode<K, E>{
    private E item;
    private K key;

    public MapNode(K key, E item){
        this.item = item;
        this.key = key;
    }
}
