package com.datastructures.DataStructures;

import com.datastructures.interfaces.DictionaryInterface;

import java.security.InvalidParameterException;

/**
 * A sorted implementation of a dictionary based on arrays.
 * NOTE: This class extends UnsortedArrayDictionary since it inherits a lot of the its methods and data fields.
 */
public class SortedArrayDictionary<K extends Comparable<? super K>, V>
        extends UnsortedArrayDictionary<K, V>
        implements DictionaryInterface<K, V> {

    // Constructor
    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        super();
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
        if (key == null || value == null)
            throw new InvalidParameterException("Please enter valid inputs.");
        V result = null;
        int index = getIndex(key);
        if (index < numberOfEntries && key.equals(entries[index].getKey())) {
            result = entries[index].value;
            entries[index].value = value;
        } else {
            checkCapacity();
            makeRoom(index);
            entries[index] = new Entry<>(key, value);
            numberOfEntries++;
        }
        return result;
    }

    /**
     * This method gives us the index or position of a certain key in the dictionary's array.
     * @param key: the key we are looking for.
     * @return the index.
     */
    public int getIndex(K key) {
        int index = 0;
        while (index < numberOfEntries && key.compareTo(entries[index].getKey()) > 0)
            index++;
        return index;
    }

    /**
     * This method leaves leaves a space for the new entry to come in to.
     * @param index: where we want the space to be.
     */
    private void makeRoom(int index) {
        checkCapacity();
        System.arraycopy(entries, index, entries,
                index + 1,
                numberOfEntries - index
        );
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
        if (index >= numberOfEntries) return null;
        V result = entries[index].value;

        System.arraycopy(entries, index + 1, entries, index, numberOfEntries - index - 1);
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
        return index < numberOfEntries &&
                key.equals(entries[index].getKey()) ?
                entries[index].value : null;
    }

    /**
     * Sees whether a specific entry is in this dictionary.
     *
     * @param key An object search key of the desired entry.
     * @return True if key is associated with an entry in the dictionary.
     */
    public boolean contains(K key) {
        int index = getIndex(key);
        return index < numberOfEntries &&
                ((String) key).toLowerCase().equals(
                        ((String) entries[index].getKey()).toLowerCase()
                );
    }
}