package com.company.heap;

import java.util.Random;

/**
 * Created by lyan on 11.11.16.
 */
public class Utils {


    public static<E extends Comparable> Heap<E> createRandomHeap(int elementsNumber){

        Random random = new Random(System.currentTimeMillis());
        int factor = 100;
        Integer[] elements = new Integer[elementsNumber];

        for(int i = 0; i<elementsNumber; i++){
//            elements[i] = random.nextInt()%100;
            elements[i] = i;
        }

        return Heap.fromArray((E[]) elements);
    }
}
