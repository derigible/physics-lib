package Entities.Structures;

import Entities.Structures.Exceptions.DeletionFromEmptyListException;

/**
 * Created by marcphillips on 5/21/2014.
 */
public class ListMinPriorityQueue<E extends Comparable<E>> extends ListPriorityQueue<E>{

    public ListMinPriorityQueue(){
        list = (E[]) new Comparable[10];
    }

    public ListMinPriorityQueue(int capacity){
        list = (E[]) new Comparable[capacity + 1];
    }

    public ListMinPriorityQueue(E[] given){
        list = given;
    }

    @Override
    void swim(int key){
        while(key > 1 && greater(key / 2, key)){
            exch(key, key / 2);
            key = key / 2;
        }
    }

    @Override
    void sink(int key){
        while(2*key <= last){
            int j = 2*key;
            if(j < last && greater(j, j + 1)){
                j++;
            }
            if(!greater(key, j)){
                break;
            }
            exch(key, j);
            key = j;
        }
    }

    private boolean greater(int i, int key){
        return list[i].compareTo(list[key]) > 0;
    }

    public static void main(String[] args){
        ListMinPriorityQueue<Integer> ints = new ListMinPriorityQueue<Integer>(50);
        for(int i = 0; i < 300; i++){
            ints.push(i);
        }
        System.out.println("Length should be 300, found: " + ints.size());
        System.out.println();
        for(int i = 300; i > 0; i--){
            if(i % 20 == 0){
                System.out.println("Length before deleteMax should be " + (i) + ", found: " + ints.size());
                System.out.println("Value before DeleteMax should be " + (300 - i) + " got " + ints.peek());
            }
            int val = 0;
            val = ints.pull();
            if(i % 20 == 0){
                System.out.println("Length after pull should be " + (i - 1) + ", found: " + ints.size());
                System.out.println("Value of pull should be " + (300 - i) + " got " + val);
                System.out.println("Peek after pull should be " + (300 - i + 1) + " got " + ints.peek());
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Should be true: " + ints.isEmpty());
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