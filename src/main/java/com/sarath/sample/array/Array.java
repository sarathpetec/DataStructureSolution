package com.sarath.sample.array;

import java.util.Iterator;

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
     * This is to return the size of the Array
     *
     * @return int This return the length of the array of type T
     */
    public int size() {
        return length;
    }

    /**
     * This method helps to check whether the Object of type T size is Zero or not.
     *
     * @return boolan This return
     * true  if the size of the array type T is Zero
     * false if the size of the array type T is greater that Zero
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * This is to get the object of type T from the given index
     *
     * @param index Index to get the Object of type T.
     * @return T Retrieve the Object T from the given index.
     * @throws IndexOutOfBoundsException If the index is greater than the length or less than Zero.
     * @see IndexOutOfBoundsException
     */
    public T get(int index) {
        if (index > size() || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for the actual length " + size());
        return datas[index];
    }


    /**
     * This method is to add an object of type T to a specific location.
     *
     * @param index  Index to set the Object of type T.
     * @param object Object to be insert in the particular index.
     * @throws IndexOutOfBoundsException If the index is greater than the length or less than Zero
     * @see IndexOutOfBoundsException
     */
    public void set(int index, T object) {
        if (index > size() || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for the actual length " + size());
        datas[index] = object;
    }

    /**
     * This method is to clear the Object from each location and set the length to Zero
     *
     * @return void
     */
    public void clear() {
        for (int i = 0; i < actualSize; i++) {
            datas[i] = null;
        }
        length = 0;
    }

    /**
     * This is to add and object of type T to the next available location.
     * If the next array size is to be actual size then
     * there will be a new array of type T will create with double the actual size.
     * All the element from the old array will be copy to the new array.
     * Finally the new array reassign to old array.
     *
     * @param element
     * @return void
     */
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


    /**
     * This method is to return the object from the given index.
     *
     * @param rmIndex
     * @return Object of type T will return.
     * @throws IndexOutOfBoundsException If the index is greater than the length or less than Zero
     * @see IndexOutOfBoundsException
     */
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

    /**
     * This is to remove an object by checking the equality with each index of the array of type T.
     *
     * @param obj
     * @return boolean
     * true if the object is available and able to remove.
     * false if it is not available
     */
    public boolean remove(Object obj) {
        for (int i = 0; i < length; i++) {
            if (datas[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /**
     * This is to return the index of the object of type T passed as an argument.
     *
     * @param obj
     * @return Object location if the object is available otherwise will return -1.
     */
    public int indexOf(Object obj) {
        for (int i = 0; i < length; i++) {
            if (datas[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Thsi is to check the object is present in the array.
     *
     * @param obj
     * @return true if it is available otherwise fale.
     */
    public boolean contain(Object obj) {
        return (indexOf(obj) != -1);
    }

    /**
     * This class implements Iterable interface is used to iterate over the elements in a collection.
     *
     * @return Iterator of type T.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return datas[index++];
            }
        };
    }

    /**
     * This is the toString method which will print the object in the console or log file.
     *
     * @return string
     */
    @Override
    public String toString() {
        if (length == 0) return "[]";
        else {
            StringBuilder builder = new StringBuilder(length).append("[");
            for (int i = 0; i < length - 1; i++) {
                builder.append(datas[i]).append(",");
            }
            return builder.append(datas[length - 1]).append("]").toString();
        }
    }
}
