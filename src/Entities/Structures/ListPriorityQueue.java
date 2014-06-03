package Entities.Structures;

import java.util.Iterator;

/**
 * Created by marcphillips on 5/21/2014.
 */

public class ListPriorityQueue<E extends Comparable<E>> implements Pushable<E>{

    protected E[] list = null;
    protected E[] sorted = null;
    protected int last = 0;
    protected int current = 1;

    public ListPriorityQueue(){
        list = (E[]) new Comparable[10];
    }

    public ListPriorityQueue(int capacity){
        list = (E[]) new Comparable[capacity + 1];
    }

    public ListPriorityQueue(E[] given){
        list = given;
    }

    @Override
    public E pull(){
        if(last == 0){
            return null;
        }
        E max = list[1];
        exch(1, last--);
        sink(1);
        list[last + 1] = null;
        return max;
    }

    @Override
    public void remove(E item){
        for(int i = 1; i <= last; i++){
            if(item.compareTo(list[i]) == 0){
                exch(i,last--);
                sink(i);
                return;
            }
        }
    }

    void swim(int key){
        while(key > 1 && less(key / 2, key)){
            exch(key, key / 2);
            key = key / 2;
        }
    }

    void exch(int key, int i){
        E temp = list[i];
        list[i] = list[key];
        list[key] = temp;
    }

    private boolean less(int i, int key){
        return list[i].compareTo(list[key]) < 0;
    }

    void sink(int key){
        while(2*key <= last){
            int j = 2*key;
            if(j < last && less(j, j +1)){
                j++;
            }
            if(!less(key, j)){
                break;
            }
            exch(key, j);
            key = j;
        }
    }

    int counted = 0;
    private void resizeArray(int i){
        E[] temp = (E[]) new Comparable[i];
        for(int j = 0; j <= last; j++){
            temp[j] = list[j];
        }
        list = temp;
//        System.out.println();
//        System.out.println("REsize #: " + ++counted + " Array Length: " + list.length);
//        System.out.println();
    }

    public boolean isEmpty(){
        return last == 0;
    }

    Comparable[] sort(Comparable[] vals, int last){
        for(int i = last/2; i >= 1; i--){
            sinkSort(vals, i, last);
        }
        while(last > 1){
            exchSort(vals, 1, last);
            sinkSort(vals, 1, --last);
        }
        return vals;
    }

    private boolean lessSort(Comparable[] vals, int i, int j){
        return vals[j].compareTo(vals[i]) < 0;
    }

    private void exchSort(Comparable[] vals, int key, int length){
        Comparable temp = vals[length];
        vals[length] = vals[key];
        vals[key] = temp;
    }

    private void sinkSort(Comparable[] vals, int key, int length){
        while(2*key <= length){
            int j = 2*key;
            if(j < length && lessSort(vals, j, j + 1)){
                j++;
            }
            if(!lessSort(vals, key, j)){
                break;
            }
            exchSort(vals, key, j);
            key = j;
        }
    }

    @Override
    public int size(){
        return last;
    }

    @Override
    public void push(E item){
        if(last + 1 == list.length){
            resizeArray(list.length * 2);
        }
        list[++last] = item;
        swim(last);
    }

    @Override
    public E peek(){
        return list[1];
    }

    @Override
    public Iterator<E> iterator(){
        sorted = (E[]) sort(this.list.clone(), last);
        return this;
    }

    @Override
    public boolean hasNext(){
        if(current == 1 && last == 0){
            return false;
        } else if(current > last){
            current = 1;
            return false;
        }
        return true;
    }

    @Override
    public E next(){
        return sorted[current++];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(E val : this){
            sb.append(val.toString());
            sb.append(" | ");
        }
        sb.delete(sb.length() - 3, sb.length() - 1);
        return sb.toString();
    }

    public static void main(String[] args){
        ListPriorityQueue<Integer> ints = new ListPriorityQueue<Integer>(50);
        ints.push(60);
        ints.push(6);
        ints.push(62);
        ints.push(63);
        ints.push(64);
        ints.push(65);
        ints.push(66);
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
            System.out.println("Val: " +i);
        }

//        ints = new ListPriorityQueue<Integer>(50);
//        for(int i = 0; i < 300; i++){
//            ints.push(i);
//        }
//        System.out.println("Length should be 300, found: " + ints.size());
//        System.out.println();
//        for(int i = 300; i > 0; i--){
//            if(i % 20 == 0){
//                System.out.println("Length before deleteMax should be " + (i) + ", found: " + ints.size());
//                System.out.println("Value before DeleteMax should be " + (i - 1) + " got " + ints.peek());
//            }
//            int val = 0;
//            val = ints.pull();
//            if(i % 20 == 0){
//                System.out.println("Length after pull should be " + (i - 1) + ", found: " + ints.size());
//                System.out.println("Value of pull should be " + (i - 1) + " got " + val);
//                System.out.println("Peek after pull should be " + (i - 2) + " got " + ints.peek());
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println("Should be true: " + ints.isEmpty());
//        System.out.println("Should be null: " + ints.pull());
//        System.out.println("Length should be 0, found: " + ints.size());
//        for(int i = 0; i < 301; i++){
//            ints.push(i);
//        }
//        System.out.println("Value should be 300, got "+ ints.peek());
//        int count = 300;
//        for(Integer i : ints){
//            if(count %20 == 0){
//                System.out.println("Value should be " + count + " got " + i);
//            }
//                count--;
//        }
    }
}
