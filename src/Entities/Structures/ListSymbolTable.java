package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/22/2014.
 * This is an unordered symbol table. The
 * peek and pull function refers to an arbitrary
 * value and should be used as such.
 *
 * NOTE: This map uses Hibbard deletion, meaning
 * that the tree will become unbalanced and
 * performance will degrade with increasing number
 * of deletions.
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
        int cmp = key.compareTo(p.key);
        if(cmp < 0) p.left = push(p.left, key, item);
        else if(cmp > 0) p.right = push(p.right, key, item);
        else p.item = item;

        if(isRed(p.right) && !isRed(p.left)) p = rotateLeft(p);
        if(isRed(p.left) && isRed(p.left.left)) p = rotateRight(p);
        if(isRed(p.left) && isRed(p.right)) flipColors(p);

        return p;
    }

    private boolean isRed(Node node){
        if(node == null) return false;
        return node.color;
    }

    private Node<K,E> rotateLeft(Node<K,E> node){
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node<K,E> rotateRight(Node<K,E> node){
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void flipColors(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    @Override
    public E pull(K key){
        E val = null;
        Node<K,E> x = root;
        Node prv = root;
        while(x != null){
            int comp = key.compareTo(x.key);
            if(comp < 0){
                if(x.left != null){
                    prv = x;
                }
                x = x.left;
            }
            else if (comp > 0){
                if(x.right != null){
                    prv = x;
                }
                x = x.right;
            }
            else{
                val = x.item;
                count--;
                break;
            }
        }
        if(x != null){
            delete(x, prv);
        }
        return val;
    }

    private void delete(Node x, Node prv){
        if(x.key.compareTo(prv.key) == 0){ //at root
            if(x.right != null){
                root = findMin(prv.right, prv);
                root.right = prv.right;
                root.left = prv.left;
                root.color = prv.color;
            } else if(x.left != null){
                root = prv.left;
            } else {
                root = null;
            }
        } else if(x.left == null && x.right == null){
            if(prv.right != null && prv.right.key.compareTo(x.key) == 0){
                prv.right = null;
            } else {
                prv.left = null;
            }
        } else {
            Node y = null;
            if(x.right != null){
                y = findMin(x.right, x);
            } else if(x.left != null){
                if(prv.right != null && prv.right.key.compareTo(x.key) == 0){
                    prv.right = x.left;
                } else {
                    prv.left = x.left;
                }
                return;
            }
            if(prv.right != null && prv.right.key.compareTo(x.key) == 0){
                prv.right = y;
            } else {
                prv.left = y;
            }
            y.left = x.left;
            y.right = x.right;
            y.color = x.color;
        }
    }

    private Node findMin(Node x, Node prv){
        if(x.left == null){
            if(x.right != null){
                prv.left = x.right;
                x.right = null;
            } else if(prv.right !=null && prv.right.key.compareTo(x.key) == 0){ //Is it the min the node on the right?
                prv.right = null;
            } else{
                prv.left = null;
            }
            return x;
        } else {
            return findMin(x.left, x);
        }
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
        return n == null ? null : n.item;
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
        ListMinPriorityQueue<K> keys = new ListMinPriorityQueue<K>();
        if(root == null){
            return keys;
        }
        getNodeKeys(root, keys);
        return keys;
    }

    private void getNodeKeys(Node<K,E> node, ListMinPriorityQueue<K> keys){
        keys.push(node.key);
        if(node.left != null){
            getNodeKeys(node.left, keys);
        }
        if(node.right != null){
            getNodeKeys(node.right, keys);
        }
    }

    @Override
    public Iterable<MapNode<K,E>> values(){
        ListQueue<MapNode<K,E>> nodes = new ListQueue<MapNode<K,E>>();
        for(K key : this.keys()){
            nodes.push(this.getNode(key));
        }
        return nodes;
    }

    @Override
    public int size(){
        return count;
    }

    @Override
    public E peek(){
        return root == null ? null : root.item;
    }

    @Override
    public E pull(){
        if(root == null){
            return null;
        }
        count--;
        Node<K,E> ret = root;
        delete(root, root);
        return ret.item;
    }

    ListStack<E> vals = null;

    @Override
    public Iterator<E> iterator(){
        vals = new ListStack<E>();
        if(root == null){
            return vals;
        }
        getNodeVals(root, vals);
        return vals;
    }

    private void getNodeVals(Node<K,E> node, ListStack<E> keys){
        keys.push(node.item);
        if(node.left != null){
            getNodeVals(node.left, keys);
        }
        if(node.right != null){
            getNodeVals(node.right, keys);
        }
    }

    @Override
    public boolean hasNext(){
        return vals != null ? vals.hasNext() : false;
    }

    @Override
    public E next(){
        return vals != null ? vals.next(): null;
    }

    private class Node<K extends Comparable<K>,E> extends MapNode<K,E>{
        K key;
        E item;
        Node<K,E> right = null;
        Node<K,E> left = null;
        boolean color;

        Node(K k, E v, boolean color){
            key = k; item = v;
            this.color = color;
        }

        @Override
        public E item(){
            return item;
        }

        @Override
        public K key(){
            return key;
        }
    }

    public static void main(String[] args){
        ListSymbolTable<String, String> strings = new ListSymbolTable<String, String>();
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
