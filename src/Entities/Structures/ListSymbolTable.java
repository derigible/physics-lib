package Entities.Structures;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by marcphillips on 5/22/2014.
 * This is an unordered symbol table. The
 * peek and pull function refers to an arbitrary
 * value and should be used as such.
 */
public class ListSymbolTable<K extends Comparable<K>, E> implements Map<K,E>{

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private int count = 0;
    Node<K,E> root = null;

    public ListSymbolTable(){
    }

    @Override
    public void push(K key, E item){
        root = push(root, key, item);
        count++;
    }

    private Node<K,E> push(Node<K,E> p, K key, E item){
        if(p == null) return new Node<K, E>(key, item, RED);
        int cmp = p.key.compareTo(key);
        if(cmp < 0) p.left = push(p.left, key, item);
        if(cmp > 0) p.right = push(p.right, key, item);
        else p.val = item;

        if(isRed(p.left) && isRed(p.right)) flipColors(p);
        if(isRed(p.left) && isRed(p.left.left)) p = rotateRight(p);
        if(isRed(p.right) && !isRed(p.left)) rotateLeft(p);

        return null;
    }

    private boolean isRed(Node node){
        if(node == null) return false;
        return node.red;
    }

    private Node<K,E> rotateLeft(Node<K,E> node){
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.red = node.red;
        node.red = RED;
        return x;
    }

    private Node<K,E> rotateRight(Node<K,E> node){
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.red = node.red;
        node.red = RED;
        return x;
    }

    private void flipColors(Node node){
        node.red = RED;
        node.left.red = BLACK;
        node.right.red = BLACK;
    }

    @Override
    public E pull(K key){
        count--;
        E val = null;
        Node<K,E> x = root;
        while(x != null){
            int comp = key.compareTo(x.key);
            if(comp < 0) x = x.left;
            else if (comp > 0) x = x.right;
            else val = x.val;
        }
        if(x != null){
            delete(x);
        }
        return val;
    }

    private void delete(Node x){
new TreeMap<String, String>();
    }

    private Node<K,E> getNode(K key){
        Node<K,E> x = root;
        while(x != null){
            int comp = key.compareTo(x.key);
            if(comp < 0) x = x.left;
            else if (comp > 0) x = x.right;
            else return x;
        }
        return null;
    }

    @Override
    public E peek(K key){
        Node<K,E> n = getNode(key);
        return n == null ? null : n.val;
    }

    @Override
    public boolean containsKey(K key){
        return getNode(key) != null;
    }

    @Override
    public boolean isEmpty(){
        return root == null;
    }

    @Override
    public Iterable<K> keys(){
        return null;
    }

    @Override
    public Iterable<MapNode<K, E>> values(){
        return null;
    }

    @Override
    public int size(){
        return count;
    }

    @Override
    public E peek(){
        return root == null ? null : root.val;
    }

    @Override
    public E pull(){
        return null;
    }

    @Override
    public Iterator<E> iterator(){
        return null;
    }

    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    public E next(){
        return null;
    }

    private class Node<K extends Comparable<K>,E>{
        K key;
        E val;
        Node<K,E> right = null;
        Node<K,E> left = null;
        boolean red;

        Node(K k, E v, boolean red){
            key = k; val = v;
            this.red = red;
        }

    }

}
