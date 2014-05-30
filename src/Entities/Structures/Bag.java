package Entities.Structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Bag<E> implements Pushable<E>{

    private Node<E> lastNode = null;
    private Node<E> currentNode = lastNode;
    private int size = 0;

    public void push(E item){
        lastNode = new Node<E>(lastNode, item);
        currentNode = lastNode;
        size++;
    }

    @Override
    public int size(){
        return size;
    }

    public E peek(){
        return lastNode == null ? null : lastNode.item;
    }

    @Override
    public E pull(){
        Node<E> temp = lastNode;
        lastNode = lastNode.next;
        size--;
        return temp.item;
    }

    @Override
    public void remove(E item){
        Node<E> node = lastNode;
        Node<E> prv = null;
        while(node != null){
            if(node.item.equals(item)){
                if(node.next != null){
                    if(prv != null){
                        prv.next = node.next;
                    } else {
                        lastNode = node.next;
                    }
                } else {
                    if(prv != null){
                        prv.next = null;
                    } else {
                        lastNode = null;
                    }
                }
                currentNode = lastNode;
                size--;
                return;
            } else {
                prv = node;
                node = node.next;
            }
        }
    }

    @Override
    public boolean hasNext(){
        if(currentNode == null){
            currentNode = lastNode;
            return false;
        }
        return true;
    }

    @Override
    public E next(){
        if(!hasNext()){
            throw new NoSuchElementException("No More elements.");
        }
        Node<E> n = currentNode;
        currentNode = currentNode.next;
        return n.item;
    }

    @Override
    public Iterator<E> iterator(){
        return this;
    }

    class Node<E>{
        Node<E> next = null;
        E item = null;

        Node(Node<E> prv, E item){
            this.next = prv;
            this.item = item;
        }
    }

    public static void main(String[] args){
        Bag<String> b = new Bag<String>();
        for(int i = 0; i < 10; i++){
            b.push("Test"+i);
        }
        System.out.println(b.size());
        for(String s : b){
            System.out.println(s);
        }
        b.remove("Test0");
        System.out.println(b.size());
        for(String s : b){
            System.out.println(s);
        }
        b.remove("Test5");
        System.out.println(b.size());
        for(String s : b){
            System.out.println(s);
        }
        b.push("Test5");
        b.push("Test5");
        b.push("Test5");
        System.out.println(b.size());
        for(String s : b){
            System.out.println(s);
        }
        b.remove("Test5");
        System.out.println(b.size());
        for(String s : b){
            System.out.println(s);
        }
    }
}
