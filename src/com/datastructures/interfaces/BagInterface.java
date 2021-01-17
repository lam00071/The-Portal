package com.datastructures.interfaces;

public interface BagInterface<T> {

    /**
     * This interface is used for the ArrayBag Class.
     */


    /**
     * Method that returns the number of saved items in the bag.
     *
     * @return the number of items in the bag.
     */

    int size();

    /**
     * Method that checks if the bag is empty.
     *
     * @return true if the bag is empty and false if the bag is not empty.
     */

    boolean isEmpty();

    /**
     * @param newEntry: the object that we want added to the bag.
     * @return the result of the operation; returns true if it was successful and false otherwise.
     */

    boolean add(T newEntry);

    /**
     * This method removes an object from our bag.
     *
     * @param anEntry: the object
     * @return : the status of the removal. True if successful, false if not.
     */

    boolean remove(T anEntry);

    /**
     * Method that removes the last element of the array. If the array contains only one object, it gets rid of
     * that object.
     */

    void remove();

    /**
     * This method clears all data from the bag.
     */

    void clear();

    /**
     * This method checks if an object exists in the bag.
     *
     * @param anEntry
     * @return true if the object exists and false otherwise.
     */

    boolean contains(T anEntry);

    /**
     * This method gives us a copy of our bag as an array.
     *
     * @return the copy of the bag as an array.
     */

    T[] toArray();

}
