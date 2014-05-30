package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/29/2014.
 */
public class ListHashTable <K extends Comparable<K>, E> implements Map<K,E>{

    private int buckets = 97;
    private int inserts = 0;
    private Node<K,E>[] table = (Node<K,E>[]) new Node[buckets];
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
        return table[pointers.peek()].val;
    }

    @Override
    public E pull(){
        if(pointers.size() == 0){
            return null;
        }
        E val = table[pointers.peek()].val;
        if(table[pointers.peek()].next != null){
            table[pointers.peek()] = table[pointers.peek()].next;
        } else{
            table[pointers.peek()] = null;
            pointers.pull();
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

    private Node<K,E> currentNode;
    @Override
    public Iterator<E> iterator(){
        int i = 0;
        if(pointers.hasNext()){
            i = pointers.next();
        }
        currentNode = table[i];
        return this;
    }

    @Override
    public boolean hasNext(){
        return currentNode.next != null ? true : pointers.hasNext();
    }

    @Override
    public E next(){
        E ret = null;
        if(currentNode.next != null){
            currentNode = currentNode.next;
            ret =  currentNode.item();
        } else if(pointers.hasNext()){
            currentNode = table[pointers.next()];
            ret = currentNode.item();
        }
        return ret;
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

    public static void main(String[] args){
        ListHashTable<String, String> strings = new ListHashTable<String, String>();
        strings.push("one", "test1");
        System.out.println(strings.pull());
        System.out.println(strings.pull());
        System.out.println(strings.size());
        for(int i =0; i < 20; i++){
            strings.push("string" + i, "test " + i);
        }
        System.out.println();
        System.out.println("Expected size: 20, got: " +strings.size());
        System.out.println();
        System.out.println(strings.pull("string10"));
        System.out.println("Expected size: 19, got: " +strings.size());
        System.out.println(strings.pull("string10"));
        System.out.println(strings.pull("string15"));
        System.out.println(strings.pull("string15"));
        System.out.println("Expected value: test 16 Result: "+ strings.peek());
        System.out.println(strings.pull("string16"));
        System.out.println("Expected value: test 17 Result: "+ strings.peek());
        System.out.println(strings.pull());
        System.out.println("Expected value: test 18 Result: "+ strings.peek());
        System.out.println(strings.pull("string7"));
        System.out.println(strings.pull("string4"));
        System.out.println(strings.pull("string8"));
        System.out.println(strings.pull("string9"));
        strings.push("hi", "testwhatever");
        strings.push("hi2", "testwhatever1");

        System.out.println(strings.pull("string5"));
        System.out.println(strings.pull("string6"));
        System.out.println(strings.pull("string13"));
        System.out.println(strings.pull("string12"));
        strings.push("hi3", "testwhatever2");
        strings.push("hi4", "testwhatever3");
        strings.push("hi5", "testwhatever4");
        System.out.println(strings.pull("string14"));
        System.out.println(strings.pull("string1"));
        System.out.println(strings.pull("string11"));
        System.out.println();
        for(String s : strings){
            System.out.println(s);
        }
        System.out.println();
        int s = strings.size();
        for(int i = 0; i < s; i++){
            System.out.println(strings.pull());
        }
        System.out.println("Size should be zero: " + strings.size());
        System.out.println("Empty? : " + strings.isEmpty());
    }

}
