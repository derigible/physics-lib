package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/29/2014.
 */
public class ListHashTable <K extends Comparable<K>, E> implements Map<K,E>{

    private int buckets = 97;
    private int inserts = 0;
    private Node<K,E>[] table = (Node<K,E>[]) new Node[buckets];

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % buckets;
    }

    private void resizeArray(){
        int i;
        if(buckets > 97 && buckets > inserts/5){
            i = inserts/5;
        } else if(buckets > 97 && buckets < inserts/5 + 20){
            i = inserts/5 - 20;
        } else {
            return;
        }
        buckets = i;
        Node<K,E>[] temp = (Node<K,E>[])new Node[i];
        for(int j = 0; j < table.length; j++){
            int hash = hash(table[j].key);
            temp[hash] = table[j];
        }
        this.table = temp;
    }

    @Override
    public void push(K key, E item){
        resizeArray();
        int h = hash(key);
        for(Node x = table[h]; x != null; x = x.next){
            if(key.equals(x.key)) {
                x.val = item;
                return;
            }
        }
        table[h] = new Node<K,E>(key, item, table[h]);
        inserts++;
    }

    @Override
    public E pull(K key){
        int h = hash(key);
        E val = null;
        Node prv = null;
        for(Node<K,E> x = table[h]; x != null; x = x.next){
            if(key.equals(x.key)) {
                val = x.val;
                if(prv != null){
                    if(x.next ==null){
                        prv.next = null;
                    } else {
                        prv.next = x.next;
                    }
                } else if(x.next != null) {
                    table[h] = x.next;
                } else {
                    table[h] = null;
                }
                inserts--;
                resizeArray();
                break;
            }
            prv = x;
        }
        return val;
    }

    @Override
    public E peek(K key){
        int h = hash(key);
        E val = null;
        for(Node<K,E> x = table[h]; x != null; x = x.next){
            if(key.equals(x.key)) {
                val = x.val;
                break;
            }
        }
        return val;
    }

    @Override
    public boolean containsKey(K key){
        return peek(key) != null;
    }

    @Override
    public boolean isEmpty(){
        return inserts > 0;
    }

    int mods = 0;
    ListStack<K> keys = null;
    @Override
    public Iterable<K> keys(){
        if(mods == inserts && keys != null) {return keys;}
        mods = inserts;
        populateKeys();
        return keys;
    }

    private void populateKeys(){
        keys = new ListStack<K>();
        for(Node<K,E> i : table){
            Node<K,E> x;
            for(x = i; x != null; x = x.next){
                keys.push(x.key);
            }
        }
    }

    @Override
    public Iterable<MapNode<K, E>> values(){
        if(mods != inserts || keys == null){
            mods = inserts;
            populateKeys();
        }
        ListMinPriorityQueue<MapNode<K,E>> nodes;
        nodes = new ListMinPriorityQueue<MapNode<K,E>>();
        for(K i : keys){
            int h = hash(i);
            for(Node<K, E> x = table[h]; x != null; x = x.next){
                if(i.equals(x.key)){
                    nodes.push(x);
                    break;
                }
            }
        }
        return nodes;
    }

    @Override
    public int size(){
        return inserts;
    }

    @Override
    public E peek(){
        return peek(keys.peek());
    }

    @Override
    public E pull(){
        if(mods != inserts || keys == null){
            mods = inserts;
            populateKeys();
        }
        return keys.peek() != null ? pull(keys.pull()) : null;
    }

    @Override
    public void remove(E item){
        if(inserts == 0){
            return;
        }
        if(mods != inserts || keys == null){
            mods = inserts;
            populateKeys();
        }
        for(K i : keys){
            Node x;
            Node prv = null;
            int h = hash(i);
            for(x = table[h]; x != null; x = x.next){
                if(x.val.equals(item)){
                    if(prv != null){
                        if(x.next != null){
                            prv.next = x.next;
                        } else {
                            prv.next = null;
                        }
                    } else if(x.next != null){
                        table[h] = x.next;
                    } else {
                        table[h] = null;
                    }
                    inserts--;
                    resizeArray();
                    return;
                }
                prv = x;
            }
        }
    }

    @Override
    public Iterator<E> iterator(){
        if(mods != inserts || keys == null){
            mods = inserts;
            populateKeys();
        }
        return this;
    }

    @Override
    public boolean hasNext(){
        return inserts != 0 ? keys.hasNext() : false;
    }

    @Override
    public E next(){
        return peek(keys.next());
    }

    private static class Node<K extends Comparable<K>,E> extends MapNode<K,E>{
        K key;
        E val;
        Node next = null;

        Node(K k, E v, Node n){
            key = k;
            val = v;
            next = n;
        }

        @Override
        public E item(){
            return val;
        }

        @Override
        public K key(){
            return key;
        }
    }

    public static void main(String[] args){
        ListHashTable<String, String> strings = new ListHashTable<String, String>();

        for(int i = 0; i < 10; i++){
            strings.push("string" + i, "test " + i);
        }
        strings.push("hi3", "testwhatever2");
        strings.push("hi4", "testwhatever3");
        strings.push("hi5", "testwhatever4");
        strings.push("hi", "testwhatever");
        strings.push("hi2", "testwhatever1");
        System.out.println("Values: ");
        for(String s : strings){
            System.out.println(s);
        }
        System.out.println();
        System.out.println("Keys: ");
        for(String s : strings.keys()){
            System.out.println(s);
        }
        System.out.println();
        strings =  new ListHashTable<String, String>();
        strings.push("one", "test1");
        System.out.println("Expected: test1, Result: " + strings.pull());
        System.out.println("Expected: null, Result: " +strings.pull());
        System.out.println("Expected: 0, Result: " +strings.size());
        for(int i =0; i < 20; i++){
            strings.push("string" + i, "test " + i);
        }
        System.out.println();
        System.out.println("Expected size: 20, got: " +strings.size());
        System.out.println();
        System.out.println("Expected: test 10, Result: " + strings.pull("string10"));
        System.out.println("Expected size: 19, got: " +strings.size());
        System.out.println("Expected: null, Result: " +strings.pull("string10"));
        System.out.println("Expected: test 15, Result: " +strings.pull("string15"));
        System.out.println("Expected: null, Result: " +strings.pull("string15"));
        System.out.println("Expected: test 16, Result: " +strings.pull("string16"));
        System.out.println("Expected: ? - not null , Result: " +strings.pull());
        System.out.println("Expected: test 7, Result: " +strings.pull("string7"));
        System.out.println("Expected: test 4, Result: " +strings.pull("string4"));
        System.out.println("Expected: test 8, Result: " +strings.pull("string8"));
        System.out.println("Expected: test 9, Result: " +strings.pull("string9"));
        strings.push("hi", "testwhatever");
        System.out.println("Added testwhatever");
        System.out.println("Added testwhatever1");
        strings.push("hi2", "testwhatever1");

        System.out.println("Expected: test 5, Result: " +strings.pull("string5"));
        System.out.println("Expected: test 6, Result: " +strings.pull("string6"));
        System.out.println("Expected: test 13, Result: " +strings.pull("string13"));
        System.out.println("Expected: test 12, Result: " +strings.pull("string12"));
        System.out.println("Added testwhatever2");
        System.out.println("Added testwhatever3");
        System.out.println("Added testwhatever4");
        strings.push("hi3", "testwhatever2");
        strings.push("hi4", "testwhatever3");
        strings.push("hi5", "testwhatever4");
        System.out.println("Expected: test 18, Result: " +strings.pull("string18"));
        System.out.println("Expected: test 1, Result: " +strings.pull("string1"));
        System.out.println("Expected: test 11, Result: " +strings.pull("string11"));
        System.out.println();
        System.out.println("Remaining: ");
        for(String s : strings){
            System.out.println(s);
        }
        System.out.println();
        int s = strings.size();
        System.out.println("Remaining values being pulled: ");
        for(int i = 0; i < s; i++){
            System.out.println(strings.pull());
        }
        System.out.println("Size should be zero: " + strings.size());
        System.out.println("Should be empty: " + strings.isEmpty());
    }

}
