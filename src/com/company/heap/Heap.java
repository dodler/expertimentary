package com.company.heap;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.pow;

/**
 * Created by lyan on 11.11.16.
 */
public class Heap<E extends Comparable>{

    E[] elements;

    private int capacity, size;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int cnt = 0, switchFactor = 1, lineCnt = 1;
        while(cnt < size){
            sb.append(elements[cnt] + ":");
            cnt++;

            if (lineCnt / switchFactor > 0){
                sb.append("\n------------------------------------\n");
                lineCnt = 1;
                switchFactor *= 2;
            }else{
                lineCnt++;
            }
        }
        return sb.toString();
    }

    public Heap(int capacity){
        this.elements = (E[]) new Comparable[capacity];

        this.capacity = capacity;
        this.size = 0;
    }

    public void insert(E elm) {
        elements[size] = elm;

        pushUp(size);

        size ++;
    }

    public E extract() {
        E max = elements[0];
        swap(elements, 0, size-1);
        size--;
        extract(0);
        return max;

    }

    private void extract(int index){
        E toCompare = elements[index];

        int maxIndex = 0;

        int leftChildIndex = getLeftChildIndex(index),
                rightChildIndex = getRightChildIndex(index);

        if (leftChildIndex >= size || rightChildIndex >= size){
            return;
        }

        if (elements[getLeftChildIndex(index)].compareTo(
                elements[getRightChildIndex(index)]
        ) > 0){
            maxIndex = getLeftChildIndex(index);
        }else{
            maxIndex = getRightChildIndex(index);
        }

        if (toCompare.compareTo(elements[maxIndex]) < 0){
            swap(elements, index, maxIndex);
            extract(maxIndex);
        }
    }

    public E[] getMax() {
        if (size != 0){
            return (E[]) new Object[]{elements[0]};
        }
        return (E[]) new Object[]{};
    }

    /**
     * method checks current elements[size] element
     * and pushes it up according to binary heap order
     */
    private void pushUp(int index){
        E toPushUp = elements[index];
        int parentIndex = getParentIndex(index);

        if (toPushUp.compareTo(elements[parentIndex]) > 0){
            swap(elements, index, parentIndex);
            pushUp(parentIndex);
        }
    }

    private int getParentIndex(int index) {
        return (index - 1)/2;
    }

    private int getLeftChildIndex(int index){
        return (index+1)*2-1;
    }


    private int getRightChildIndex(int index){
        return (index+1)*2;
    }


    /**
     * method swaps 2 positions in array
     * swapping ind1 & ind2 objects
     * @param toSwap array where swap should be performed
     * @param ind1
     * @param ind2
     */
    private void swap(Object[] toSwap, int ind1, int ind2){
        Object tmp = toSwap[ind1];
        toSwap[ind1] = toSwap[ind2];
        toSwap[ind2] = tmp;
    }

    public static<E extends Comparable> Heap<E> fromArray(E[] elements) {
        Heap<E> heap = new Heap<>(elements.length);

        for (E element : elements) {
            heap.insert(element);
        }

        return heap;

    }
}
