package Entities.Structures;

/**
 * Created by marcphillips on 5/22/2014.
 * A simple container class for a map.
 */
public abstract class MapNode<K extends Comparable<K>, E> implements Comparable<MapNode<K,E>>{
    private E item;
    private K key;

    public MapNode(){
        //Empty Constructor
    }

    public MapNode(K key, E item){
        this.item = item;
        this.key = key;
    }

    public abstract E item();

    public abstract K key();

    public int compareTo(MapNode<K,E> key){
        return this.key.compareTo(key.key);
    }
}
