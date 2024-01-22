package ru.job4j.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ConcurrentModificationException;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > container.length) {
            int newCapacity = container.length * 2;
            container = Arrays.copyOf(container, Math.max(minCapacity, newCapacity));
        }
    }

    @Override
    public void add(T value) {
        ensureCapacity(size + 1);
        container[size++] = value;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        validateIndex(index);
        T oldValue = (T) container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        T removedValue = (T) container[index];
        System.arraycopy(container, index + 1, container, index, size - index - 1);
        container[--size] = null;
        modCount++;
        return removedValue;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return (T) container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[currentIndex++];
            }
        };
    }
}

