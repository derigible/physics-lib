package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/29/2014.
 */
public class ListHashTable <K extends Comparable<K>, E> implements Map<K,E>{

    private int buckets = 97;
    private int inserts = 0;
    private Node[] table = new Node[buckets];
    private ListStack<Integer> pointers = new ListStack<Integer>();

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
        Node[] temp = new Node[i];
        for(int j = 0; j < table.length; j++){
            int hash = hash((K)table[j].key);
            temp[hash] = table[j];
            pointers.push(hash);
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
        if(table[h] == null){
            pointers.push(h);
        }
        table[h] = new Node(key, item, table[h]);
        inserts++;
    }

    @Override
    public E pull(K key){
        int h = hash(key);
        E val = null;
        Node prv = null;
        for(Node x = table[h]; x != null; x = x.next){
            if(key.equals(x.key)) {
                val = (E) x.val;
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
                    pointers.remove(h);
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
        for(Node x = table[h]; x != null; x = x.next){
            if(key.equals(x.key)) {
                val = (E) x.val;
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

    @Override
    public Iterable<K> keys(){
        ListStack<K> keys = new ListStack<K>();
        for(Integer i : pointers){
            Node x;
            for(x = table[i]; x != null; x = x.next){
                keys.push((K)x.key);
            }
        }
        return keys;
    }

    @Override
    public Iterable<MapNode<K, E>> values(){
        ListQueue<MapNode<K,E>> nodes = new ListQueue<MapNode<K,E>>();
        for(Integer i : pointers){
            Node x;
            for(x = table[i]; x != null; x = x.next){
                nodes.push(x);
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
        return (E) table[pointers.peek()].val;
    }

    @Override
    public E pull(){
        if(pointers.size() == 0){
            return null;
        }
        E val = (E) table[pointers.peek()].val;
        if(table[pointers.peek()].next != null){
            table[pointers.peek()] = table[pointers.peek()].next;
        } else{
            table[pointers.peek()] = null;
        }
        resizeArray();
        inserts--;
        return val;
    }

    @Override
    public void remove(E item){
        if(pointers.size() == 0){
            return;
        }
        for(Integer i : pointers){
            Node x;
            Node prv = null;
            for(x = table[i]; x != null; x = x.next){
                if(x.val.equals(item)){
                    if(prv != null){
                        if(x.next != null){
                            prv.next = x.next;
                        } else {
                            prv.next = null;
                        }
                    } else if(x.next != null){
                        table[i] = x.next;
                    } else {
                        table[i] = null;
                        pointers.remove(i);
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
        return this;
    }

    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    public E next(){
        return null;
    }

    private static class Node<K,E> extends MapNode<K,E>{
        private K key;
        private E val;
        private Node next;

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

}
