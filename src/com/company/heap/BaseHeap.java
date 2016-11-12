package com.company.heap;

/**
 * Created by lyan on 11.11.16.
 */
public interface BaseHeap <E extends Comparable>{
    void insert(E elm);

    void delete(E elm);

    E getMax();
}
