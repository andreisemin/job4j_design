package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry[] table = new MapEntry[capacity];


    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private int basket(K key) {
        return key == null ? 0 : indexFor(hash(Objects.hashCode(key)));
    }

    private boolean check(K key) {
        int index = basket(key);
        return table[index] != null && Objects.hashCode(table[index].key) == Objects.hashCode(key)
                && Objects.equals(table[index].key, key);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] temp = table;
        table = new MapEntry[capacity];
        for (MapEntry<K, V> entry : temp) {
            if (entry != null) {
                table[basket(entry.key)] = entry;
            }
        }
    }

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        boolean rsl = false;
        int index = basket(key);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        int index = basket(key);
        MapEntry<K, V> entry = table[index];
        return check(key) ? entry.value : null;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int index = basket(key);
        if (check(key)) {
            table[index] = null;
            count--;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int expectedModCount = modCount;
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (currentIndex < capacity && table[currentIndex] == null) {
                    currentIndex++;
                }
                return currentIndex < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (K) table[currentIndex++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
