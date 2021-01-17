package com.datastructures.DataStructures;

import com.datastructures.interfaces.BagInterface;

import java.util.Iterator;
import java.util.Random;

/**
 * @author Biruk Mengistu
 * This class is used to store objects of any type, and includes helpful methods.
 */


public class ArrayList<T> implements BagInterface<T>, Iterable<T> {

    //Constants
    private static final int DEFAULT_SIZE = 25;
    private static final int MAX_CAPACITY = 10000;

    private int numberOfEntries;
    private T[] bag;

    private boolean integrityOK;

    //Default Constructor

    public ArrayList() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructor that takes desired capacity as an argument
     * Note that it checks if the desiredCapacity is greater than the maximum allowed capacity.
     *
     * @param desiredCapacity : the number of items we are expecting to store in the bag.
     */

    public ArrayList(int desiredCapacity) {
        if (desiredCapacity < MAX_CAPACITY) {
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[]) new Object[desiredCapacity];
            bag = tempBag;
            numberOfEntries = 0;
            integrityOK = true;
        } else {
            integrityOK = false;
            throw new IllegalStateException(
                    "Tried to make a bag with a greater capacity than what is allowed."
            );
        }
    }

    /**
     * This method checks if the array is full.
     *
     * @return true if array is full and false if array is not full.
     */

    private boolean isArrayFull() {
        return numberOfEntries >= bag.length;
    }

    /**
     * Method that returns the number of saved items in the bag.
     *
     * @return the number of items in the bag.
     */

    @Override
    public int size() {
        return numberOfEntries;
    }

    /**
     * Method that checks if the bag is empty.
     *
     * @return true if the bag is empty and false if the bag is not empty.
     */

    @Override
    public boolean isEmpty() {
        return bag.length == 0;
    }

    /**
     * @param a: the index of the item that we are trying to retrieve
     * @return the item.
     */

    public T get(int a) {
        return bag[a];
    }

    /**
     * @param givenIndex
     * @return This method removes an entry at a given index from the bag.
     * @Assignment_1: Exercise 1 This method is private because it will only be used in this class and wont be called from outside the class.
     */
    private T removeEntry(int givenIndex) {
        T result = null;
        if (!isEmpty() && (givenIndex >= 0)) {
            result = bag[givenIndex];
            bag[givenIndex] = bag[numberOfEntries - 1];
            bag[numberOfEntries - 1] = null;
            numberOfEntries--;
        }
        return result;
    }

    /**
     * @param item: the item that we want the index of.
     * @return the index
     * <p>
     * This method returns the index of an object in a bag.
     */
    private int getIndexOf(T item) {
        int position = -1;
        int index = 0;
        for (T t : bag) {
            if (t.equals(item)) {
                position = index;
                break;
            }
            index++;
        }
        return position;
    }

    /**@Assignment1 Exercise 2
     * This method replaces an item at an index then returns the replaced item.
     *
     * @param index        : The index of the item that we are trying to replace.
     * @param replacedWith : What we want to replace it with.
     * @return
     */
    private T replace(int index, T replacedWith) {
        if ((index >= 0) && (index <= numberOfEntries - 1)) {
            T temporary = get(index);
            bag[index] = replacedWith;
            return temporary;
        } else
            throw new IndexOutOfBoundsException(
                    "Illegal position given to replace operation.");
    }

    /**
     * @param newEntry: the object that we want added to the bag.
     * @return the result of the operation; returns true if it was successful and false otherwise.
     */

    @Override
    public boolean add(T newEntry) {
        checkIntegrity();
        boolean result = true;
        if (isArrayFull()) {
            result = false;
        } else {
            bag[numberOfEntries] = newEntry;
            numberOfEntries++;
        }
        return result;
    }

    /**
     * Counts the number of occurences of an item in the bag.
     *
     * @param item
     * @return
     */
    public int getFrequencyOf(T item) {
        int count = 0;
        for (T currentItem : bag) {
            if (item.equals(currentItem))
                count += 1;
        }
        return count;
    }

    /**
     * Checks if the bag is corrupted.
     */
    private void checkIntegrity() {
        if (!integrityOK)
            throw new SecurityException("ArrayBag object is corrupt.");
    }


    /**
     * This method removes an object from our bag.
     *
     * @param anEntry: the object
     * @return : the status of the removal. True if successful, false if not.
     */

    public boolean remove(T anEntry) {
        checkIntegrity();
        int index = getIndexOf(anEntry);
        T result = removeEntry(index);
        return anEntry.equals(result);
    }

    /**
     * @Assignment1: Exercise 5
     * This method removes all occurrences of an object in a bag
     * @param anEntry: the entry that we want removed. 
     */

    public void removeEvery(T anEntry){
        for (int i = 0; i < bag.length; i++) {
            if(bag[i].equals(anEntry))
                removeEntry(i);
        }
    }

    /**
     * @Assignment 1: Exercise 4 | Altering this method would have affected the previous implemementation of
     * clear where it would call clear the same number of times as the number of elements in the array.
     *
     * Method that removes a random element of the array. If the array contains only one object, it gets rid of
     * that object.
     * reorder() gets rid of array spaces where there is a value of null. For more information, read the comments of the next
     * method.
     */

    @Override
    public void remove() {
        checkIntegrity();
        bag[new Random().nextInt(numberOfEntries)] = null;
        reorder();
        numberOfEntries--;
    }

    /**
     * This method reorders the objects in the bag by getting rid of null members of the array.
     * It then updates the bag with non null members and also updates the numberOfEntries data field to
     * account for the objects removed. So for example:
     * {"Todd","Tom",null,"Hussam"} becomes {"Todd","Tom","Hussam"}
     */

    private void reorder() {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[numberOfEntries];
        int numberOfItems = 0;
        for (T t : bag)
            if (t != null) {
                temp[numberOfItems] = t;
                numberOfItems++;
            }
        bag = temp;
        numberOfEntries = numberOfItems;
    }

    /**
     * @Assignment1: Exercise 3
     * This method clears all data from the bag.
     */

    @Override
    public void clear() {
        checkIntegrity();
        bag = (T[]) new Object[numberOfEntries];
        numberOfEntries = 0;
    }

    /**
     * This method checks if an object exists in the bag.
     *
     * @param anEntry
     * @return true if the object exists and false otherwise.
     */

    @Override
    public boolean contains(T anEntry) {
        boolean exists = false;
        for (T currentItem : bag)
            if (anEntry == currentItem) {
                exists = true;
                break;
            }
        return exists;
    }

    /**
     * This method gives us a copy of our bag as an array.
     *
     * @return the copy of the bag as an array.
     */

    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        for (int index = 0; index < numberOfEntries; index++) {
            result[index] = bag[index];
        }
        return result;
    }

    /**
     * This method returns the members of the bag as a string separated by spaces.
     * Note that it checks if the item is null so that it can avoid a NullPointerException
     *
     * @return the string
     */

    @Override
    public String toString() {
        String returnString = "";
        for (T t : bag) {
            if (t != null) returnString += t.toString() + " ";
        }
        return returnString;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < numberOfEntries;
            }

            @Override
            public T next() {
                T t = get(index);
                index++;
                return t;
            }
        };
    }
    
}
