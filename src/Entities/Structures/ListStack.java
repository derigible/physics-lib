package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/20/2014.
 * Note that this class is a hybrid of sorts, allowing for a
 * FIFO approach to the data (which does not change the data
 * in any way) and a LIFO approach (which will delete the
 * first instance off the top of the list). Thus it is a simple
 * List and Stack in one.
 */
public class ListStack<E> implements Pushable<E>{
    private E[] list = (E[]) new Object[10];
    private int last = -1;
    private int current = 0;
    private double capacity = .25;

    /**
     * Add a new item to the top of the list.
     *
     * @param item the item to add
     */
    public void push(E item){
        if(list.length == last + 1){
            E[] temp = (E[]) new Object[list.length * 2];
            for(int i = 0; i < list.length; i++){
                temp[i] = list[i];
            }
            list = temp;
        }
        list[++last] = item;
    }

    /**
     * Pop the first item from the top of the list. Removes the
     * item.
     *
     * @return the top item
     */
    @Override
    public E pull(){
        if(last == -1){
            return null;
        }
        E i = list[last--];
        if((double)last /(double) (list.length-1) < capacity && list.length > 10){
            shrinkArray();
        }
        return i;
    }

    @Override
    public void remove(E item){
        if(last == -1){
            return;
        }
        for(int i = 0; i < last; i++){
            if(list[i].equals(item)){
                for(int j = i; j < last; j++){
                    list[j] = list[j +1];
                }
                list[last--] = null;
                return;
            }
        }
    }

    private void shrinkArray(){
        E[] temp = (E[]) new Object[(int)Math.ceil(list.length * capacity)];
        for(int i = 0; i <= last; i++){
            temp[i] = list[i];
        }
        list = temp;
    }

    /**
     * Retrieve the top item of the list without
     * removing the item.
     *
     * @return the last item of the list
     */
    public E peek(){
        return last != -1 ? list[last] : null;
    }


    /**
     * The size of the list.
     *
     * @return the size
     */
    public int size(){
        return last + 1;
    }

    @Override
    public Iterator<E> iterator(){
        return this;
    }

    @Override
    public boolean hasNext(){
        boolean result = current <= last;
        if(!result){
            current = 0;
        }
        return result;
    }

    @Override
    public E next(){
        return current > last ? null : list[current++];
    }

    public static void main(String[] args){
        ListStack<Integer> ints = new ListStack<Integer>();
        for(Integer i : ints){
            System.out.println(i);
        }

        ints = new ListStack<Integer>();
        ints.push(60);
        ints.push(6);
        ints.push(62);
        ints.push(63);
        ints.push(64);
        ints.push(65);
        ints.push(66);
//        ints.push(45);
//        ints.push(75);
//        ints.push(55);
//        ints.push(34);
        ints.push(67);
        ints.push(68);
        ints.push(69);
        ints.push(93);
        ints.push(94);
        ints.push(95);
        ints.push(96);
        ints.push(0);
        ints.push(1);
        ints.push(2);
        ints.push(3);
        ints.push(4);
        ints.remove(64);
        System.out.println();
        for(Integer i : ints){
            System.out.println("Val:" +i);
        }

//        for(int i = 0; i < 300; i++){
//            ints.push(i);
//        }
//        System.out.println("Length should be 300, found: " + ints.size());
//        System.out.println();
//        for(int i = 300; i > 0; i-- ){
//            if(i % 20 == 0){
//                System.out.println("Length before pop should be " + (i) + ", found: " + ints.size());
//                System.out.println("Value before pop should be " + (i - 1) + " got " + ints.peek());
//            }
//            int val = ints.pull();
//            if(i % 20 == 0){
//                System.out.println("Value of pop should be " + (i - 1) + " got " + val);
//                System.out.println("Length after pop should be " + (i - 1) + ", found: " + ints.size());
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println("Should be null: " + ints.pull());
//        System.out.println("Length should be 0, found: " + ints.size());
//        for(int i = 0; i < 301; i++){
//            ints.push(i);
//        }
//        System.out.println("Value should be 300, got "+ ints.peek());
//        int count = 0;
//        for(Integer i : ints){
//            if(count %20 == 0){
//                System.out.println("Value should be " + count + " got " + i);
//            }
//            count++;
//        }
    }
}
