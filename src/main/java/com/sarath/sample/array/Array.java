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

    private T datas[];
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
        this.datas = (T[]) new Object[initialCapacity];
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
     * @param index Index to get the Object of type T.
     * @return T Retrieve the Object T from the given index.
     * @throws IndexOutOfBoundsException If the index is greater than the length.
     * @see IndexOutOfBoundsException
     */
    public T get(int index) {
        if (index > size())
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for the actual length " + size());
        return datas[index];
    }

    public void set(int index, T object) {
        if (index > size())
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for the actual length " + size());
        datas[index] = object;
    }

    public void clear() {
        for (int i = 0; i < actualSize; i++) {
            datas[i] = null;
        }
        length = 0;
    }

    public void add(T element) {
        if ((length + 1) >= actualSize) {
            if (actualSize == 0) actualSize = 1;
            else actualSize *= 2;
            T[] newData = (T[]) new Object[actualSize];
            for (int i = 0; i < length; i++) {
                newData[i] = datas[i];
            }
            datas = newData;
        }
        datas[length++] = element;
    }

    public T removeAt(int rmIndex) {
        if (rmIndex >= length || rmIndex < 0)
            throw new IndexOutOfBoundsException("The index which you tried is either not valid or reachable");
        T data = datas[rmIndex];
        T[] newData = (T[]) new Object[length - 1];
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (i == rmIndex) j--;
            else newData[j] = datas[i];
        }
        datas = newData;
        actualSize = --length;
        return data;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < length; i++) {
            if (datas[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object obj){
        for (int i=0;i<length;i++){
            if (datas[i].equals(obj)){
                return i;
            }
        } return -1;
    }

    public boolean contain(Object obj){
        return (indexOf(obj) != -1);
    }




    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index<length;
            }

            @Override
            public T next() {
                return datas[index++];
            }
        };
    }

    @Override
    public String toString() {
        if(length==0) return "[]";
        else {
            StringBuilder builder = new StringBuilder(length).append("[");
            for (int i=0;i<length-1;i++){
                builder.append(datas[i]).append(",");
            }
            return builder.append(datas[length-1]).append("]").toString();
        }
    }
}
