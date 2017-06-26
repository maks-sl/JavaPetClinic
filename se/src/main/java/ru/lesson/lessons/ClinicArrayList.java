package ru.lesson.lessons;

import java.util.*;

/**
 * @param <E> Тип-параметр
 */
public class ClinicArrayList<E> extends AbstractList<E> implements List<E> {


    private final static int INC_CAPACITY = 10;
    private int size;
    private E[] keeping;

    public ClinicArrayList() {
        this.keeping = (E[]) new Object[INC_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public E get(int index) {
        rangeCheck(index);
        return keeping[index];
    }

    private void rangeCheck(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }













}