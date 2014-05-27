package Newtonian;

import Entities.Structures.ListMinPriorityQueue;
import Entities.Structures.ListPriorityQueue;
import Entities.Structures.ListQueue;

/**
 * Created by marcphillips on 5/19/2014.
 */
public class Holder{

    public static String reverse(String reverse){
        return reverse(reverse.toCharArray());
    }

    public static String reverse(char[] chars){
        char temp;
        for(int i = 0; i < chars.length / 2; i++){
            temp = chars[chars.length - i - 1];
            chars[chars.length - i -1] = chars[i];
            chars[i] = temp;
        }
        return new String(chars);
    }

    //For testing only!
    static long factorial(long n) { return n <= 1 ? 1 : n * factorial(n-1); }

    public static String permutate(String perm){
        if(perm.length() == 1){
            return perm;
        }
        ListMinPriorityQueue<String> strings = new ListMinPriorityQueue<String>();
        char[] chars = perm.toCharArray();
        char temp;

        for(int i =0; i < chars.length/2; i++){
            strings.push(new String(chars));
            strings.push(reverse(chars.clone()));
            for(int j = i + 1; j < chars.length; j++){
                temp = chars[chars.length - j];
                chars[chars.length - j] = chars[j];
                chars[j] = temp;
                strings.push(new String(chars));
                strings.push(reverse(chars.clone()));
            }
            temp = chars[chars.length - 1 - i];
            chars[chars.length - 1 - i] = chars[i + 1];
            chars[i +1] = temp;
        }
        return strings.toString();
    }

    public static void main(String[] args){
        for(int i = 0; i < 5; i++){
            String rev = "Test+" + i;
            System.out.println("Input: " + rev + " Result: " + reverse(rev));
        }
        System.out.println(permutate("hitme"));
    }
}
