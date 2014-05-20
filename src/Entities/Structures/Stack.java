package Entities.Structures;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Stack<E>{

    private Node<E> lastNode = null;

    public boolean empty(){
        return lastNode == null;
    }

    public E peek(){
        return lastNode == null ? null : lastNode.item;
    }

    public E pop(){
        E i = lastNode.item;
        lastNode = lastNode.prv;
        return i;
    }

    public void push(E item){
        Node<E> i = new Node<E>(lastNode, item);
        lastNode = i;
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
