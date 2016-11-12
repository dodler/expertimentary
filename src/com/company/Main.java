package com.company;

import com.company.heap.BaseHeap;
import com.company.heap.Heap;
import com.company.heap.Utils;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        Heap<Integer> heap = Utils.createRandomHeap(30);
        System.out.println(heap);
        System.out.println(heap.extract());
    }
}
