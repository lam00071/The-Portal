package com.datastructures.DataStructures;

import com.datastructures.interfaces.DictionaryInterface;

import java.util.Arrays;
import java.util.Iterator;

public class UnsortedArrayDictionary<K, V> implements DictionaryInterface<K, V> {
    protected int numberOfEntries;
    protected static final int MAX_CAPACITY = 1000;
    protected final static int DEFAULT_CAPACITY = 5;
    protected boolean integrity_ok;
    protected Entry<K, V>[] entries;

    @SuppressWarnings("unchecked")
    public UnsortedArrayDictionary() {
        entries = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        numberOfEntries = 0;
        integrity_ok = true;
    }

    /**
     * This method checks the capacity of the array and doubles the size if necessary.
     */
    protected void checkCapacity() {
        if (entries.length <= numberOfEntries) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        } else if (entries.length * 2 >= MAX_CAPACITY) {
            integrity_ok = false;
            throw new SecurityException("Size has been exceeded.");
        }

    }

    /**
     * Adds a new entry to this dictionary. If the given search key already
     * exists in the dictionary, replaces the corresponding value.
     *
     * @param key   An object search key of the new entry.
     * @param value An object associated with the search key.
     * @return Either null if the new entry was added to the dictionary
     * or the value that was associated with key if that was replaced.
     */
    public V add(K key, V value) {
        V result = null;
        int index = getIndex(key);
        if (index != -1) {
            result = entries[index].value;
            entries[index].value = value;
        } else {
            checkCapacity();
            entries[numberOfEntries] = new Entry<>(key, value);
            numberOfEntries++;
        }
        return result;
    }

    private int getIndex(K key) {
        int index = -1;
        for (int i = 0; i < entries.length; i++)
            if (entries[i] != null) if (entries[i].key == key) index = i;
        return index;
    }

    /**
     * Removes a specific entry from this dictionary.
     *
     * @param key An object search key of the entry to be removed.
     * @return Either the value that was associated with the search key
     * or null if no such object exists.
     */
    public V remove(K key) {
        int index = getIndex(key);
        if (index == -1) return null;
        V result = entries[index].value;
        entries[index] = entries[numberOfEntries - 1];
        entries[numberOfEntries - 1] = null;
        numberOfEntries--;
        return result;
    }

    /**
     * Retrieves from this dictionary the value associated with a given
     * search key.
     *
     * @param key An object search key of the entry to be retrieved.
     * @return Either the value that is associated with the search key
     * or null if no such object exists.
     */
    public V getValue(K key) {
        int index = getIndex(key);
        return index == -1 ? null : entries[index].value;
    }

    /**
     * Sees whether a specific entry is in this dictionary.
     *
     * @param key An object search key of the desired entry.
     * @return True if key is associated with an entry in the dictionary.
     */
    public boolean contains(K key) {
        return getIndex(key) != -1;
    }

    /**
     * Creates an iterator that traverses all search keys in this dictionary.
     *
     * @return An iterator that provides sequential access to the search
     * keys in the dictionary.
     */
    public Iterator<K> getKeyIterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index <= numberOfEntries - 1;
            }

            @Override
            public K next() {
                if (!hasNext()) return null;
                else {
                    K key = entries[index].key;
                    index++;
                    return key;
                }
            }
        };
    }

    /**
     * Creates an iterator that traverses all values in this dictionary.
     *
     * @return An iterator that provides sequential access to the values
     * in this dictionary.
     */
    public Iterator<V> getValueIterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index <= numberOfEntries - 1;
            }

            @Override
            public V next() {
                if (!hasNext()) return null;
                else {
                    V val = entries[index].value;
                    index++;
                    return val;
                }
            }
        };
    }

    /**
     * Sees whether this dictionary is empty.
     *
     * @return True if the dictionary is empty.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * Gets the size of this dictionary.
     *
     * @return The number of entries (key-value pairs) currently
     * in the dictionary.
     */
    public int getSize() {
        return numberOfEntries;
    }

    /**
     * This method returns all of the keys and values in the dictionary.
     * @return the string
     */
    public String toString() {
        Iterator<K> keyIterator = getKeyIterator();
        Iterator<V> valueIterator = getValueIterator();
        String s = "";
        while (keyIterator.hasNext()) {
            K currentKey = keyIterator.next();
            V currentValue = valueIterator.next();
            s += currentKey + " - " + currentValue + "\n";
        }
        return s;
    }

    /**
     * Removes all entries from this dictionary.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        numberOfEntries = 0;
        entries = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
    }


    /**
     * Class that contains the data pairs.
     */
    protected static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}