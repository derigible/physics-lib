package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/20/2014.
 * Acts as a List and a Queue, in that you can
 * iterate over all instances withou losing any
 * information, or you can treat it like a Queue,
 * where the pull function takes the first item
 * placed in the list and returns it with removal
 * from the queue.
 */
public class ListQueue<E> implements Pushable<E>{
    private E[] list = (E[]) new Object[10];
    private int head = 0;
    private int tail = 0;
    private int current = 0;
    private double capacity = .25;

    @Override
    public void push(E item){
        if(tail == list.length ||
                ((double)(tail - head)/(double)list.length < capacity
                        && list.length > 10)){
            resizeArray(list.length * 2);
        }
        list[tail++] = item;
    }

    int counted = 0;
    private void resizeArray(int i){
        E[] temp = (E[]) new Object[i];
        for(int j = 0; j < tail - head; j++){
            temp[j] = list[j + head];
            current = j;
        }
        list = temp;
        head = 0;
        tail = current + 1;
        current = head;
//        System.out.println();
//        System.out.println("REsize #: " + ++counted + " Array Length: " + list.length);
//        System.out.println();
    }

    /**
     * Typical queue behavior of pulling the first item
     * from the list and removing it.
     *
     * @return
     */
    public E pull(){
        E val = null;
        if(head == tail){
            head = 0;
            tail = 0;
            current = 0;
            return val;
        } else {
            val = (E) list[head++];
        }
        if((double)(tail - head)/(double)list.length < capacity && list.length > 10){
            resizeArray(Math.max((int)Math.ceil(list.length * capacity), 10));
        }
        current = head;
        return val;
    }

    @Override
    public E peek(){
        return list[head];
    }

    @Override
    public int size(){
        return tail - head;
    }

    @Override
    public Iterator<E> iterator(){
        return this;
    }

    @Override
    public boolean hasNext(){
        boolean result = current != tail;
        if(!result){
            current = head;
        }
        return result;
    }

    @Override
    public E next(){
        return list[current++];
    }

    public static void main(String[] args){
        ListQueue<Integer> ints = new ListQueue<Integer>();
        for(int i = 0; i < 300; i++){
            ints.push(i);
        }
        System.out.println("Length should be 300, found: " + ints.size());
        System.out.println();
        for(int i = 300; i > 0; i--){
            if(i % 20 == 0){
                System.out.println("Length before pull should be " + (i) + ", found: " + ints.size());
                System.out.println("Value before pull should be " + (300 - i) + " got " + ints.peek());
            }
            int val = ints.pull();
            if(i % 20 == 0){
                System.out.println("Length after pull should be " + (i - 1) + ", found: " + ints.size());
                System.out.println("Value of pull should be " + (300 - i) + " got " + val);
                System.out.println("Peek after pull should be " + (300 - i + 1) + " got " + ints.peek());
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Should be null: " + ints.pull());
        System.out.println("Length should be 0, found: " + ints.size());
        for(int i = 0; i < 301; i++){
            ints.push(i);
        }
        System.out.println("Value should be 0, got "+ ints.peek());
        int count = 0;
        for(Integer i : ints){
            if(count %20 == 0){
                System.out.println("Value should be " + count + " got " + i);
            }
            count++;
        }
    }
}
