package Entities.Structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Bag<E> implements Iterates<E>{

    private Node<E> lastNode = null;
    private Node<E> currentNode = lastNode;

    public void push(E item){
        Node<E> i = new Node<E>(lastNode, item);
        lastNode = i;
        currentNode = lastNode;
    }

    public E peek(){
        return lastNode == null ? null : lastNode.item;
    }

    @Override
    public boolean hasNext(){
        if(currentNode.prv == null){
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
        currentNode = currentNode.prv;
        return currentNode.item;
    }

    @Override
    public Iterator<E> iterator(){
        return this;
    }

    class Node<E>{
        Node<E> prv = null;
        E item = null;

        Node(Node<E> prv, E item){
            this.prv = prv;
            this.item = item;
        }
    }
}
