package Entities.Structures;

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
    public E pull(){
        E val = super.pull();
        current = last;
        return val;
    }

    @Override
    public void remove(E item){
        super.remove(item);
        current = last;
    }

    @Override
    public void push(E item){
        super.push(item);
        current = last;
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

    @Override
    public boolean hasNext(){
        if(current == 1 && last == 0){
            return false;
        } else if(current < 1){
            current = last;
            return false;
        }
        return true;
    }

    @Override
    public E next(){
        return current < 0 ? null: sorted[current--];
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
        ListMinPriorityQueue<Integer> ints = new ListMinPriorityQueue<Integer>(50);

        for(int i = 0; i < 10; i++){
            ints.push(i);
        }

        ints.pull();
        ints.pull();
        ints.pull();
        ints.pull();
        ints.push(54);
        ints.push(4);
        System.out.println("Expected: 4 : " + ints.pull());
        for(Integer i : ints){
            System.out.println(i);
        }

        ints = new ListMinPriorityQueue<Integer>();
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
//        for(int i = 300; i > 0; i--){
//            if(i % 20 == 0){
//                System.out.println("Length before deleteMax should be " + (i) + ", found: " + ints.size());
//                System.out.println("Value before DeleteMax should be " + (300 - i) + " got " + ints.peek());
//            }
//            int val = 0;
//            val = ints.pull();
//            if(i % 20 == 0){
//                System.out.println("Length after pull should be " + (i - 1) + ", found: " + ints.size());
//                System.out.println("Value of pull should be " + (300 - i) + " got " + val);
//                System.out.println("Peek after pull should be " + (300 - i + 1) + " got " + ints.peek());
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
//        System.out.println("Value should be 0, got "+ ints.peek());
//        int count = 0;
//        for(Integer i : ints){
//            if(count %20 == 0){
//                System.out.println("Value should be " + count + " got " + i);
//            }
//            count++;
//        }
    }

}
