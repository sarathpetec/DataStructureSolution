package com.sarath.sample.array;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This is class is implementing the Array data structure with all requred methid
 *
 * @author Sarath
 * @version 1.0
 * @since 2020-05-31
 */

public class Array<T> implements Iterable {

    private T data[];
    private int length;
    private int actualSize;


    /**
     * No Argument Constructor
     */

    public Array() {
        this(16);
    }

    /**
     * This constructor will help to create an of of type T with a specified size.
     *
     * @param initialCapacity This is the initial capacity of the array of type T.
     * @throws IllegalArgumentException On the initial capacity 0 or less that that.
     * @see IllegalArgumentException
     */
    public Array(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Initial Capacity Should not be Zero");
        this.actualSize = initialCapacity;
        this.data = (T[]) new Object[initialCapacity];
    }

    /**
     * @return int This return the length of the array of type T
     */
    public int size() {
        return length;
    }

    /**
     * @return boolan This return
     * true  if the size of the array type T is Zero
     * false if the size of the array type T is greater that Zero
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @param index Index to get the Object of type T.
     * @return T Retrieve the Object T from the given index.
     * @exception IndexOutOfBoundsException If the index is greater than the length.
     * @see IndexOutOfBoundsException
     */
    public T get(int index) {
        if (index > size()) throw new IndexOutOfBoundsException("Index "+index+" is out of bound for the actual length "+size());
        return data[index];
    }

    public void set(int Index, T data) {

    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
